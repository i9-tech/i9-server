package school.sptech.service.recuperacaoSenha;

import com.azure.core.exception.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.recuperacaoSenha.RecuperacaoSenhaToken;
import school.sptech.exception.EntidadeInativaException;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.recuperacaoSenha.RecuperacaoSenhaTokenRepository;
import school.sptech.service.emailService.EmailService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class RecuperacaoSenhaTokenService {

    private final FuncionarioRepository funcionarioRepository;
    private final RecuperacaoSenhaTokenRepository senhaTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public RecuperacaoSenhaTokenService(FuncionarioRepository funcionarioRepository, RecuperacaoSenhaTokenRepository senhaTokenRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.senhaTokenRepository = senhaTokenRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Transactional
    public void iniciarRecuperacaoSenha(String cpf) {
        Funcionario funcionario = funcionarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado!"));

        if (!funcionario.isAtivo()) {
            throw new EntidadeInativaException("Funcionário inativo!");
        }

        senhaTokenRepository.invalidateActiveTokensByFuncionarioId(funcionario.getId());

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusMinutes(30);

        RecuperacaoSenhaToken recuperacaoToken = new RecuperacaoSenhaToken();
        recuperacaoToken.setToken(token);
        recuperacaoToken.setFuncionario(funcionario);
        recuperacaoToken.setExpiracao(expiracao);
        recuperacaoToken.setTokenUsado(false);
        senhaTokenRepository.save(recuperacaoToken);

        String linkRecuperacao = frontendUrl + "/redefinir-senha/" + token;

        String emailDestinatario = funcionario.getEmpresa().getEmail();
        emailService.enviarEmailRecuperacao(
                emailDestinatario,
                funcionario.getNome(),
                funcionario.getEmpresa().getNome(),
                funcionario.getCpf(),
                linkRecuperacao
        );
    }


    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        RecuperacaoSenhaToken recuperacaoToken = senhaTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Token de recuperação inválido."));

        if (recuperacaoToken.isTokenUsado()) {
            throw new IllegalStateException("Este link de recuperação já foi utilizado.");
        }

        if (recuperacaoToken.getExpiracao().isBefore(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")))) {
            throw new IllegalStateException("Este link de recuperação expirou.");
        }

        Funcionario funcionario = recuperacaoToken.getFuncionario();
        if (funcionario == null) {
            throw new EntidadeNaoEncontradaException("Funcionário associado ao token não encontrado.");
        }
        String senhaCriptografada = passwordEncoder.encode(novaSenha);

        funcionario.setSenha(senhaCriptografada);

        funcionarioRepository.save(funcionario);

        recuperacaoToken.setTokenUsado(true);
        senhaTokenRepository.save(recuperacaoToken);
    }

    public String buscarCpfUsuarioToken(String token) {
        RecuperacaoSenhaToken recuperacaoToken = senhaTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Token de recuperação inválido."));

        Funcionario funcionario = recuperacaoToken.getFuncionario();

        if (funcionario == null) {
            throw new EntidadeNaoEncontradaException("Funcionário associado ao token não encontrado.");
        }

        return funcionario.getCpf();
    }
}

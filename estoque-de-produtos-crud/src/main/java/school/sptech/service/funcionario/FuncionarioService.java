package school.sptech.service.funcionario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.config.GerenciadorTokenJwt;
import school.sptech.controller.funcionario.dto.FuncionarioMapper;
import school.sptech.controller.funcionario.dto.FuncionarioRequestDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;

import school.sptech.controller.funcionario.dto.FuncionarioTokenDto;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.funcionario.IdentificadorFactory;
import school.sptech.entity.funcionario.IdentificadorPrincipal;
import school.sptech.entity.funcionario.TipoIdentificador;
import school.sptech.exception.EntidadeConflictException;

import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.exception.EnumBadRequestException;
import school.sptech.exception.ValidacaoException;
import school.sptech.observer.FuncionarioEvent;
import school.sptech.observer.FuncionarioEventListener;
import school.sptech.repository.empresa.EmpresaRepository;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.service.emailService.NotificacaoProducer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final EmpresaRepository empresaRepository;

    private final FuncionarioRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final GerenciadorTokenJwt gerenciadorTokenJwt;

    private final AuthenticationManager authenticationManager;

//    private final ApplicationEventPublisher eventPublisher;
    private final NotificacaoProducer notificacaoProducer;

    public FuncionarioService(EmpresaRepository empresaRepository, FuncionarioRepository repository, PasswordEncoder passwordEncoder, GerenciadorTokenJwt gerenciadorTokenJwt, AuthenticationManager authenticationManager, NotificacaoProducer notificacaoProducer) {
        this.empresaRepository = empresaRepository;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
        this.authenticationManager = authenticationManager;
        this.notificacaoProducer = notificacaoProducer;
    }

    public String criptografar(String senha) {
        return passwordEncoder.encode(senha);
    }

    public FuncionarioTokenDto autenticar(Funcionario funcionario) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                funcionario.getLogin(), funcionario.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Funcionario funcionarioAutenticado =
                repository.findByLogin(funcionario.getLogin())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Login do usuário não cadastrado", null)
                        );

        if (!funcionarioAutenticado.isAtivo()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Usuário inativo. Solicite ativação com um administrador.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return FuncionarioMapper.of(funcionarioAutenticado, token);
    }

    public FuncionarioResponseDto cadastrarFuncionario(Funcionario funcionario, Integer idEmpresa){

        boolean funcionarioExisteByCpf = repository.existsByCpfAndEmpresa_Id(funcionario.getCpf(), idEmpresa);
        if (funcionarioExisteByCpf) {
            throw new EntidadeConflictException("O CPF informado já está em uso!");
        }

        boolean loginExiste = repository.existsByLogin(funcionario.getLogin());
        if (loginExiste) {
            throw new EntidadeConflictException("O login informado já está em uso!");
        }

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Empresa não encontrada"));
        funcionario.setEmpresa(empresa);

        IdentificadorPrincipal validador = IdentificadorFactory.criar(funcionario.getIdentificadorPrincipal());
        if (!validador.validar(funcionario.getLogin())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Login inválido para o tipo " + validador.getTipo()
            );
        }

        String senhaGerada = gerarSenha(empresa.getId(), funcionario.getCpf());

        String senhaCriptografada = passwordEncoder.encode(senhaGerada);

        funcionario.setSenha(senhaCriptografada);

        funcionario.setSenha(senhaCriptografada);
        funcionario.setId(funcionario.getId());

        Funcionario funcionarioSalvo = repository.save(funcionario);
        String cargos = FuncionarioEventListener.determinarCargo(funcionarioSalvo);
        String emailDestinatario =
                funcionarioSalvo.getEmail() == null ?
                        funcionarioSalvo.getEmpresa().getEmail() :
                        funcionarioSalvo.getEmail();

        notificacaoProducer.publicarEventoFuncionarioCadastrado(
                funcionarioSalvo.getNome(),
                funcionarioSalvo.getCpf(),
                cargos,
                emailDestinatario
        );

        return FuncionarioMapper.toDto(funcionario);
    }


    public String gerarSenha(int empresaId, String cpfFuncionario) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        // Remove tudo que não for número do CPF
        String cpfLimpo = cpfFuncionario.replaceAll("[^\\d]", "");

        String senha = cpfLimpo + '@' + empresa.getNomeSenha();

        return senha;
    }


    public List<Funcionario> listarPorEmpresa(Integer idEmpresa) {
        List<Funcionario> todosFuncionariosEmpresa = repository.findByEmpresaIdAndAtivoTrue(idEmpresa);

        if (todosFuncionariosEmpresa.isEmpty()) {
            return Collections.emptyList(); // Retorna uma lista vazia
        }

        return todosFuncionariosEmpresa;
    }

    public Funcionario buscarFuncionarioPorId(int id, Integer idEmpresa) {
        return repository.findByIdAndEmpresaId(id, idEmpresa)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado na empresa especificada."));
    }


    public void removerPorId(int id, Integer idEmpresa) {
        Optional<Funcionario> funcionario = repository.findByIdAndEmpresaId(id, idEmpresa);

        if (funcionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Funcionário não encontrado na empresa especificada.");
        }

        repository.softDeleteByIdAndEmpresa(id,idEmpresa);

    }

    public Funcionario editarFuncionario(int id, Integer idEmpresa, Funcionario funcionarioParaEditar) {
        Optional<Funcionario> funcionarioOptional = repository.findByIdAndEmpresaId(id, idEmpresa);

        if (funcionarioOptional.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Funcionário não encontrado na empresa especificada.");
        }

        Funcionario funcionarioExistente = funcionarioOptional.get();

        funcionarioExistente.setCpf(funcionarioParaEditar.getCpf());
        funcionarioExistente.setNome(funcionarioParaEditar.getNome());
        funcionarioExistente.setCargo(funcionarioParaEditar.getCargo());
        funcionarioExistente.setEmail(funcionarioParaEditar.getEmail());

        if (funcionarioParaEditar.getSenha() != null && !funcionarioParaEditar.getSenha().isBlank()) {
            if (!passwordEncoder.matches(funcionarioParaEditar.getSenha(), funcionarioExistente.getSenha())) {
                String senhaCriptografada = passwordEncoder.encode(funcionarioParaEditar.getSenha());
                funcionarioExistente.setSenha(senhaCriptografada);
            }
        }

        funcionarioExistente.setAcessoSetorCozinha(funcionarioParaEditar.isAcessoSetorCozinha());
        funcionarioExistente.setAcessoSetorAtendimento(funcionarioParaEditar.isAcessoSetorAtendimento());
        funcionarioExistente.setAcessoSetorEstoque(funcionarioParaEditar.isAcessoSetorEstoque());
        funcionarioExistente.setProprietario(funcionarioParaEditar.isProprietario());

        return repository.save(funcionarioExistente);
    }


    public void validarFuncionario(FuncionarioRequestDto requestDto){
        if (requestDto.getNome() == null || requestDto.getNome().trim().isEmpty()){
            throw new ValidacaoException("O nome do funcionário é obrigatório");
        }  if (requestDto.getCpf() == null){
            throw new ValidacaoException("O CPF do funcionário é obrigatório");
        } if (requestDto.getCargo() == null || requestDto.getCargo().trim().isEmpty()){
            throw new ValidacaoException("O cargo do funcionário é obrigatório");
        }
    }

}

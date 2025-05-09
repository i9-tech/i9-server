package school.sptech.service.funcionario;

import org.springframework.beans.factory.annotation.Autowired;
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
import school.sptech.exception.EntidadeConflictException;

import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.exception.SenhaInvalidaException;
import school.sptech.exception.ValidacaoException;
import school.sptech.repository.empresa.EmpresaRepository;
import school.sptech.repository.funcionario.FuncionarioRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String criptografar(String senha) {
        return passwordEncoder.encode(senha);
    }

    public FuncionarioTokenDto autenticar(Funcionario funcionario) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                funcionario.getCpf(), funcionario.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Funcionario funcionarioAutenticado =
                repository.findByCpf(funcionario.getCpf())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "CPF do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return FuncionarioMapper.of(funcionarioAutenticado, token);
    }

    public Funcionario cadastrarFuncionario(Funcionario requestDto, Integer idEmpresa){

        boolean funcionarioExisteByCpf = repository.existsByCpfIgnoreCaseAndEmpresa_Id(requestDto.getCpf(), idEmpresa);

        if (funcionarioExisteByCpf){
            throw new EntidadeConflictException("Esse usuário já está cadastrado!");
        }

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Empresa não encontrada"));
        requestDto.setEmpresa(empresa);

        String senhaCriptografada = passwordEncoder.encode(requestDto.getSenha());
        requestDto.setSenha(senhaCriptografada);

        requestDto.setId(requestDto.getId());
        return repository.save(requestDto);
    }

    public List<FuncionarioResponseDto> listarPorEmpresa(Integer idEmpresa) {
        List<Funcionario> todosFuncionariosEmpresa = repository.findByEmpresaIdAndAtivoTrue(idEmpresa);

        if (todosFuncionariosEmpresa.isEmpty()) {
            return Collections.emptyList(); // Retorna uma lista vazia
        }

        return todosFuncionariosEmpresa.stream()
                .map(FuncionarioMapper::toDto)
                .collect(Collectors.toList()); // Retorna uma lista com dtos
    }

    public FuncionarioResponseDto buscarFuncionarioPorId(int id,Integer idEmpresa) {
        Optional<Funcionario> funcionario = repository.findByIdAndEmpresaId(id, idEmpresa);

        if (funcionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Funcionário não encontrado na empresa especificada.");
        }

        return FuncionarioMapper.toDto(funcionario.get());

    }

    public void removerPorId(int id, Integer idEmpresa) {
        Optional<Funcionario> funcionario = repository.findByIdAndEmpresaId(id, idEmpresa);

        if (funcionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Funcionário não encontrado na empresa especificada.");
        }

        repository.softDeleteByIdAndEmpresa(id,idEmpresa);

    }

    public FuncionarioResponseDto editarFuncionario(int id, Integer idEmpresa , FuncionarioRequestDto requestDto){
        Optional<Funcionario> funcionario = repository.findByIdAndEmpresaId(id, idEmpresa);

        if (funcionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Funcionário não encontrado na empresa especificada.");
        }

        Funcionario funcionarioExiste = funcionario.get();

        validarFuncionario(requestDto);

        funcionarioExiste.setCpf(requestDto.getCpf());
        funcionarioExiste.setNome(requestDto.getNome());
        funcionarioExiste.setCargo(requestDto.getCargo());
        funcionarioExiste.setSenha(requestDto.getSenha());
        funcionarioExiste.setAcessoSetorCozinha(requestDto.isAcessoSetorCozinha());
        funcionarioExiste.setAcessoSetorAtendimento(requestDto.isAcessoSetorAtendimento());
        funcionarioExiste.setAcessoSetorEstoque(requestDto.isAcessoSetorEstoque());

        return FuncionarioMapper.toDto(repository.save(funcionarioExiste));
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

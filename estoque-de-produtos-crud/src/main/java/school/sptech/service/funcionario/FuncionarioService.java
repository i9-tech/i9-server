package school.sptech.service.funcionario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.controller.funcionario.dto.FuncionarioMapper;
import school.sptech.controller.funcionario.dto.FuncionarioRequestDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;

import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.exception.EntidadeConflictException;

import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.exception.ValidacaoException;
import school.sptech.repository.empresa.EmpresaRepository;
import school.sptech.repository.funcionario.FuncionarioRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FuncionarioService {

    private FuncionarioRepository repository;
    @Autowired
    private EmpresaRepository empresaRepository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public FuncionarioResponseDto cadastrarFuncionario(FuncionarioRequestDto requestDto, Integer idEmpresa){

        boolean funcionarioExisteByCpf = repository.existsByCpfIgnoreCaseAndEmpresa_Id(requestDto.getCpf(), idEmpresa);

        if (funcionarioExisteByCpf){
            throw new EntidadeConflictException("Esse usuário já está cadastrado!");
        }

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Empresa não encontrada"));

        Funcionario funcionario = FuncionarioMapper.toEntity(requestDto, empresa);

        funcionario = repository.save(funcionario);
        return FuncionarioMapper.toDto(funcionario);

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

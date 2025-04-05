package school.sptech.service.funcionario;

import org.springframework.stereotype.Service;
import school.sptech.controller.funcionario.dto.FuncionarioRequestDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.exception.EntidadeConflictException;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.exception.ValidacaoException;
import school.sptech.repository.funcionario.FuncionarioRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public FuncionarioResponseDto cadastrarFuncionario(FuncionarioRequestDto requestDto, int fkEmpresa){

        boolean funcionarioExisteByCpf = repository.existsByCpfIgnoreCaseAndFkEmpresa(requestDto.getCpf(), fkEmpresa);

        if (funcionarioExisteByCpf){
            throw new EntidadeConflictException("Esse usuário já está cadastrado!");
        }

        // Convertendo DTO para entity
        Funcionario funcionario = FuncionarioRequestDto.toEntity(requestDto, fkEmpresa);

        funcionario = repository.save(funcionario);
        return FuncionarioResponseDto.fromEntity(funcionario);

    }

    public List<FuncionarioResponseDto> listarPorEmpresa(int fkEmpresa) {

        List<Funcionario> todosFuncionariosEmpresa = repository.findByFkEmpresa(fkEmpresa);

        if (todosFuncionariosEmpresa.isEmpty()) {
            return Collections.emptyList(); // Retorna uma lista vazia
        }

        return todosFuncionariosEmpresa.stream()
                .map(FuncionarioResponseDto::fromEntity)
                .collect(Collectors.toList()); // Retorna uma lista com dtos
    }

    public FuncionarioResponseDto buscarFuncionarioPorId(int id, int fkEmpresa) {

        Optional<Funcionario> funcionario = repository.findByIdAndFkEmpresa(id, fkEmpresa);

        if (funcionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Funcionário não encontrado na empresa especificada.");
        }


        return FuncionarioResponseDto.fromEntity(funcionario.get());

    }

    public void removerPorId(int id, int fkEmpresa) {

        Optional<Funcionario> funcionario = repository.findByIdAndFkEmpresa(id, fkEmpresa);

        if (funcionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Funcionário não encontrado na empresa especificada.");
        }

        repository.delete(funcionario.get());
    }


    public FuncionarioResponseDto editarFuncionario(int id, int fkEmpresa, FuncionarioRequestDto requestDto){
        Optional<Funcionario> funcionario = repository.findByIdAndFkEmpresa(id, fkEmpresa);

        if (funcionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Funcionário não encontrado na empresa especificada.");
        }

        Funcionario funcionarioExiste = funcionario.get();

        validarFuncionario(requestDto);

        funcionarioExiste.setId(id);
        funcionarioExiste.setCpf(requestDto.getCpf());
        funcionarioExiste.setNome(requestDto.getNome());
        funcionarioExiste.setCargo(requestDto.getCargo());
        funcionarioExiste.setSenha(requestDto.getSenha());

        if (funcionarioExiste.isProprietario()){
            funcionarioExiste.setAcessoSetorCozinha(requestDto.isAcessoSetorCozinha());
            funcionarioExiste.setAcessoSetorAtendimento(requestDto.isAcessoSetorAtendimento());
            funcionarioExiste.setAcessoSetorEstoque(requestDto.isAcessoSetorEstoque());
        }else {
            throw new ValidacaoException("Você não é proprietário para alterar o acesso aos setores");
        }

       return FuncionarioResponseDto.fromEntity(repository.save(funcionarioExiste));
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

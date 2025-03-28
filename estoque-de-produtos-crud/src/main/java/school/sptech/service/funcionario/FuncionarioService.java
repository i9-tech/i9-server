package school.sptech.service.funcionario;

import org.springframework.beans.factory.annotation.Autowired;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.exception.EntidadeConflictException;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.funcionario.FuncionarioRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public List<Funcionario> listarPorEmpresa(int fkEmpresa) {

        List<Funcionario> todosFuncionariosEmpresa = repository.findByFkEmpresa(fkEmpresa);

        //Em operações de listagem, é comum retornar uma lista vazia em vez de lançar uma exceção, isso evita tratativa de erro
        if (todosFuncionariosEmpresa.isEmpty()) {
            return Collections.emptyList(); // Retorna uma lista vazia
        }

        return todosFuncionariosEmpresa;

    }

    public Optional<Funcionario> buscarFuncionarioPorId(int id, int fkEmpresa) {

        Optional<Funcionario> funcionario = repository.findByIdAndFkEmpresa(id, fkEmpresa);

        if (funcionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Funcionário não encontrado na empresa especificada.");
        }

        return funcionario;

    }

    public void removerPorId(int id, int fkEmpresa) {

        Optional<Funcionario> funcionario = repository.findByIdAndFkEmpresa(id, fkEmpresa);

        if (funcionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Funcionário não encontrado na empresa especificada.");
        }

        repository.deleteById(funcionario.get());
    }

    public Funcionario cadastrarFuncionario(Funcionario funcionario, int fkEmpresa){

        boolean funcionarioExisteByCpf = repository.existsByCpfIgnoreCaseAndFkEmpresa(funcionario.getCpf(), fkEmpresa);
        boolean funcionarioExisteById= repository.existsByIdAndFkEmpresa(funcionario.getId(), fkEmpresa);

        if (funcionarioExisteByCpf || funcionarioExisteById){
            throw new EntidadeConflictException("Esse usuário já está cadastrado!");
        }

        return repository.save(funcionario);

    }
}

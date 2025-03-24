package school.sptech.service.funcionario;

import org.springframework.beans.factory.annotation.Autowired;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.funcionario.FuncionarioRepository;

import java.util.List;
import java.util.Optional;

public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public List<Funcionario> listarPorEmpresa(Integer fkEmpresa){

        List<Funcionario> todosFuncionariosEmpresa = repository.findByFkEmpresa(fkEmpresa);

        if (todosFuncionariosEmpresa.isEmpty()){
            throw new EntidadeNaoEncontradaException( "Nenhum funcionário encontrado para a empresa ID: " + fkEmpresa );
        }

        return todosFuncionariosEmpresa;

    }

    public Funcionario buscarFuncionarioPorId(Integer id){
        Optional<Funcionario> byId = repository.findById(id);

        if (byId.isPresent()){
            return byId.get();
        }

        //@ControllerAdvice
        throw new EntidadeNaoEncontradaException("Produto %d não encontrardo.".formatted(id));

    }

    public void removerPorId(int id){

        if (repository.existsById(id)){
            repository.deleteById(id);
        }

        throw new EntidadeNaoEncontradaException("O funcionário não foi encontrado");
    }


}

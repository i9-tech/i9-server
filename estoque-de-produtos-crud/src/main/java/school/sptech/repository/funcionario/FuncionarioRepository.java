package school.sptech.repository.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.funcionario.Funcionario;

import java.util.List;
import java.util.Optional;


public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

   //exemplo de optional e list
   // optional = retorna 0 ou 1 usuario da fkempresa informada
   // list = retorna lista fazia ou com funcionarios
    Optional<Funcionario> findByIdAndFkEmpresa(int id, int fkEmpresa);
    List<Funcionario> findByFkEmpresa(int fkEmpresa);
    boolean existsByCpfIgnoreCaseAndFkEmpresa(String cpf, int fk);
    boolean existsByIdAndFkEmpresa(Integer id, int fkEmpresa);
    void deleteById(Funcionario funcionario);

}

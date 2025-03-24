package school.sptech.repository.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.funcionario.Funcionario;

import java.util.List;


public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    List<Funcionario> findByFkEmpresa(int fkEmpresa);

   // List<Funcionario> findByCargoContainingIgnoreCase(String cargo);
}

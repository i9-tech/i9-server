package school.sptech.repository.colaborador;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.funcionario.Funcionario;

import java.util.List;


public interface ColaboradorRepository extends JpaRepository<Funcionario, Integer> {

    List<Funcionario> findByFkEmpresa(int fkEmpresa);

    List<Funcionario> findByCargoContainingIgnoreCase(String cargo);
}

package school.sptech.repository.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.empresa.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

}

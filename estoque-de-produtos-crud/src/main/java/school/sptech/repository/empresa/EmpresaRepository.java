package school.sptech.repository.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.empresa.Empresa;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

}

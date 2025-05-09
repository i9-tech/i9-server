package school.sptech.repository.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.empresa.Empresa;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    @Query("SELECT e.ativo FROM Empresa e WHERE e.id = :idEmpresa")
    Boolean verificarEmpresaAtivaPorId(@Param("idEmpresa") Integer idEmpresa);

}

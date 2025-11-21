package school.sptech.repository.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.empresa.Empresa;
import school.sptech.service.empresa.port.EmpresaPort;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer>, EmpresaPort {

    @Override
    @Query("SELECT e.ativo FROM Empresa e WHERE e.id = :idEmpresa")
    Boolean verificarEmpresaAtivaPorId(@Param("idEmpresa") Integer idEmpresa);

    @Override
    Optional<Empresa> findById(Integer id);

    @Override
    List<Empresa> findAll();

    @Override
    boolean existsById(Integer id);



    @Override
    List<Empresa> findByAtivoTrue();
}

/*
 O repositório irá atuar como adaptador, ou seja, implementa
 nosso contrato (PORTA) usando JPA.

ADAPTADOR: implementa a porta usando JPA
 */
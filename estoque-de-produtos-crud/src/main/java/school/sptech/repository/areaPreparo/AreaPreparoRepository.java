package school.sptech.repository.areaPreparo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.areaPreparo.AreaPreparo;
import java.util.List;
import java.util.Optional;

public interface AreaPreparoRepository  extends JpaRepository<AreaPreparo, Integer> {

    @Query("select c from AreaPreparo c join c.funcionario f where f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario)")
    List<AreaPreparo> buscarCategoriasDaEmpresaDoFuncionario(@Param("idFuncionario") Integer idFuncionario);

    @Query("select c from AreaPreparo c join c.funcionario f where c.id = :idArea and f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario)")
    Optional<AreaPreparo> buscarAreaPreparoPorIdDoFuncionarioDaEmpresa(@Param("idArea") Integer idArea, @Param("idFuncionario") Integer idFuncionario);

    @Query("SELECT e.ativo FROM Empresa e WHERE e = (SELECT f.empresa  FROM Funcionario f WHERE f.id = :idFuncionario)")
    Boolean verificarEmpresaAtivaPorFuncionarioId(@Param("idFuncionario") Integer idFuncionario);

    @Modifying
    @Query("UPDATE Prato p SET p.areaPreparo = NULL WHERE p.areaPreparo.id = :areaId")
    void desvincularPratosArea(@Param("areaId") Integer areaId);

    @Modifying
    @Query("DELETE FROM AreaPreparo c WHERE c.id = :areaId")
    void deleteAreaPreparoById(@Param("areaId") Integer areaId);

}

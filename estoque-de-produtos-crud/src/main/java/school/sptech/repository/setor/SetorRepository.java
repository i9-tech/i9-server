package school.sptech.repository.setor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.setor.Setor;

import java.util.List;
import java.util.Optional;

public interface SetorRepository extends JpaRepository<Setor, Integer> {

    @Query("select s from Setor s join s.funcionario f where f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario)")
    List<Setor> buscarSetorsDaEmpresaDoFuncionario(@Param("idFuncionario") Integer idFuncionario);

    @Query("select s from Setor s join s.funcionario f where s.id = :idSetor and f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario)")
    Optional<Setor> buscarSetorPorIdDoFuncionarioDaEmpresa(@Param("idSetor") Integer idSetor, @Param("idFuncionario") Integer idFuncionario);

}

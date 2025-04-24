package school.sptech.repository.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.categoria.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    @Query("select c from Categoria c join c.funcionario f where f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario)")
    List<Categoria> buscarCategoriasDaEmpresaDoFuncionario(@Param("idFuncionario") Integer idFuncionario);

    @Query("select c from Categoria c join c.funcionario f where c.id = :idCategoria and f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario)")
    Optional<Categoria> buscarCategoriaPorIdDoFuncionarioDaEmpresa(@Param("idCategoria") Integer idCategoria, @Param("idFuncionario") Integer idFuncionario);
}

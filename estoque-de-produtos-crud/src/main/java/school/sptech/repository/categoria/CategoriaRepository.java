package school.sptech.repository.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("SELECT e.ativo FROM Empresa e WHERE e = (SELECT f.empresa  FROM Funcionario f WHERE f.id = :idFuncionario)")
    Boolean verificarEmpresaAtivaPorFuncionarioId(@Param("idFuncionario") Integer idFuncionario);

    @Modifying
    @Query("UPDATE Prato p SET p.categoria = NULL WHERE p.categoria.id = :categoriaId")
    void desvincularPratosDaCategoria(@Param("categoriaId") Integer categoriaId);

    @Modifying
    @Query("UPDATE Produto p SET p.categoria = NULL WHERE p.categoria.id = :categoriaId")
    void desvincularProdutosDaCategoria(@Param("categoriaId") Integer categoriaId);

    @Modifying
    @Query("DELETE FROM Categoria c WHERE c.id = :id")
    void deleteCategoriaById(@Param("id") Integer id);

}

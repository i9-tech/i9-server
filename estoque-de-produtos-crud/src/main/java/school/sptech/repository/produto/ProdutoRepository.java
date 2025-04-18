package school.sptech.repository.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.produto.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("SELECT p FROM Produto p JOIN p.funcionario f WHERE f.empresa = (SELECT f2.empresa FROM Funcionario f2 WHERE f2.id = :idFuncionario)")
    List<Produto> buscarProdutosDaEmpresaDoFuncionario(@Param("idFuncionario") Integer idFuncionario);

    Optional<Produto> findById(Integer id);

    @Query("SELECT produto FROM Produto produto " +
            "JOIN produto.funcionario funcionarioProduto " +
            "WHERE produto.id = :id AND funcionarioProduto.empresa = (" +
            "SELECT funcionarioParametro.empresa FROM Funcionario funcionarioParametro " +
            "WHERE funcionarioParametro.id = :idFuncionario)")
    Optional<Produto> buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(@Param("id") Integer id,
                                                                                       @Param("idFuncionario") Integer idFuncionario);

}

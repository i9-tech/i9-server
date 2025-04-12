package school.sptech.repository.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.produto.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("select produto from Produto produto join produto.funcionario funcionario where funcionario.fkEmpresa = (select funcionario2.fkEmpresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    List<Produto> buscarProdutosDaEmpresaDoFuncionario(@Param("idFuncionario") Integer idFuncionario);

    Optional<Produto> findById(Integer id);

    @Query("select produto from Produto produto join produto.funcionario funcionarioProduto where produto.id = :id and funcionarioProduto.fkEmpresa = ( select funcionarioParametro.fkEmpresa from Funcionario funcionarioParametro where funcionarioParametro.id = :idFuncionario)")
    Optional<Produto> buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(@Param("id") Integer id, @Param("idFuncionario") Integer idFuncionario);

}

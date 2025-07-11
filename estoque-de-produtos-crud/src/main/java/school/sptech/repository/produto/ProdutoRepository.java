package school.sptech.repository.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.produto.Produto;
import school.sptech.entity.setor.Setor;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("select produto from Produto produto join produto.funcionario funcionario where funcionario.empresa = (select funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    List<Produto> buscarProdutosDaEmpresaDoFuncionario(@Param("idFuncionario") Integer idFuncionario);

    Optional<Produto> findById(Integer id);

    @Query("select produto from Produto produto join produto.funcionario funcionarioProduto where produto.id = :id and funcionarioProduto.empresa = ( select funcionarioParametro.empresa from Funcionario funcionarioParametro where funcionarioParametro.id = :idFuncionario)")
    Optional<Produto> buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(@Param("id") Integer id, @Param("idFuncionario") Integer idFuncionario);

    @Query("select sum(produto.quantidade * produto.valorCompra) from Produto produto join produto.funcionario funcionario where funcionario.empresa = (select funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Double valorTotalProdutosEstoqueEmpresa(@Param("idFuncionario") Integer idFuncionario);

    @Query("select sum(produto.quantidade * produto.valorUnitario) from Produto produto join produto.funcionario funcionario where funcionario.empresa = (SELECT funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Double lucroTotalProdutosEstoqueEmpresa(@Param("idFuncionario") Integer idFuncionario);

    @Query("select sum(produto.quantidade) from Produto produto join produto.funcionario funcionario where funcionario.empresa = (select funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Integer quantidadeProdutoEstoqueEmpresa(@Param("idFuncionario")  Integer idFuncionario);

    @Query("select count(produto) from Produto produto join produto.funcionario funcionario where produto.quantidade < produto.quantidadeMin and funcionario.empresa = (select funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Integer quantidadeProdutoEstoqueBaixoEmpresa(@Param("idFuncionario") Integer idFuncionario);

    @Query("select count(produto) from Produto produto join produto.funcionario funcionario where produto.quantidade > produto.quantidadeMax and funcionario.empresa = (select funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Integer quantidadeProdutoEstoqueAltoEmpresa(Integer idFuncionario);

    @Query("select produto from Produto produto join produto.funcionario funcionarioProduto where produto.categoria.id = :categoriaId and funcionarioProduto.empresa = ( select funcionarioParametro.empresa from Funcionario funcionarioParametro where funcionarioParametro.id = :idFuncionario)")
    List<Produto> listarProdutoPorCategoriaEmpresa(@Param("categoriaId") Integer categoriaId, @Param("idFuncionario") Integer idFuncionario);

    @Query("select produto from Produto produto join produto.funcionario funcionarioProduto where produto.setor.id = :setorId and funcionarioProduto.empresa = ( select funcionarioParametro.empresa from Funcionario funcionarioParametro where funcionarioParametro.id = :idFuncionario)")
    List<Produto> listarProdutoPorSetorEmpresa(@Param("setorId") Integer setorId, @Param("idFuncionario") Integer idFuncionario);

    @Query("select produto from Produto produto join produto.funcionario funcionarioProduto where lower(produto.nome) like lower(concat('%', :nome, '%')) and funcionarioProduto.empresa = (select funcionarioParametro.empresa from Funcionario funcionarioParametro where funcionarioParametro.id = :idFuncionario)")
    List<Produto> listarProdutoPorNomeLikeEmpresa(@Param("nome") String categoria, @Param("idFuncionario") Integer idFuncionario);

    @Modifying
    @Query("update ItemCarrinho ic set ic.produto = null where ic.produto.id = :produtoId")
    void desvincularProdutoDosItens(@Param("produtoId") Integer produtoId);
}

package school.sptech.repository.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    List<Produto> buscarTodosProdutosDaEmpresaDoFuncionario(@Param("idFuncionario") Integer idFuncionario);

    @Query("select produto from Produto produto join produto.funcionario funcionario where funcionario.empresa = (select funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Page<Produto> buscarProdutosDaEmpresaDoFuncionarioPaginado(@Param("idFuncionario") Integer idFuncionario, Pageable pageable);

    @Query("select produto from Produto produto join produto.funcionario funcionario where funcionario.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario) and (:termoBusca is null or lower(produto.nome) like lower(concat('%', :termoBusca, '%'))) and (:setorId is null or produto.setor.id = :setorId) and (:categoriaId is null or produto.categoria.id = :categoriaId) and (:statusEstoque is null or (:statusEstoque = 'baixo' and produto.quantidade < produto.quantidadeMin and produto.quantidade != 0 ) or (:statusEstoque = 'sem' and produto.quantidade = 0) or (:statusEstoque = 'em_estoque' and produto.quantidade > 0))")
    Page<Produto> buscarProdutosDaEmpresaDoFuncionarioPaginadoComFiltro(@Param("idFuncionario") Integer idFuncionario, @Param("termoBusca") String termoBusca, @Param("statusEstoque") String statusEstoque, @Param("setorId") Integer setorId, @Param("categoriaId") Integer categoriaId, Pageable pageable);

    Optional<Produto> findById(Integer id);

    @Query("select produto from Produto produto join produto.funcionario funcionarioProduto where produto.id = :id and funcionarioProduto.empresa = ( select funcionarioParametro.empresa from Funcionario funcionarioParametro where funcionarioParametro.id = :idFuncionario)")
    Optional<Produto> buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(@Param("id") Integer id, @Param("idFuncionario") Integer idFuncionario);

    @Query("select sum(produto.quantidade * produto.valorCompra) from Produto produto join produto.funcionario funcionario where funcionario.empresa = (select funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Double valorTotalProdutosEstoqueEmpresa(@Param("idFuncionario") Integer idFuncionario);

    @Query("select sum(produto.quantidade * produto.valorUnitario) from Produto produto join produto.funcionario funcionario where funcionario.empresa = (SELECT funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Double lucroBrutoTotalProdutosEstoqueEmpresa(@Param("idFuncionario") Integer idFuncionario);

    @Query("select sum((produto.valorUnitario - produto.valorCompra) * produto.quantidade)from Produto produto join produto.funcionario funcionario where funcionario.empresa = (SELECT funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Double lucroLiquidoTotalProdutosEstoqueEmpresa(@Param("idFuncionario") Integer idFuncionario);

    @Query("select sum(produto.quantidade) from Produto produto join produto.funcionario funcionario where funcionario.empresa = (select funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Integer quantidadeProdutoEstoqueEmpresa(@Param("idFuncionario")  Integer idFuncionario);

    @Query("select count(produto) from Produto produto join produto.funcionario funcionario where funcionario.empresa = (select funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Integer quantidadeProdutosDiferentesCadastrados(@Param("idFuncionario") Integer idFuncionario);

    @Query("select count(produto) from Produto produto join produto.funcionario funcionario where produto.quantidade < produto.quantidadeMin and  produto.quantidade != 0 and funcionario.empresa = (select funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Integer quantidadeProdutoEstoqueBaixoEmpresa(@Param("idFuncionario") Integer idFuncionario);

    @Query("select count(produto) from Produto produto join produto.funcionario funcionario where produto.quantidade = 0 and funcionario.empresa = (select funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Integer quantidadeProdutoSemEstoqueEmpresa(@Param("idFuncionario") Integer idFuncionario);

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

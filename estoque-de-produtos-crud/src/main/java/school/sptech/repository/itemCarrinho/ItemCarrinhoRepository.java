package school.sptech.repository.itemCarrinho;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.itemCarrinho.ItemCarrinho;

import java.util.List;
import java.util.Optional;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Integer> {

    @Query("SELECT ic FROM ItemCarrinho ic JOIN ic.funcionario f WHERE ic.venda = :venda AND f.empresa = (SELECT f2.empresa FROM Funcionario f2 WHERE f2.id = :idFuncionario)")
    List<ItemCarrinho> buscarItensCarrinhoPorVendaEFuncionario(@Param("venda") String venda, @Param("idFuncionario") Integer idFuncionario);

    @Query("SELECT ic FROM ItemCarrinho ic WHERE ic.id = :id AND ic.venda = :venda")
    Optional<ItemCarrinho> buscarItemPorIdEVenda(@Param("id") Integer id, @Param("venda") String venda);

    @Modifying
    @Query("UPDATE ItemCarrinho ic SET ic.prato = NULL WHERE ic.prato.id = :pratoId")
    void desvincularPratoDosItens(@Param("pratoId") Integer pratoId);

    @Modifying
    @Query("UPDATE ItemCarrinho ic SET ic.produto = NULL WHERE ic.produto.id = :produtoId")
    void desvincularProdutoDosItens(@Param("produtoId") Integer produtoId);
}

package school.sptech.repository.venda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.sptech.entity.venda.Venda;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {

    @Query("select venda from Venda venda where venda.dataVenda = :dataVenda")
    List<Venda> findAllByDataVenda(LocalDate dataVenda);


    @Query("SELECT v FROM Venda v WHERE v.dataVenda = :dataVenda AND v.funcionario.empresa.id = :idEmpresa")
    List<Venda> findByDataVendaAndEmpresaId(@Param("dataVenda") LocalDate dataVenda,
                                            @Param("idEmpresa") Integer idEmpresa);


    @Query("SELECT SUM(v.valorTotal) FROM Venda v " +
            "WHERE v.funcionario.empresa.id = :empresaId " +
            "AND v.dataVenda = :data")
    Double valorTotalVendasPorEmpresaEData(@Param("empresaId") Integer empresaId,
                                        @Param("data") LocalDate data);

    @Query(value = """
            SELECT\s
                                                SUM(
                                                    CASE\s
                                                        WHEN ic.prato_id IS NOT NULL THEN p.valor_venda
                                                        WHEN ic.produto_id IS NOT NULL THEN pr.valor_unitario - pr.valor_compra
                                                        ELSE 0
                                                    END
                                                ) AS lucro_liquido
                                            FROM venda v
                                            JOIN funcionario f ON v.fk_funcionario = f.id
                                            JOIN venda_itens_carrinho vic ON vic.venda_id = v.id
                                            JOIN item_carrinho ic ON ic.id = vic.itens_carrinho_id
                                            LEFT JOIN prato p ON p.id = ic.prato_id
                                            LEFT JOIN produto pr ON pr.id = ic.produto_id
                                            WHERE f.empresa_id = :empresaId
                                              AND v.data_venda = :hoje
                                            
            
    """, nativeQuery = true)
    Double calcularLucroLiquidoPorEmpresaEData(@Param("hoje") LocalDate hoje, @Param("empresaId") Integer empresaId);



    @Query(value = """
        SELECT 
            COALESCE(sp.nome, spr.nome) AS setor,
            SUM(ic.valor_unitario) AS total_valor
        FROM venda v
        JOIN venda_itens_carrinho vic ON vic.venda_id = v.id
        JOIN item_carrinho ic ON ic.id = vic.itens_carrinho_id
        LEFT JOIN prato p ON p.id = ic.prato_id
        LEFT JOIN setor sp ON sp.id = p.setor_id
        LEFT JOIN produto prod ON prod.id = ic.produto_id
        LEFT JOIN setor spr ON spr.id = prod.setor_id
        JOIN funcionario f ON f.id = v.fk_funcionario
        JOIN empresa e ON e.id = f.empresa_id
        WHERE e.id = :empresaId
          AND v.data_venda = :hoje
        GROUP BY COALESCE(sp.nome, spr.nome)
        """, nativeQuery = true)
    List<Object[]> valorTotalDiarioPorSetorEmpresa(
            @Param("empresaId") Integer empresaId,
            @Param("hoje") LocalDate hoje);


    @Query(value = """
SELECT
    COALESCE(cp.nome, cpr.nome) AS categoria,
    SUM(ic.valor_unitario) AS total_valor
FROM venda v
JOIN venda_itens_carrinho vic ON vic.venda_id = v.id
JOIN item_carrinho ic ON ic.id = vic.itens_carrinho_id
LEFT JOIN prato p ON p.id = ic.prato_id
LEFT JOIN categoria cp ON cp.id = p.categoria_id
LEFT JOIN produto prod ON prod.id = ic.produto_id
LEFT JOIN categoria cpr ON cpr.id = prod.categoria_id
JOIN funcionario f ON f.id = v.fk_funcionario
JOIN empresa e ON e.id = f.empresa_id
        WHERE e.id = :empresaId
          AND v.data_venda = :hoje
        GROUP BY COALESCE(cp.nome, cpr.nome)
        """, nativeQuery = true)
    List<Object[]> valorTotalDiarioPorCategoriaEmpresa(
            @Param("empresaId") Integer empresaId,
            @Param("hoje") LocalDate hoje);


    @Query("SELECT COUNT(v) FROM Venda v WHERE v.vendaConcluida = true AND v.dataVenda = :hoje AND v.funcionario.empresa.id = :empresaId")
    Integer contarVendasConcluidasPorEmpresaEData(@Param("empresaId") Integer empresaId, @Param("hoje") LocalDate hoje);

}

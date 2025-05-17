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



    @Query("select sum(v.valorTotal) from Venda v where v.funcionario.empresa.id = :empresaId and v.dataVenda = :data")
    Double valorTotalVendasPorEmpresaEData(@Param("empresaId") Integer empresaId, @Param("data") LocalDate data);

    @Query("""
    select
        sum(
            case
                when ic.prato is not null then p.valorVenda
                WHEN ic.produto is not null then pr.valorUnitario - pr.valorCompra
                else 0
            end
        ) as lucroLiquido
    from Venda v
    join v.funcionario f
    join v.itensCarrinho ic
    left join ic.prato p
    left join ic.produto pr
    where f.empresa.id = :empresaId
      and v.dataVenda = :hoje
    """)
    Double calcularLucroLiquidoPorEmpresaEData(@Param("empresaId") Integer empresaId, @Param("hoje") LocalDate hoje);


    @Query(""" 
    select coalesce(sp.nome, spr.nome), sum(ic.valorUnitario) from Venda v
    join v.itensCarrinho ic
    left join ic.prato p
    left join p.setor sp
    left join ic.produto prod
    left join prod.setor spr
    join v.funcionario f
    join f.empresa e
    where e.id = :empresaId
      and v.dataVenda = :hoje
    group by coalesce(sp.nome, spr.nome)
    """)
    List<Object[]> valorTotalDiarioPorSetorEmpresa(@Param("empresaId") Integer empresaId, @Param("hoje") LocalDate hoje);


    @Query(""" 
    select coalesce(cp.nome, cpr.nome), sum(ic.valorUnitario) from Venda v
    join v.itensCarrinho ic
    left join ic.prato p
    left join p.categoria cp
    left join ic.produto prod
    left join prod.categoria cpr
    join v.funcionario f
    join f.empresa e
    where e.id = :empresaId
      and v.dataVenda = :hoje
    group by coalesce(cp.nome, cpr.nome)
    """)
    List<Object[]> valorTotalDiarioPorCategoriaEmpresa(@Param("empresaId") Integer empresaId, @Param("hoje") LocalDate hoje);


    @Query("select count(v) from Venda v where v.funcionario.empresa.id = :empresaId and v.dataVenda = :hoje")
    Integer contarVendasConcluidasPorEmpresaEData(@Param("empresaId") Integer empresaId, @Param("hoje") LocalDate hoje);

}

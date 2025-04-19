package school.sptech.repository.venda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.sptech.entity.venda.Venda;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {
    List<Venda> findByVendaConcluidaTrue(Boolean vendaConcluida);

    List<Venda> findByVendaConcluidaFalse(Boolean vendaConcluida);

    @Query("SELECT v FROM Venda v JOIN v.itensCarrinho ic WHERE ic.id = :itemCarrinhoId")
    List<Venda> findByItemCarrinhoId(Integer itemCarrinhoId);
}

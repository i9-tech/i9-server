package school.sptech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.entity.venda.Venda;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {

    List<Venda> findByMesa(String mesa);

    List<Venda> findByDataVenda(LocalDate dataVenda);

    List<Venda> findByVendaConcluida(Boolean vendaConcluida);


}

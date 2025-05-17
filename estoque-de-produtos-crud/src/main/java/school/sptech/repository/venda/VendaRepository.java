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
}

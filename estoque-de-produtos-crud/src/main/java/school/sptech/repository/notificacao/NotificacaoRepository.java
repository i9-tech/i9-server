package school.sptech.repository.notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.notificacao.Notificacao;
import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {

    List<Notificacao> findByLidaFalseOrderByDataCriacaoDesc();

    List<Notificacao> findByEmpresaIdOrderByDataCriacaoDesc(Integer empresaId);

}
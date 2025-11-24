package school.sptech.repository.plano;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.plano.GerenciamentoPlano;
import java.util.Optional;

public interface GerenciamentoPlanoRepository extends JpaRepository<GerenciamentoPlano, Integer> {
    Optional<GerenciamentoPlano> findByEmpresaId(Integer empresaId);
}
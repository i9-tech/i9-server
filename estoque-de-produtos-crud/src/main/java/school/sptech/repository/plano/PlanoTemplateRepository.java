package school.sptech.repository.plano;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.plano.PlanoTemplate;
import java.util.Optional;

public interface PlanoTemplateRepository extends JpaRepository<PlanoTemplate, Integer> {
    Optional<PlanoTemplate> findByTipo(String tipo);

}
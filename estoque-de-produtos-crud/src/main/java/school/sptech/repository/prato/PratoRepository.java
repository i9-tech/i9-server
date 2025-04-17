package school.sptech.repository.prato;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.prato.Prato;

import java.util.List;

public interface PratoRepository extends JpaRepository<Prato, Integer> {
    List<Prato> findByNomeContainingIgnoreCase(String nome);
}

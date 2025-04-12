package school.sptech.repository.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.categoria.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}

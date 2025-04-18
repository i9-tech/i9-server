package school.sptech.repository.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.categoria.Categoria;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    //TENTATIVA POR URI AO DIGITAR
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
}

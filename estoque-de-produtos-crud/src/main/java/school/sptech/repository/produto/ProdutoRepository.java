package school.sptech.repository.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.produto.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByFkEmpresa(int fkEmpresa);
    List<Produto> findByNomeProdutoContainingIgnoreCaseAndFkEmpresa(String categoria, int fkEmpresa);
    List<Produto> findBySetorAlimenticioContainingIgnoreCaseAndFkEmpresa(String setorAlimenticio, int fkEmpresa);
    List<Produto> findByCategoriaContainingIgnoreCaseAndFkEmpresa(String categoria, int fkEmpresa);

    Optional<Produto> findByIdAndFkEmpresa(int id, int fkEmpresa);

    void deleteById(Produto produto);
}

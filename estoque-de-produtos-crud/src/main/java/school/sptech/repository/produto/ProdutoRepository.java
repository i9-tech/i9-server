package school.sptech.repository.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.produto.Produto;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {


    List<Produto> findByFkEmpresa(int fkEmpresa);
    List<Produto> findByNomeProdutoContainingIgnoreCaseAndFkEmpresa(String categoria, int fkEmpresa);
    List<Produto> findBySetorAlimenticioContainingIgnoreCaseAndFkEmpresa(String setorAlimenticio, int fkEmpresa);
    List<Produto> findByCategoriaContainingIgnoreCaseAndFkEmpresa(String categoria, int fkEmpresa);
}

package school.sptech.estoque_de_produtos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    List<Produto> findByNomeProdutoContainingIgnoreCase(String nomeProduto);
    List<Produto> findByCategoriaContainingIgnoreCase(String categoria);
    List<Produto> findBySetorAlimenticioContainingIgnoreCase(String setorAlimenticio);

    List<Produto> findByQuantidadeLessThan(double quantidadeMin);
}

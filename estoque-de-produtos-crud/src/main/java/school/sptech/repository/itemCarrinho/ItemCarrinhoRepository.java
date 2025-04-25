package school.sptech.repository.itemCarrinho;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.entity.itemCarrinho.ItemCarrinho;

import java.util.List;


public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Integer> {

    List<ItemCarrinho> findAllById(List<Integer> ids);
}


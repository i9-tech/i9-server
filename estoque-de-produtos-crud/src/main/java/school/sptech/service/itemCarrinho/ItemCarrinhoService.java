package school.sptech.service.itemCarrinho;

import org.springframework.stereotype.Service;
import school.sptech.repository.itemCarrinho.ItemCarrinhoRepository;

@Service
public class ItemCarrinhoService {

    private final ItemCarrinhoRepository itemCarrinhoRepository;


    public ItemCarrinhoService(ItemCarrinhoRepository itemCarrinhoRepository) {
        this.itemCarrinhoRepository = itemCarrinhoRepository;
    }
}

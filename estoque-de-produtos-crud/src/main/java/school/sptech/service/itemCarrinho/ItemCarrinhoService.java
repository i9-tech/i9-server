package school.sptech.service.itemCarrinho;

import org.springframework.stereotype.Service;
import school.sptech.entity.itemCarrinho.ItemCarrinho;
import school.sptech.repository.itemCarrinho.ItemCarrinhoRepository;

import java.util.List;

@Service
public class ItemCarrinhoService {

    private final ItemCarrinhoRepository itemCarrinhoRepository;

    public ItemCarrinho adicionarPrato(ItemCarrinho prato) {
        prato.setProduto(null);
        prato.setObservacao(null);
        return itemCarrinhoRepository.save(prato);
    }

    public ItemCarrinho adicionarProduto(ItemCarrinho produto) {
        produto.setPrato(null);
        return itemCarrinhoRepository.save(produto);
    }

    public void removerItem(Integer id) {
        itemCarrinhoRepository.deleteById(id);
    }

    public List<ItemCarrinho> listarItens() {
        return itemCarrinhoRepository.findAll();
    }

    public ItemCarrinhoService(ItemCarrinhoRepository itemCarrinhoRepository) {
        this.itemCarrinhoRepository = itemCarrinhoRepository;
    }
}

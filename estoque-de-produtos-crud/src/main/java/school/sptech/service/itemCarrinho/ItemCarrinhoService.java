package school.sptech.service.itemCarrinho;

import org.springframework.stereotype.Service;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.itemCarrinho.ItemCarrinho;
import school.sptech.entity.prato.Prato;
import school.sptech.entity.produto.Produto;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.itemCarrinho.ItemCarrinhoRepository;
import school.sptech.repository.prato.PratoRepository;
import school.sptech.repository.produto.ProdutoRepository;

import java.util.List;

@Service
public class ItemCarrinhoService {

    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final PratoRepository pratoRepository;
    private final ProdutoRepository produtoRepository;

    public ItemCarrinho adicionarPrato(ItemCarrinho pratoCarrinho, Integer idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        Prato prato = pratoRepository.buscarPratoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(pratoCarrinho.getPrato().getId(), idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Prato não encontrado"));


        pratoCarrinho.setProduto(null);
        pratoCarrinho.setPrato(prato);
        pratoCarrinho.setFuncionario(funcionario);
        pratoCarrinho.setValorUnitario(prato.getValorVenda());

        return itemCarrinhoRepository.save(pratoCarrinho);
    }

    public ItemCarrinho adicionarProduto(ItemCarrinho produtoCarrinho, Integer idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        Produto produto = produtoRepository.buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(produtoCarrinho.getProduto().getId(), idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado"));


        produtoCarrinho.setPrato(null);
        produtoCarrinho.setObservacao(null);
        produtoCarrinho.setProduto(produto);
        produtoCarrinho.setFuncionario(funcionario);
        produtoCarrinho.setValorUnitario(produto.getValorUnitario());
        produto.setQuantidade(produto.getQuantidade() - 1 );

        return itemCarrinhoRepository.save(produtoCarrinho);
    }

    public void removerItem(Integer id, String venda) {
        if (itemCarrinhoRepository.buscarItemPorIdEVenda(id, venda).isEmpty()) {
            throw new EntidadeNaoEncontradaException("Item não existe nesse carrinho!");
        }

        itemCarrinhoRepository.deleteById(id);
    }

    public List<ItemCarrinho> listarItens(String venda, Integer idFuncionario) {
        List<ItemCarrinho> itens = itemCarrinhoRepository.buscarItensCarrinhoPorVendaEFuncionario(venda, idFuncionario);

        System.out.println("Itens encontrados: " + itens.size());

        if (itens.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Não existem itens nesse carrinho!");
        }


        return itens;
    }

    public ItemCarrinhoService(ItemCarrinhoRepository itemCarrinhoRepository, FuncionarioRepository funcionarioRepository, PratoRepository pratoRepository, ProdutoRepository produtoRepository) {
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.pratoRepository = pratoRepository;
        this.produtoRepository = produtoRepository;
    }
}

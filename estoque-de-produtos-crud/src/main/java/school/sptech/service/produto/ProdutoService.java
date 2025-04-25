package school.sptech.service.produto;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.controller.produto.dto.ProdutoEdicaoDto;
import school.sptech.controller.produto.dto.ProdutoMapper;
import school.sptech.controller.produto.dto.ProdutoCadastroDto;
import school.sptech.controller.produto.dto.ProdutoListagemDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.produto.Produto;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.produto.ProdutoRepository;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public ProdutoListagemDto cadastrarProduto(@Valid ProdutoCadastroDto produtoCadastroDto, Integer idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        Produto produto = ProdutoMapper.toEntity(produtoCadastroDto);
        produto.setFuncionario(funcionario);

        Produto produtoCadastrado = repository.save(produto);

        return ProdutoMapper.toDto(produtoCadastrado);
    }

    public List<ProdutoListagemDto> listarProdutoPorEmpresa(Integer idFuncionario) {

        List<Produto> todosProdutosEmpresa = repository.buscarProdutosDaEmpresaDoFuncionario(idFuncionario);
        if (todosProdutosEmpresa.isEmpty()) {
            return Collections.emptyList();
        }

        return todosProdutosEmpresa.stream()
                .map(ProdutoMapper::toDto)
                .collect(Collectors.toList());
    }


    public ProdutoListagemDto editarProduto(Integer id, Integer idFuncionario, @Valid ProdutoEdicaoDto produtoParaEditar) {
        Optional<Produto> produtoPorEmpresaFuncionario = repository.buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(id, idFuncionario);

        if (produtoPorEmpresaFuncionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado ou não pertence à empresa do funcionário informado.");
        }

        Produto produtoExiste = produtoPorEmpresaFuncionario.get();

        produtoExiste.setNome(produtoParaEditar.getNome());
        produtoExiste.setQuantidade(produtoParaEditar.getQuantidade());
        produtoExiste.setDataVencimento(produtoParaEditar.getDataVencimento());
        produtoExiste.setValorCompra(produtoParaEditar.getValorCompra());
        produtoExiste.setValorUnitario(produtoParaEditar.getValorUnitario());
        produtoExiste.setQuantidadeMin(produtoParaEditar.getQuantidadeMin());
        produtoExiste.setQuantidadeMax(produtoParaEditar.getQuantidadeMax());
        produtoExiste.setDescricao(produtoParaEditar.getDescricao());
        produtoExiste.setCategoria(produtoParaEditar.getCategoria());
        produtoExiste.setSetor(produtoParaEditar.getSetor());
        produtoExiste.setDataRegistro(produtoParaEditar.getDataRegistro());

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        produtoExiste.setFuncionario(funcionario);

        ProdutoMapper.atualizarProdutoComDto(produtoExiste, produtoParaEditar);
        return ProdutoMapper.toDto(repository.save(produtoExiste));
    }


    public void removerPorId(Integer id, Integer idFuncionario) {
        Optional<Produto> produto = repository.buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(id, idFuncionario);

        if (produto.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado ou não pertence à empresa do funcionário informado.");
        }

        repository.delete(produto.get());
    }


    public Double valorTotalEstoqueProduto(Integer idFuncionario) {
        Double valorTotal = repository.valorTotalProdutosEstoqueEmpresa(idFuncionario);

        if (valorTotal == null) {
            valorTotal = 0.0;
        }
        return valorTotal;
    }

    public Double lucroPrevistoEstoqueProduto(Integer idFuncionario) {
        Double valorTotal = repository.lucroTotalProdutosEstoqueEmpresa(idFuncionario);
        if (valorTotal == null) {
            valorTotal = 0.0;
        }

        return valorTotal;
    }


    public Integer quantidadeProdutoEstoque(Integer idFuncionario) {
        Integer quantidadeProduto = repository.quantidadeProdutoEstoqueEmpresa(idFuncionario);

        if (quantidadeProduto == null) {
            quantidadeProduto = 0;
        }
        return quantidadeProduto;
    }

    public Integer quantidadeProdutoEstoqueBaixo(Integer idFuncionario) {
        Integer quantidadeProduto = repository.quantidadeProdutoEstoqueBaixoEmpresa(idFuncionario);

        if (quantidadeProduto == null) {
            quantidadeProduto = 0;
        }
        return quantidadeProduto;
    }

    public Integer quantidadeProdutoEstoqueAlto(Integer idFuncionario) {
        Integer quantidadeProduto = repository.quantidadeProdutoEstoqueAltoEmpresa(idFuncionario);

        if (quantidadeProduto == null) {
            quantidadeProduto = 0;
        }
        return quantidadeProduto;
    }

    public List<ProdutoListagemDto> listarProdutoPorCategoriaEmpresa(String categoria, Integer idFuncionario) {
        List<Produto> produtosCategoriaEmpresa = repository.listarProdutoPorCategoriaEmpresa(categoria, idFuncionario);
        if (produtosCategoriaEmpresa.isEmpty()) {
            return Collections.emptyList();
        }

        return produtosCategoriaEmpresa.stream()
                .map(ProdutoMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProdutoListagemDto> listarProdutoPorSetorEmpresa(String setor, Integer idFuncionario) {
        List<Produto> produtosCategoriaEmpresa = repository.listarProdutoPorSetorEmpresa(setor, idFuncionario);
        if (produtosCategoriaEmpresa.isEmpty()) {
            return Collections.emptyList();
        }

        return produtosCategoriaEmpresa.stream()
                .map(ProdutoMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProdutoListagemDto> listarProdutoPorNomeEmpresa(String nome, Integer idFuncionario) {
        List<Produto> produtosNomeEmpresa = repository.listarProdutoPorNomeLikeEmpresa(nome, idFuncionario);
        if (produtosNomeEmpresa.isEmpty()) {
            return Collections.emptyList();
        }

        return produtosNomeEmpresa.stream()
                .map(ProdutoMapper::toDto)
                .collect(Collectors.toList());
    }

}

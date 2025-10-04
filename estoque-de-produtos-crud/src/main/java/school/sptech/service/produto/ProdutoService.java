package school.sptech.service.produto;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import school.sptech.controller.produto.dto.ProdutoEdicaoDto;
import school.sptech.controller.produto.dto.ProdutoMapper;
import school.sptech.controller.produto.dto.ProdutoCadastroDto;
import school.sptech.controller.produto.dto.ProdutoListagemDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.produto.Produto;
import school.sptech.entity.setor.Setor;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.produto.ProdutoRepository;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    private final FuncionarioRepository funcionarioRepository;

    public ProdutoService(ProdutoRepository repository, FuncionarioRepository funcionarioRepository) {
        this.repository = repository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public ProdutoListagemDto cadastrarProduto(@Valid ProdutoCadastroDto produtoCadastroDto, Integer idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        Produto produto = ProdutoMapper.toEntity(produtoCadastroDto);
        produto.setFuncionario(funcionario);

        Produto produtoCadastrado = repository.save(produto);

        return ProdutoMapper.toDto(produtoCadastrado);
    }

    public ProdutoListagemDto buscarProdutoPorId(Integer id, Integer idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        Optional<Produto> produtoPorEmpresaFuncionario = repository.buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(id, idFuncionario);

        if (produtoPorEmpresaFuncionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado ou não pertence à empresa do funcionário informado.");
        }
        ProdutoListagemDto respostaDto = ProdutoMapper.toDto(produtoPorEmpresaFuncionario.get());

        return respostaDto;
    }

    public List<ProdutoListagemDto> listarTodosProdutoPorEmpresa(Integer idFuncionario) {

        List<Produto> todosProdutosEmpresa = repository.buscarTodosProdutosDaEmpresaDoFuncionario(idFuncionario);
        if (todosProdutosEmpresa.isEmpty()) {
            return Collections.emptyList();
        }

        return todosProdutosEmpresa.stream()
                .map(ProdutoMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<ProdutoListagemDto> listarProdutoPorEmpresaPaginado(Integer idFuncionario,  int pagina, int quantidadePorPagina, String ordem, String termoBusca, String statusEstoque, Integer setorSelecionado, Integer categoriaSelecionada) {
        Sort ordenacao = Sort.by("nome");
        ordenacao = ordem.equalsIgnoreCase("desc") ? ordenacao.descending() : ordenacao.ascending();
        Pageable pageable = PageRequest.of(pagina, quantidadePorPagina, ordenacao);


        if ((termoBusca == null || termoBusca.isEmpty()) && (statusEstoque == null || statusEstoque.isEmpty()) && setorSelecionado== null && categoriaSelecionada == null) {
            Page<Produto> produtos = repository.buscarProdutosDaEmpresaDoFuncionarioPaginado(idFuncionario, pageable);
            return produtos.map(ProdutoMapper::toDto);
        }

        Page<Produto> produtosFiltrados = repository.buscarProdutosDaEmpresaDoFuncionarioPaginadoComFiltro(idFuncionario, termoBusca, statusEstoque, setorSelecionado, categoriaSelecionada, pageable);
        return produtosFiltrados.map(ProdutoMapper::toDto);
    }


    public ProdutoListagemDto editarProduto(Integer id, Integer idFuncionario, @Valid ProdutoEdicaoDto produtoParaEditar) {
        Optional<Produto> produtoPorEmpresaFuncionario = repository.buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(id, idFuncionario);

        if (produtoPorEmpresaFuncionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado ou não pertence à empresa do funcionário informado.");
        }

        Produto produtoExiste = produtoPorEmpresaFuncionario.get();

        produtoExiste.setNome(produtoParaEditar.getNome());
        produtoExiste.setQuantidade(produtoParaEditar.getQuantidade());
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


    @Transactional
    public void removerPorId(Integer id, Integer idFuncionario) {
        Optional<Produto> produto = repository.buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(id, idFuncionario);

        if (produto.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado ou não pertence à empresa do funcionário informado.");
        }

        repository.desvincularProdutoDosItens(id);
        repository.delete(produto.get());
    }


    public Double valorTotalEstoqueProduto(Integer idFuncionario) {
        Double valorTotal = repository.valorTotalProdutosEstoqueEmpresa(idFuncionario);

        if (valorTotal == null) {
            valorTotal = 0.0;
        }
        return valorTotal;
    }

    public Double lucroBrutoPrevistoEstoqueProduto(Integer idFuncionario) {
        Double valorTotal = repository.lucroBrutoTotalProdutosEstoqueEmpresa(idFuncionario);
        if (valorTotal == null) {
            valorTotal = 0.0;
        }

        return valorTotal;
    }

    public Double lucroLiquidoPrevistoEstoqueProduto(Integer idFuncionario) {
        Double valorTotal = repository.lucroLiquidoTotalProdutosEstoqueEmpresa(idFuncionario);
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

    public Integer quantidadeProdutosDiferentesCadastrados(Integer idFuncionario) {
        Integer quantidadeProdutos = repository.quantidadeProdutosDiferentesCadastrados(idFuncionario);

        if (quantidadeProdutos == null) {
            quantidadeProdutos = 0;
        }
        return quantidadeProdutos;
    }

    public Integer quantidadeProdutoEstoqueBaixo(Integer idFuncionario) {
        Integer quantidadeProduto = repository.quantidadeProdutoEstoqueBaixoEmpresa(idFuncionario);

        if (quantidadeProduto == null) {
            quantidadeProduto = 0;
        }
        return quantidadeProduto;
    }

    public Integer quantidadeProdutoSemEstoque(Integer idFuncionario) {
        Integer quantidadeProduto = repository.quantidadeProdutoSemEstoqueEmpresa(idFuncionario);

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

    public List<ProdutoListagemDto> listarProdutoPorCategoriaEmpresa(Integer categoriaId, Integer idFuncionario) {
        List<Produto> produtosCategoriaEmpresa = repository.listarProdutoPorCategoriaEmpresa(categoriaId, idFuncionario);
        if (produtosCategoriaEmpresa.isEmpty()) {
            return Collections.emptyList();
        }

        return produtosCategoriaEmpresa.stream()
                .map(ProdutoMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProdutoListagemDto> listarProdutoPorSetorEmpresa(Integer setorId, Integer idFuncionario) {
        List<Produto> produtosCategoriaEmpresa = repository.listarProdutoPorSetorEmpresa(setorId, idFuncionario);
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

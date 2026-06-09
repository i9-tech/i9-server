package school.sptech.service.produto;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.controller.images.EventoProcessamentoImagemDto;
import school.sptech.controller.produto.dto.ProdutoEdicaoDto;
import school.sptech.controller.produto.dto.ProdutoMapper;
import school.sptech.controller.produto.dto.ProdutoCadastroDto;
import school.sptech.controller.produto.dto.ProdutoListagemDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.prato.Prato;
import school.sptech.entity.produto.Produto;
import school.sptech.entity.setor.Setor;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.observer.NotificacaoEstoqueEvent;
import school.sptech.observer.enums.TipoEventoEstoque;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.produto.ProdutoRepository;
import school.sptech.service.imagesService.ImagemProducer;


import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    private final FuncionarioRepository funcionarioRepository;

    private final ImagemProducer rabbitMQProducerService;


    public ProdutoService(ProdutoRepository repository, FuncionarioRepository funcionarioRepository, ImagemProducer rabbitMQProducerService) {
        this.repository = repository;
        this.funcionarioRepository = funcionarioRepository;
        this.rabbitMQProducerService = rabbitMQProducerService;
    }

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @CacheEvict(value = "listaProdutos", key = "#idFuncionario")
    public ProdutoListagemDto cadastrarProduto(@Valid ProdutoCadastroDto produtoCadastroDto, MultipartFile imagem, Integer idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        Produto produto = ProdutoMapper.toEntity(produtoCadastroDto);
        produto.setFuncionario(funcionario);

        Produto produtoCadastrado = repository.save(produto);

        // Gatilho: Produto recém-cadastrado
        eventPublisher.publishEvent(new NotificacaoEstoqueEvent(
                produtoCadastrado,
                TipoEventoEstoque.CADASTRADO,
                "O produto " + produtoCadastroDto.getNome() + " foi adicionado ao estoque com sucesso, e já pode ser vendido na tela da Atendimento!",
                produtoCadastrado.getFuncionario().getEmpresa().getId()
        ));

        if (produtoCadastrado.getQuantidade() == 0) {
            eventPublisher.publishEvent(new NotificacaoEstoqueEvent(
                    produtoCadastrado,
                    TipoEventoEstoque.ZERADO,
                    "O produto " + produtoCadastrado.getNome() + " está zerado no estoque. Reposição necessária.",
                    produtoCadastrado.getFuncionario().getEmpresa().getId()
            ));
        }
        else if (produtoCadastrado.getQuantidade() <= produtoCadastrado.getQuantidadeMin()) {
            eventPublisher.publishEvent(new NotificacaoEstoqueEvent(
                    produtoCadastrado,
                    TipoEventoEstoque.ABAIXO_MINIMO,
                    "O produto \"" + produtoCadastrado.getNome() + " possui apenas " + produtoCadastrado.getQuantidade() + " unidades disponíveis. Considere a reposição.",
                    produtoCadastrado.getFuncionario().getEmpresa().getId()
            ));
        }
        else if (produtoCadastrado.getQuantidade() > produtoCadastrado.getQuantidadeMax()) {
            eventPublisher.publishEvent(new NotificacaoEstoqueEvent(
                    produtoCadastrado,
                    TipoEventoEstoque.ACIMA_MAXIMO,
                    "A quantidade de " + produtoCadastrado.getQuantidade() +  "do produto '" + produtoCadastrado.getNome() + "' excede o limite máximo permitido em estoque (" + produtoCadastrado.getQuantidadeMax() + ").",
                    produtoCadastrado.getFuncionario().getEmpresa().getId()
            ));
        }

        if (imagem != null && !imagem.isEmpty()) {
            try {
                EventoProcessamentoImagemDto evento = new EventoProcessamentoImagemDto(
                        imagem.getBytes(),
                        "PRODUTO",
                        imagem.getOriginalFilename(),
                        imagem.getContentType(),
                        String.valueOf(produtoCadastrado.getId())
                );
                rabbitMQProducerService.enviarEventoProcessamentoImagem(evento);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao ler os bytes da imagem.", e);
            }
        }


        return ProdutoMapper.toDto(produtoCadastrado);
    }

    @Transactional
    @CacheEvict(value = "produtoPorId", key = "#idProduto")
    public void atualizarUrlImagem(Integer idProduto, String urlImagem) {
        Produto produto = repository.findById(idProduto)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado para atualização da imagem"));

        produto.setImagem(urlImagem);
        repository.save(produto);
        System.out.println("URL da imagem do produto " + idProduto + " atualizada para: " + urlImagem);
    }

    @Cacheable(value = "produtoPorId", key = "#id")
    public ProdutoListagemDto buscarProdutoPorId(Integer id, Integer idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        Optional<Produto> produtoPorEmpresaFuncionario = repository.buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(id, idFuncionario);

        if (produtoPorEmpresaFuncionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado ou não pertence à empresa do funcionário informado.");
        }

        return ProdutoMapper.toDto(produtoPorEmpresaFuncionario.get());
    }

    @Cacheable(value = "listaProdutos", key = "#idFuncionario")
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

    @Caching(evict = {
            @CacheEvict(value = "produtoPorId", key = "#id"),
            @CacheEvict(value = "listaProdutos", key = "#idFuncionario")
    })
    public ProdutoListagemDto editarProduto(Integer id, Integer idFuncionario, @Valid ProdutoEdicaoDto produtoParaEditar, MultipartFile imagem) {
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

        if (imagem != null && !imagem.isEmpty()) {
            try {
                EventoProcessamentoImagemDto evento = new EventoProcessamentoImagemDto(
                        imagem.getBytes(),
                        "PRODUTO",
                        imagem.getOriginalFilename(),
                        imagem.getContentType(),
                        String.valueOf(produtoExiste.getId())
                );
                rabbitMQProducerService.enviarEventoProcessamentoImagem(evento);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao ler os bytes da imagem.", e);
            }
        }

        ProdutoMapper.atualizarProdutoComDto(produtoExiste, produtoParaEditar);

        Produto produtoSalvo = repository.save(produtoExiste);

       if (produtoSalvo.getQuantidade() == 0) {
            eventPublisher.publishEvent(new NotificacaoEstoqueEvent(
                    produtoSalvo,
                    TipoEventoEstoque.ZERADO,
                    "O produto " + produtoSalvo.getNome() + " está zerado no estoque. Reposição necessária.",
                    produtoSalvo.getFuncionario().getEmpresa().getId()
            ));
        }
        else if (produtoSalvo.getQuantidade() <= produtoSalvo.getQuantidadeMin()) {
            eventPublisher.publishEvent(new NotificacaoEstoqueEvent(
                    produtoSalvo,
                    TipoEventoEstoque.ABAIXO_MINIMO,
                    "O produto \"" + produtoSalvo.getNome() + " possui apenas " + produtoSalvo.getQuantidade() + " unidades disponíveis. Considere a reposição.",
                    produtoSalvo.getFuncionario().getEmpresa().getId()
            ));
        }
        else if (produtoSalvo.getQuantidade() > produtoSalvo.getQuantidadeMax()) {
            eventPublisher.publishEvent(new NotificacaoEstoqueEvent(
                    produtoSalvo,
                    TipoEventoEstoque.ACIMA_MAXIMO,
                    "A quantidade de " + produtoSalvo.getQuantidade() +  " do produto '" + produtoSalvo.getNome() + "' excede o limite máximo permitido em estoque (" + produtoSalvo.getQuantidadeMax() + ").",
                    produtoSalvo.getFuncionario().getEmpresa().getId()
            ));
        }


        return ProdutoMapper.toDto(produtoSalvo);
    }


    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "produtoPorId", key = "#id"),
            @CacheEvict(value = "listaProdutos", key = "#idFuncionario")
    })
    public void removerPorId(Integer id, Integer idFuncionario) {
        Optional<Produto> produto = repository.buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(id, idFuncionario);

        if (produto.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado ou não pertence à empresa do funcionário informado.");
        }

        repository.desvincularProdutoDosItens(id);
        repository.delete(produto.get());

        Produto produtoExtraido = produto.get();

        // Gatilho: Produto removido
        eventPublisher.publishEvent(new NotificacaoEstoqueEvent(
                produtoExtraido,
                TipoEventoEstoque.REMOVIDO,
                produtoExtraido.getNome() + " foi removido do estoque.",
                produtoExtraido.getFuncionario().getEmpresa().getId()
        ));
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

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "produtoPorId", key = "#idProduto"),
            @CacheEvict(value = "listaProdutos", key = "#idFuncionario")
    })
    public ProdutoListagemDto atualizarQuantidade(Integer idProduto, Integer idFuncionario, Integer quantidadeAdicional) {
        Optional<Produto> produtoPorEmpresaFuncionario = repository
                .buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(idProduto, idFuncionario);

        if (produtoPorEmpresaFuncionario.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    "Produto não encontrado ou não pertence à empresa do funcionário informado.");
        }

        Produto produto = produtoPorEmpresaFuncionario.get();
        produto.setQuantidade(produto.getQuantidade() + quantidadeAdicional);
        return ProdutoMapper.toDto(repository.save(produto));
    }

    public List<ProdutoListagemDto> buscarProdutoPorNomeExatoEmpresa(String nome, Integer idFuncionario) {
        List<Produto> produtos = repository.listarProdutoPorNomeExatoEmpresa(nome, idFuncionario);
        if (produtos.isEmpty()) return Collections.emptyList();
        return produtos.stream().map(ProdutoMapper::toDto).collect(Collectors.toList());
    }

    public List<ProdutoListagemDto> buscarProdutoPorCodigoEmpresa(String codigo, Integer idFuncionario) {
        List<Produto> produtos = repository.listarProdutoPorCodigoExatoEmpresa(codigo, idFuncionario);
        if (produtos.isEmpty()) return Collections.emptyList();
        return produtos.stream().map(ProdutoMapper::toDto).collect(Collectors.toList());
    }
}

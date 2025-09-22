package school.sptech.service.venda;

import org.springframework.stereotype.Service;
import school.sptech.controller.venda.dto.VendaMapper;
import school.sptech.controller.venda.dto.VendaRequestDto;
import school.sptech.controller.venda.dto.VendaResponseDto;
import school.sptech.entity.categoria.Categoria;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.itemCarrinho.ItemCarrinho;
import school.sptech.entity.prato.Prato;
import school.sptech.entity.produto.Produto;
import school.sptech.entity.venda.Venda;
import school.sptech.repository.venda.VendaRepository;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.itemCarrinho.ItemCarrinhoRepository;
import school.sptech.repository.produto.ProdutoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDate;
import java.time.ZoneId;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class VendaService {
    private final FuncionarioRepository funcionarioRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    public VendaService(FuncionarioRepository funcionarioRepository, ItemCarrinhoRepository itemCarrinhoRepository, VendaRepository vendaRepository, ProdutoRepository produtoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
    }

    public Venda criarVenda(Venda venda) {

        Funcionario funcionario = funcionarioRepository.findById(venda.getFuncionario().getId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));

        if (venda.getItensCarrinho() == null || venda.getItensCarrinho().isEmpty()) {
            throw new RuntimeException("Itens da venda não encontrados.");
        }

        List<Integer> itemIds = venda.getItensCarrinho().stream()
                .map(ItemCarrinho::getId)
                .toList();

        List<ItemCarrinho> itensCarrinho = itemCarrinhoRepository.findAllById(itemIds);

        for (ItemCarrinho item : itensCarrinho) {
            Produto produto = item.getProduto();
            if (produto != null) {
                int novaQuantidade = produto.getQuantidade() - 1;

                if (novaQuantidade < 0) {
                    throw new RuntimeException("Produto " + produto.getNome() + " sem estoque suficiente.");
                }

                produto.setQuantidade(novaQuantidade);
                produtoRepository.save(produto);
            }
        }

        if (itensCarrinho.isEmpty()) {
            throw new RuntimeException("Nenhum item válido encontrado para os IDs informados.");
        }

        venda.setFuncionario(funcionario);
        venda.setItensCarrinho(itensCarrinho);

        Double valorTotal = calcularValorTotal(itensCarrinho);
        venda.setValorTotal(valorTotal);

        return vendaRepository.save(venda);
    }

    public Double calcularValorTotal(List<ItemCarrinho> itens) {
        return itens.stream()
                .collect(Collectors.groupingBy(item -> item))
                .entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getValorUnitario() * entry.getValue().size())
                .sum();
    }

    public Venda buscarVendaPorId(Integer id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
    }


    public Boolean finalizarVendaPrato(Integer idVenda) {
        Venda venda = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
        venda.setVendaConcluida(true);
        vendaRepository.save(venda);

        return venda.getVendaConcluida();
    }

    public void excluirVenda(Integer id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        vendaRepository.delete(venda);
    }

    public Double calcularLucroTotal(Integer idFuncionario, LocalDate dataVenda) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario).orElseThrow();
        Integer idEmpresa = funcionario.getEmpresa().getId();

        List<Venda> vendas = vendaRepository.findAllByDataVenda(dataVenda);
        return vendas.stream()
                .filter(venda -> venda.getFuncionario().getEmpresa().getId().equals(idEmpresa))
                .mapToDouble(Venda::getValorTotal)
                .sum();
    }

    public Double valorTotalPorEmpresaNoPeriodo(Integer empresaId, LocalDate dataInicio, LocalDate dataFim) {
        LocalDate hojeNoBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        LocalDate inicio = dataInicio != null ? dataInicio : hojeNoBrasil;
        LocalDate fim = dataFim != null ? dataFim : inicio;

        if (dataInicio != null) {
            inicio = dataInicio.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }
        if (dataFim != null) {
            fim = dataFim.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }

        Double valorTotal = vendaRepository.valorTotalPorEmpresaNoPeriodo(empresaId, inicio, fim);
        return valorTotal != null ? valorTotal : 0.0;
    }

    public Double lucroLiquidoPorEmpresaNoPeriodo(Integer empresaId, LocalDate dataInicio, LocalDate dataFim) {
        LocalDate hojeNoBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        LocalDate inicio = dataInicio != null ? dataInicio : hojeNoBrasil;
        LocalDate fim = dataFim != null ? dataFim : inicio;

        if (dataInicio != null) {
            inicio = dataInicio.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }
        if (dataFim != null) {
            fim = dataFim.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }

        Double lucro = vendaRepository.calcularLucroLiquidoPorEmpresaNoPeriodo(empresaId, inicio, fim);
        return lucro;
    }

    public Map<String, Double> valorTotalPorSetorHoje(Integer empresaId) {
        LocalDate hojeNoBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        List<Object[]> resultados = vendaRepository.valorTotalDiarioPorSetorEmpresa(empresaId, hojeNoBrasil);

        Map<String, Double> totalPorSetor = new HashMap<>();
        for (Object[] linha : resultados) {
            String setor = (String) linha[0];
            Double valor = ((Number) linha[1]).doubleValue();
            totalPorSetor.put(setor, valor);
        }

        return totalPorSetor;
    }

    public Map<String, Double> valorTotalPorCategoriaHoje(Integer empresaId) {
        LocalDate hojeNoBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        List<Object[]> resultados = vendaRepository.valorTotalDiarioPorCategoriaEmpresa(empresaId, hojeNoBrasil);

        Map<String, Double> totalPorCategoria = new HashMap<>();
        for (Object[] linha : resultados) {
            String categoria = (String) linha[0];
            Double valor = ((Number) linha[1]).doubleValue();
            totalPorCategoria.put(categoria, valor);
        }

        return totalPorCategoria;
    }

    public Integer quantidadeVendasPorEmpresaNoPeriodo(Integer empresaId, LocalDate dataInicio, LocalDate dataFim) {
        LocalDate hojeNoBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        LocalDate inicio = dataInicio != null ? dataInicio : hojeNoBrasil;
        LocalDate fim = dataFim != null ? dataFim : inicio;

        if (dataInicio != null) {
            inicio = dataInicio.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }
        if (dataFim != null) {
            fim = dataFim.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }

        Integer quantidade = vendaRepository.contarVendasConcluidasPorEmpresaEPeriodo(empresaId, inicio, fim);
        return quantidade;
    }

    public List<Produto> listarProdutosAbaixoDaQuantidadeMinima(Integer empresaId) {
        return vendaRepository.buscaProdutosAbaixoDeQuantidadeMinima(empresaId);
    }

    public List<Venda> listarPratosVendidosPorEmpresaEData(Integer empresaId) {
        LocalDate hojeNoBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        return vendaRepository.findVendasDePratosComItensPorEmpresaEData(empresaId, hojeNoBrasil);
    }

    public List<String> listarResumoItensVendidosPorEmpresaEData(Integer empresaId) {
        LocalDate hojeNoBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        List<Venda> vendas = vendaRepository.findVendasComItensPorEmpresaEData(empresaId, hojeNoBrasil);

        List<ItemCarrinho> itensVendidos = new ArrayList<>();
        for (Venda venda : vendas) {
            itensVendidos.addAll(venda.getItensCarrinho());
        }

        Map<String, Integer> resumo = new HashMap<>();

        for (ItemCarrinho item : itensVendidos) {
            String nomeItem = null;

            if (item.getProduto() != null) {
                nomeItem = item.getProduto().getNome();
            } else if (item.getPrato() != null) {
                nomeItem = item.getPrato().getNome();
            }

            if (nomeItem != null) {
                resumo.put(nomeItem, resumo.getOrDefault(nomeItem, 0) + 1);
            }
        }

        return resumo.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .map(entry -> entry.getKey() + " - " + entry.getValue() + " unidade(s)")
                .collect(Collectors.toList());
    }


    public List<Object[]> top7Pratos(Integer empresaId, LocalDate dataInicio, LocalDate dataFim) {
        LocalDate hojeNoBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        LocalDate inicio = dataInicio != null ? dataInicio : hojeNoBrasil;
        LocalDate fim = dataFim != null ? dataFim : inicio;

        if (dataInicio != null) {
            inicio = dataInicio.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }
        if (dataFim != null) {
            fim = dataFim.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }

        Pageable limite = PageRequest.of(0, 7);
        return vendaRepository.top7PratosMaisVendidos(empresaId, inicio, fim, limite);
    }

    public List<Object[]> top7Produtos(Integer empresaId, LocalDate dataInicio, LocalDate dataFim) {
        LocalDate hojeNoBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        LocalDate inicio = dataInicio != null ? dataInicio : hojeNoBrasil;
        LocalDate fim = dataFim != null ? dataFim : inicio;

        if (dataInicio != null) {
            inicio = dataInicio.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }
        if (dataFim != null) {
            fim = dataFim.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }

        Pageable limite = PageRequest.of(0, 7);
        return vendaRepository.top7ProdutosMaisVendidos(empresaId, inicio, fim, limite);
    }

    public List<Object[]> top5Categorias(Integer empresaId, LocalDate dataInicio, LocalDate dataFim) {
        LocalDate hojeNoBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        LocalDate inicio = dataInicio != null ? dataInicio : hojeNoBrasil;
        LocalDate fim = dataFim != null ? dataFim : inicio;

        if (dataInicio != null) {
            inicio = dataInicio.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }
        if (dataFim != null) {
            fim = dataFim.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }

        Pageable limite = PageRequest.of(0, 5);
        return vendaRepository.top5CategoriasMaisVendidas(empresaId, inicio, fim, limite);
    }


    public List<Object[]> obterRankingSetoresMaisVendidos(Integer empresaId, LocalDate dataInicio, LocalDate dataFim) {
        LocalDate hojeNoBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        LocalDate inicio = dataInicio != null ? dataInicio : hojeNoBrasil;
        LocalDate fim = dataFim != null ? dataFim : inicio;

        if (dataInicio != null) {
            inicio = dataInicio.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }
        if (dataFim != null) {
            fim = dataFim.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }

        return vendaRepository.rankingSetoresMaisVendidos(empresaId, inicio, fim);

    }

    public List<Object[]> calculosKpi(Integer empresaId, LocalDate dataInicio, LocalDate dataFim) {
        LocalDate hojeNoBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        List<Object[]> resultados = new ArrayList<>();

        LocalDate inicio = dataInicio != null ? dataInicio : hojeNoBrasil;
        LocalDate fim = dataFim != null ? dataFim : inicio;

        if (dataInicio != null) {
            inicio = dataInicio.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }
        if (dataFim != null) {
            fim = dataFim.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toLocalDate();
        }

        long diasDoPeriodo = ChronoUnit.DAYS.between(inicio, fim) + 1;
        LocalDate fimAnterior = inicio.minusDays(1);
        LocalDate inicioAnterior = fimAnterior.minusDays(diasDoPeriodo - 1);


        Double brutoHoje = vendaRepository.valorTotalPorEmpresaNoPeriodo(empresaId, inicio, fim);
        brutoHoje = brutoHoje != null ? brutoHoje.doubleValue() : 0.0;

        Double brutoOntem = vendaRepository.valorTotalPorEmpresaNoPeriodo(empresaId, inicioAnterior, fimAnterior);
        brutoOntem = brutoOntem != null ? brutoOntem.doubleValue() : 0.0;

        Double liquidoHoje = vendaRepository.calcularLucroLiquidoPorEmpresaNoPeriodo(empresaId, inicio, fim);
        liquidoHoje = liquidoHoje != null ? liquidoHoje.doubleValue() : 0.0;

        Double liquidoMercadoria = brutoHoje - liquidoHoje;
        liquidoMercadoria = liquidoMercadoria != null ? liquidoMercadoria.doubleValue() : 0.0;

        Integer vendasHoje = vendaRepository.contarVendasConcluidasPorEmpresaEPeriodo(empresaId, inicio, fim);
        vendasHoje = vendasHoje != null ? vendasHoje.intValue() : 0;

        Integer vendasOntem = vendaRepository.contarVendasConcluidasPorEmpresaEPeriodo(empresaId, inicioAnterior, fimAnterior);
        vendasOntem = vendasOntem != null ? vendasOntem.intValue() : 0;

        resultados.add(new Object[]{
                brutoHoje,
                brutoOntem,
                vendasHoje,
                vendasOntem,
                liquidoHoje,
                liquidoMercadoria
        });
        return resultados;
    }
}
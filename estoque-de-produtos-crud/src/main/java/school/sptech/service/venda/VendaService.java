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

    public VendaService(FuncionarioRepository funcionarioRepository, ItemCarrinhoRepository itemCarrinhoRepository, VendaRepository vendaRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.vendaRepository = vendaRepository;
    }

    public Venda criarVenda(VendaRequestDto vendaRequest) {
        Funcionario funcionario = funcionarioRepository.findById(vendaRequest.getFuncionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        List<ItemCarrinho> itens = itemCarrinhoRepository.findAllById(vendaRequest.getItens());
        if (itens.isEmpty()) {
            throw new RuntimeException("Itens não encontrados");
        }

        Venda venda = VendaMapper.toEntity(vendaRequest, funcionario, itens);
        venda.setValorTotal(calcularValorTotal(itens));

        return vendaRepository.save(venda);
    }

    public Double calcularValorTotal(List<ItemCarrinho> itens) {
        return itens.stream()
                .collect(Collectors.groupingBy(item -> item))  // Agrupar os itens pelo próprio objeto
                .entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getValorUnitario() * entry.getValue().size())  // Multiplicando valorUnitario pela quantidade (tamanho do grupo)
                .sum();  // Somando o valor total
    }

    public VendaResponseDto buscarVendaPorId(Integer id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
        return VendaMapper.toDto(venda);
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

    public Double valorTotalPorEmpresaHoje(Integer empresaId) {
        LocalDate hoje = LocalDate.now();
        Double valorTotal = vendaRepository.valorTotalVendasPorEmpresaEData(empresaId, hoje);
        return valorTotal;
    }

    public Double lucroLiquidoPorEmpresaHoje(Integer empresaId) {
        LocalDate hoje = LocalDate.now();
        Double lucro = vendaRepository.calcularLucroLiquidoPorEmpresaEData(empresaId, hoje);
        return lucro;
    }



    public Map<String, Double> valorTotalPorSetorHoje(Integer empresaId) {
        LocalDate hoje = LocalDate.now();
        List<Object[]> resultados = vendaRepository.valorTotalDiarioPorSetorEmpresa(empresaId, hoje);

        Map<String, Double> totalPorSetor = new HashMap<>();
        for (Object[] linha : resultados) {
            String setor = (String) linha[0];
            Double valor = ((Number) linha[1]).doubleValue();
            totalPorSetor.put(setor, valor);
        }

        return totalPorSetor;
    }

    public Map<String, Double> valorTotalPorCategoriaHoje(Integer empresaId) {
        LocalDate hoje = LocalDate.now();
        List<Object[]> resultados = vendaRepository.valorTotalDiarioPorCategoriaEmpresa(empresaId, hoje);

        Map<String, Double> totalPorCategoria = new HashMap<>();
        for (Object[] linha : resultados) {
            String categoria = (String) linha[0];
            Double valor = ((Number) linha[1]).doubleValue();
            totalPorCategoria.put(categoria, valor);
        }

        return totalPorCategoria;
    }


    public Integer quantidadeVendasPorEmpresaHoje(Integer empresaId) {
        LocalDate hoje = LocalDate.now();
        Integer quantidade = vendaRepository.contarVendasConcluidasPorEmpresaEData(empresaId, hoje);
        return quantidade;
    }

    public List<Produto> listarProdutosAbaixoDaQuantidadeMinima(Integer empresaId) {
        return vendaRepository.buscaProdutosAbaixoDeQuantidadeMinima(empresaId);
    }

    public List<String> listarResumoItensVendidosPorEmpresaEData(Integer empresaId) {
        LocalDate hoje = LocalDate.now();
        List<Venda> vendas = vendaRepository.findVendasComItensPorEmpresaEData(empresaId, hoje);

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

        List<String> resultado = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : resumo.entrySet()) {
            resultado.add(entry.getKey() + " - " + entry.getValue() + " unidade(s)");
        }

        return resultado;
    }

    public List<Object[]> top7Pratos(Integer empresaId) {
        Pageable limite = PageRequest.of(0, 7);
        LocalDate hoje = LocalDate.now();
        return vendaRepository.top7PratosMaisVendidos(empresaId, hoje, limite);
    }

    public List<Object[]> top7Produtos(Integer empresaId) {
        Pageable limite = PageRequest.of(0, 7);
        LocalDate hoje = LocalDate.now();
        return vendaRepository.top7ProdutosMaisVendidos(empresaId, hoje, limite);
    }

    public List<Object[]> top5Categorias(Integer empresaId) {
        Pageable limite = PageRequest.of(0, 7);
        LocalDate hoje = LocalDate.now();
        return vendaRepository.top5CategoriasMaisVendidas(empresaId, hoje, limite);
    }
  
  
    public List<Object[]> obterRankingSetoresMaisVendidos(Integer empresaId) {
        LocalDate hoje = LocalDate.now();
        return vendaRepository.rankingSetoresMaisVendidos(empresaId, hoje);

    }
  
    public List<Object[]> calculosKpi(Integer empresaId) {
        LocalDate hoje = LocalDate.now();
        LocalDate ontem = hoje.minusDays(1);
        List<Object[]> resultados = new ArrayList<>();

        Double brutoHoje = vendaRepository.valorTotalVendasPorEmpresaEData(empresaId, hoje);
        Double brutoOntem = vendaRepository.valorTotalVendasPorEmpresaEData(empresaId, ontem);
        Double liquidoHoje = vendaRepository.calcularLucroLiquidoPorEmpresaEData(empresaId, hoje);
        Double liquidoMercadoria = brutoHoje - liquidoHoje;
        Integer vendasHoje = vendaRepository.contarVendasConcluidasPorEmpresaEData(empresaId, hoje);
        Integer vendasOntem = vendaRepository.contarVendasConcluidasPorEmpresaEData(empresaId, ontem);

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

    /**
     * Gera um número especificado de vendas, com cada venda contendo um número aleatório
     * de itens do carrinho, garantindo que os IDs dos itens existam no banco de dados.
     *
     * @param quantidadeVendas O número total de vendas a serem geradas.
     * @param maxItensPorVenda O número máximo de itens do carrinho que uma única venda pode ter.
     * @return Uma lista de DTOs de resposta das vendas geradas.
     */
    public List<VendaResponseDto> gerarVendasComItens(int quantidadeVendas, int maxItensPorVenda) {
        List<VendaResponseDto> vendasGeradas = new ArrayList<>();
        LocalDate dataAtual = LocalDate.now();

        // 1. Obter TODOS os IDs de ItemCarrinho válidos no banco de dados
        // Isso evita gerar IDs aleatórios que não existem.
        List<Integer> allExistingItemIds = itemCarrinhoRepository.findAll()
                .stream()
                .map(ItemCarrinho::getId)
                .collect(Collectors.toList());

        if (allExistingItemIds.isEmpty()) {
            throw new RuntimeException("Nenhum ItemCarrinho encontrado no banco de dados. Crie itens primeiro.");
        }

        // Para evitar NPE caso não haja funcionários
        Funcionario funcionarioPadrao = funcionarioRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Funcionário padrão (ID 1) não encontrado para geração de vendas."));

        for (int i = 0; i < quantidadeVendas; i++) {
            List<Integer> itemIdsParaEstaVenda = new ArrayList<>();
            int numItensNaVenda = ThreadLocalRandom.current().nextInt(1, maxItensPorVenda + 1); // Gera entre 1 e maxItensPorVenda itens

            for (int j = 0; j < numItensNaVenda; j++) {
                // 2. Selecionar um ID de ItemCarrinho aleatório da lista de IDs existentes
                int randomIndex = ThreadLocalRandom.current().nextInt(allExistingItemIds.size());
                itemIdsParaEstaVenda.add(allExistingItemIds.get(randomIndex));
            }

            // Cria um DTO de requisição como se estivesse vindo do Insomnia
            VendaRequestDto requestDto = new VendaRequestDto();
            requestDto.setMesa(String.valueOf(ThreadLocalRandom.current().nextInt(1, 21))); // Mesa aleatória de 1 a 20
            requestDto.setDataVenda(dataAtual); // Sempre a data atual
            requestDto.setItens(itemIdsParaEstaVenda); // Usamos a lista de IDs de itens válidos
            requestDto.setFuncionarioId(funcionarioPadrao.getId());
            requestDto.setVendaConcluida(true); // Sempre concluída para este exemplo

            try {
                Venda novaVenda = criarVenda(requestDto); // Reutiliza seu método existente de criação de venda
                vendasGeradas.add(VendaMapper.toDto(novaVenda));
            } catch (RuntimeException e) {
                // Imprimir o erro, mas continuar se for um erro de item não encontrado que por algum motivo escapou
                System.err.println("Erro ao criar venda: " + e.getMessage() + " para itens: " + itemIdsParaEstaVenda);
            }
        }
        return vendasGeradas;

}

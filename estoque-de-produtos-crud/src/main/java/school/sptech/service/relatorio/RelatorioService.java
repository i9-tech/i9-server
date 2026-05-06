package school.sptech.service.relatorio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.produto.Produto;
import school.sptech.repository.empresa.EmpresaRepository;
import school.sptech.repository.notificacao.NotificacaoRepository;
import school.sptech.service.venda.VendaService;

import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {

    private static final Logger logger = LoggerFactory.getLogger(RelatorioService.class);

    private final VendaService vendaService;
    private final EmpresaRepository empresaRepository;
    private final NotificacaoRepository notificacaoRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public RelatorioService(VendaService vendaService,
                            EmpresaRepository empresaRepository, NotificacaoRepository notificacaoRepository,
                            SimpMessagingTemplate messagingTemplate) {
        this.vendaService = vendaService;
        this.empresaRepository = empresaRepository;
        this.notificacaoRepository = notificacaoRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void enviarRelatorioNotificacao(Integer empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        Double valorTotal = vendaService.valorTotalPorEmpresaNoPeriodo(empresaId, null, null);
        Double valorLiquido = vendaService.lucroLiquidoPorEmpresaNoPeriodo(empresaId, null, null);
        Integer quantidadeVendas = vendaService.quantidadeVendasPorEmpresaNoPeriodo(empresaId, null, null);
        Map<String, Double> totalPorSetor = vendaService.valorTotalPorSetorHoje(empresaId);
        Map<String, Double> totalPorCategoria = vendaService.valorTotalPorCategoriaHoje(empresaId);
        List<Produto> produtosBaixoEstoque = vendaService.listarProdutosAbaixoDaQuantidadeMinima(empresaId);
        List<String> resumoItens = vendaService.listarResumoItensVendidosPorEmpresaEData(empresaId);

        StringBuilder mensagem = new StringBuilder();


        mensagem.append("📌 Resumo\n");

        mensagem.append(String.format("Vendas: %s\n",
                quantidadeVendas != null ? quantidadeVendas : "-"));

        mensagem.append(String.format("Faturamento: %s\n",
                valorTotal != null ? String.format("R$ %.2f", valorTotal) : "-"));

        mensagem.append(String.format("Lucro: %s\n\n",
                valorLiquido != null ? String.format("R$ %.2f", valorLiquido) : "-"));

        mensagem.append("🏪 Setores\n");
        if (totalPorSetor != null && !totalPorSetor.isEmpty()) {
            totalPorSetor.forEach((s, v) ->
                    mensagem.append(String.format("▸ %s: R$ %.2f\n", s, v))
            );
        } else {
            mensagem.append("▸ℹ️ Sem dados\n");
        }

        mensagem.append("\n📦 Categorias\n");
        if (totalPorCategoria != null && !totalPorCategoria.isEmpty()) {
            totalPorCategoria.forEach((c, v) ->
                    mensagem.append(String.format("▸ %s: R$ %.2f\n", c, v))
            );
        } else {
            mensagem.append("▸ℹ️ Sem dados\n");
        }

        mensagem.append("\n📋 Mais vendidos\n");
        if (!resumoItens.isEmpty()) {
            resumoItens.forEach(item ->
                    mensagem.append("▸ ").append(item).append("\n")
            );
        } else {
            mensagem.append("▸ℹ️ Nenhum item\n");
        }

        if (!produtosBaixoEstoque.isEmpty()) {
            mensagem.append("\n🔔 Estoque baixo\n");
            produtosBaixoEstoque.forEach(p ->
                    mensagem.append(String.format("▸ %s (%d un.)\n",
                            p.getNome(), p.getQuantidade()))
            );
        }

        String mensagemFinal = mensagem.toString();

        school.sptech.entity.notificacao.Notificacao novaNotificacao = new school.sptech.entity.notificacao.Notificacao();
        novaNotificacao.setTitulo("📊 Relatório Diário de Vendas");
        novaNotificacao.setMensagem(mensagemFinal);

        notificacaoRepository.save(novaNotificacao);
        messagingTemplate.convertAndSend("/topic/notificacoes", mensagemFinal);

        logger.info("📨 Relatório para o aplicativo da empresa '{}' gerado e enviado via WebSocket.", empresa.getNome());
    }
}
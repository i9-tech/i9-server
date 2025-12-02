package school.sptech.config.twilio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.produto.Produto;
import school.sptech.repository.empresa.EmpresaRepository;
import school.sptech.service.venda.VendaService;

import java.util.List;
import java.util.Map;

@Service
public class TwilioService {

    private static final Logger logger = LoggerFactory.getLogger(TwilioService.class);

    private final VendaService vendaService;
    private final EmpresaRepository empresaRepository;
    private final TwilioMessageProducer twilioProducer;

    public TwilioService(VendaService vendaService,
                         EmpresaRepository empresaRepository,
                         TwilioMessageProducer twilioProducer) {
        this.vendaService = vendaService;
        this.empresaRepository = empresaRepository;
        this.twilioProducer = twilioProducer;
    }

    public void enviarMensagemRelatorioCompletoHoje(Integer empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        String whatsapp = empresa.getWhatsapp();
        if (whatsapp == null || whatsapp.isBlank()) {
            logger.warn("Empresa '{}' não possui número de WhatsApp cadastrado.", empresa.getNome());
            return;
        }

        Double valorTotal = vendaService.valorTotalPorEmpresaNoPeriodo(empresaId, null, null);
        Double valorLiquido = vendaService.lucroLiquidoPorEmpresaNoPeriodo(empresaId, null, null);
        Integer quantidadeVendas = vendaService.quantidadeVendasPorEmpresaNoPeriodo(empresaId, null, null);
        Map<String, Double> totalPorSetor = vendaService.valorTotalPorSetorHoje(empresaId);
        Map<String, Double> totalPorCategoria = vendaService.valorTotalPorCategoriaHoje(empresaId);
        List<Produto> produtosBaixoEstoque = vendaService.listarProdutosAbaixoDaQuantidadeMinima(empresaId);
        List<String> resumoItens = vendaService.listarResumoItensVendidosPorEmpresaEData(empresaId);

        StringBuilder mensagem = new StringBuilder();
        mensagem.append(String.format("📊 *Relatório de Vendas - %s*\n\n", empresa.getNome()));

        // Quantidade de vendas
        mensagem.append(String.format("🔢 *Quantidade de Vendas*: %d\n", quantidadeVendas != null ? quantidadeVendas : 0));

        // Lucro bruto
        mensagem.append(String.format("💰 *Lucro Bruto*: R$ %.2f\n", valorTotal != null ? valorTotal : 0.0));

        // Lucro líquido
        mensagem.append(String.format("📈 *Lucro Líquido*: R$ %.2f\n\n", valorLiquido != null ? valorLiquido : 0.0));

        // Por Setor
        mensagem.append("🏪 *_Lucro bruto por Setor:_*\n");
        if (totalPorSetor == null || totalPorSetor.isEmpty()) {
            mensagem.append("- Nenhum dado disponível\n");
        } else {
            totalPorSetor.forEach((setor, valor) ->
                    mensagem.append(String.format("- %s: R$ %.2f\n", setor, valor))
            );
        }

        // Por Categoria
        mensagem.append("\n📦 *_Lucro bruto por Categoria:_*\n");
        if (totalPorCategoria == null || totalPorCategoria.isEmpty()) {
            mensagem.append("- Nenhum dado disponível\n");
        } else {
            totalPorCategoria.forEach((categoria, valor) ->
                    mensagem.append(String.format("- %s: R$ %.2f\n", categoria, valor))
            );
        }

        // Top Itens
        mensagem.append("\n📋 *Top 5 itens mais vendidos hoje:*\n");
        if (resumoItens.isEmpty()) {
            mensagem.append("⚠️ Nenhum item vendido hoje.\n");
        } else {
            for (String linha : resumoItens) {
                mensagem.append("— ").append(linha).append("\n");
            }
        }

        // Estoque baixo
        if (!produtosBaixoEstoque.isEmpty()) {
            mensagem.append("\n⚠️ *Alerta de Estoque Baixo!*\n");
            for (Produto p : produtosBaixoEstoque) {
                mensagem.append(String.format("— %s (Estoque: %d | Mínimo: %d)\n",
                        p.getNome(), p.getQuantidade(), p.getQuantidadeMin()));
            }
        }

        mensagem.append("\n🫱🏻‍🫲🏻 A equipe *i9Tech* agradece pela confiança!\n");
        mensagem.append("🛎️ Responda *join slowly-cloud* para continuar recebendo os relatórios.");

        // 📤 Publica no RabbitMQ
        TwilioRequest request = new TwilioRequest(whatsapp, mensagem.toString());
        twilioProducer.sendToQueue(request);

        logger.info("📨 Relatório enviado para a fila Twilio da empresa '{}' - número: {}", empresa.getNome(), whatsapp);
    }
}
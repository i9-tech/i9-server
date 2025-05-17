package school.sptech.service.Twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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


    private final EmpresaRepository empresaRepository;
    private final VendaService vendaService;
    private final Logger logger = LoggerFactory.getLogger(TwilioService.class);

    public TwilioService(EmpresaRepository empresaRepository, VendaService vendaService) {
        this.empresaRepository = empresaRepository;
        this.vendaService = vendaService;
    }


    public void enviarMensagem(List<String> numeros, String mensagem) {
        for (String numero : numeros) {
            Message message = Message.creator(
                    new PhoneNumber("whatsapp:" + numero),
                    new PhoneNumber("whatsapp:+14155238886"),
                    mensagem
            ).create();
        }
    }

    public void enviarMensagemRelatorioCompletoHoje(Integer empresaId) {
        Double valorTotal = vendaService.valorTotalPorEmpresaHoje(empresaId);
        Double valorLiquido = vendaService.lucroLiquidoPorEmpresaHoje(empresaId);
        Integer quantidadeVendas = vendaService.quantidadeVendasPorEmpresaHoje(empresaId);
        Map<String, Double> totalPorSetor = vendaService.valorTotalPorSetorHoje(empresaId);
        Map<String, Double> totalPorCategoria = vendaService.valorTotalPorCategoriaHoje(empresaId);
        List<Produto> produtosBaixoEstoque = vendaService.listarProdutosAbaixoDaQuantidadeMinima(empresaId);
        List<String> resumoItens = vendaService.listarResumoItensVendidosPorEmpresaEData(empresaId);

        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        String whatsapp = empresa.getWhatsapp();
        if (whatsapp == null || whatsapp.isBlank()) {
            logger.warn("Empresa '{}' não possui número de WhatsApp cadastrado.", empresa.getNome());
            return;
        }

        StringBuilder mensagem = new StringBuilder();
        mensagem.append(String.format("Olá! Aqui está o relatório de vendas para a empresa %s hoje:\n\n", empresa.getNome()));


        if (quantidadeVendas <= 0) {
            mensagem.append("🔢 *Quantidade de Vendas*: 0 - Nenhuma venda foi realizada hoje ou o serviço está indisponível. Contate-nos.\n\n");
        } else {
            mensagem.append(String.format("🔢 *Quantidade de Vendas*: %d \n", quantidadeVendas));
        }

        if (valorTotal == null) {
            mensagem.append("💰 *Lucro Bruto*: R$ 00,00 - Nenhuma venda foi realizada hoje ou o serviço está indisponível. Contate-nos.\n\n");
        } else {
            mensagem.append(String.format("💰 *Lucro Bruto*: R$ %.2f\n", valorTotal));
        }

        if (valorLiquido == null) {
            mensagem.append("📈 *Lucro Líquido*: R$ 0,00 - Nenhuma venda foi realizada hoje ou o serviço está indisponível. Contate-nos.\n\n");
        } else {
            mensagem.append(String.format("*📈 Lucro Líquido*: R$ %.2f\n\n", valorLiquido));
        }

        mensagem.append("🏪 *_Lucro bruto por Setor:_*\n");
        if (totalPorSetor == null || totalPorSetor.isEmpty()) {
            mensagem.append("- Nenhum dado disponível\n");
        } else {
            totalPorSetor.forEach((setor, valor) ->
                    mensagem.append(String.format("- %s: R$ %.2f\n", setor, valor))
            );
        }
        mensagem.append("\n");

        mensagem.append("📦 *_Lucro bruto por Categoria:_*\n");
        if (totalPorCategoria == null || totalPorCategoria.isEmpty()) {
            mensagem.append("- Nenhum dado disponível\n\n");
        } else {
            totalPorCategoria.forEach((categoria, valor) ->
                    mensagem.append(String.format("- %s: R$ %.2f\n", categoria, valor))
            );
        }

        mensagem.append(String.format("\n📋 *Resumo dos itens vendidos:*\n"));

        if (resumoItens.isEmpty()) {
            mensagem.append("⚠️ Nenhum item vendido hoje.\n");
        } else {
            for (String linha : resumoItens) {
                mensagem.append("— ").append(linha).append("\n");
            }
        }


        if (!produtosBaixoEstoque.isEmpty()) {
            mensagem.append("\n\n⚠️ *Alerta de Estoque Baixo!*\n");
            mensagem.append("_Reponha o estoque o quanto antes._ \n");

            for (Produto p : produtosBaixoEstoque) {
                mensagem.append(String.format("— %s (Estoque: %d | Mínimo: %d)\n",
                        p.getNome(), p.getQuantidade(), p.getQuantidadeMin()));
            }
        }

        mensagem.append("\n🫱🏻‍🫲🏻 A equipe *i9Tech* agradece pela confiança e reafirma seu compromisso com a excelência em soluções para o seu negócio.");
        mensagem.append("\n\n🛎️ _Lembrete: para garantir o recebimento dos próximos relatórios, responda a esta mensagem com *join slowly-cloud* após a leitura._");
        enviarMensagem(List.of(whatsapp), mensagem.toString());
        logger.info("Mensagem completa enviada para a empresa '{}' no número {}", empresa.getNome(), whatsapp);
    }

}

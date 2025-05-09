package school.sptech;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.produto.Produto;
import school.sptech.repository.empresa.EmpresaRepository;
import school.sptech.repository.produto.ProdutoRepository;
import school.sptech.service.Twilio.TwilioService;

import java.util.ArrayList;
import java.util.List;

@Component
public class TwilioScheduler {

    private final TwilioService twilioService;
    private final EmpresaRepository empresaRepository;
    private final ProdutoRepository produtoRepository;

    public TwilioScheduler(TwilioService twilioService, EmpresaRepository empresaRepository, ProdutoRepository produtoRepository) {
        this.twilioService = twilioService;
        this.empresaRepository = empresaRepository;
        this.produtoRepository = produtoRepository;
    }

    @Scheduled(cron = "0 */1 * * * *") // a cada 1 minuto
    public void enviarMensagemAgendada() {
        List<Produto> produtos = produtoRepository.findAll();

        for (Produto produto : produtos) {
            Empresa empresa = produto.getFuncionario().getEmpresa();
            if (empresa.getWhatsapp() != null && !empresa.getWhatsapp().isBlank() && empresa.isAtivo()) {
                String mensagem = String.format(
                        "📦 Produto cadastrado:\n\n" +
                                "🏢 Empresa: %s\n" +
                                "📛 Nome: %s\n" +
                                "🧾 Código: %d\n" +
                                "📦 Quantidade: %d\n" +
                                "💰 Compra: R$ %.2f | Venda: R$ %.2f\n" +
                                "📂 Categoria: %s\n" +
                                "🏬 Setor: %s\n" +
                                "🗓️ Registrado em: %s",
                        empresa.getNome(),
                        produto.getNome(),
                        produto.getCodigo(),
                        produto.getQuantidade(),
                        produto.getValorCompra(),
                        produto.getValorUnitario(),
                        produto.getCategoria(),
                        produto.getSetor(),
                        produto.getDataRegistro()
                );

                twilioService.enviarMensagem(List.of(empresa.getWhatsapp()), mensagem);
            }
        }
    }
}
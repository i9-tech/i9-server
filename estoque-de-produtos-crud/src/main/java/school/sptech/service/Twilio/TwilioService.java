package school.sptech.service.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.produto.Produto;
import school.sptech.entity.venda.Venda;
import school.sptech.repository.empresa.EmpresaRepository;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.produto.ProdutoRepository;
import school.sptech.repository.venda.VendaRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TwilioService {

    private final EmpresaRepository empresaRepository;
    private final ProdutoRepository produtoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final VendaRepository vendaRepository;
    private final Logger logger = LoggerFactory.getLogger(TwilioService.class);

    public TwilioService(EmpresaRepository empresaRepository, ProdutoRepository produtoRepository, FuncionarioRepository funcionarioRepository, VendaRepository vendaRepository) {
        this.empresaRepository = empresaRepository;
        this.produtoRepository = produtoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.vendaRepository = vendaRepository;
    }

    public void enviarMensagemAutomatica() {
        List<Produto> produtos = produtoRepository.findAll();

        for (Produto produto : produtos) {
            Empresa empresa = produto.getFuncionario().getEmpresa();
            if (empresa.getWhatsapp() != null && !empresa.getWhatsapp().isBlank() && empresa.isAtivo()) {
                String mensagem = formatarMensagem(produto, empresa);
                enviarMensagem(List.of(empresa.getWhatsapp()), mensagem);

                logger.info("✅ Mensagem enviada para empresa '{}' (número: {})", empresa.getNome(), empresa.getWhatsapp());
            }
        }
    }

    private String formatarMensagem(Produto produto, Empresa empresa) {
        return String.format(
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
                produto.getCategoria().getNome(),
                produto.getSetor().getNome(),
                produto.getDataRegistro()
        );
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


    public void enviarMensagemLucroTotalPorFuncionario(Integer idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        var empresa = funcionario.getEmpresa();
        String whatsappEmpresa = empresa.getWhatsapp();

        if (whatsappEmpresa == null || whatsappEmpresa.isBlank()) {
            logger.warn("Empresa '{}' não possui número de WhatsApp cadastrado.", empresa.getNome());
            return;
        }

        Integer idEmpresa = empresa.getId();
        LocalDate hoje = LocalDate.now();

        List<Venda> vendas = vendaRepository.findByDataVendaAndEmpresaId(hoje, idEmpresa);

        double valorTotal = vendas.stream()
                .mapToDouble(Venda::getValorTotal)
                .sum();

        String mensagem = String.format(
                "Olá! Hoje a empresa %s teve um total de vendas de R$ %.2f.",
                empresa.getNome(),
                valorTotal
        );

        enviarMensagem(List.of(whatsappEmpresa), mensagem);

        logger.info("Mensagem de lucro total enviada para a empresa '{}' no WhatsApp {}", empresa.getNome(), whatsappEmpresa);
    }

}

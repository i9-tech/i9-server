package school.sptech.observer;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.service.emailService.EmailService;

import java.util.ArrayList;
import java.util.List;

@Component
public class FuncionarioEventListener {

    private final EmailService emailService;

    public FuncionarioEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleFuncionarioEvent(FuncionarioEvent event) {
        Funcionario funcionario = event.getFuncionario();

        String cargoGerado = determinarCargo(funcionario);
        String destinatario = funcionario.getEmpresa().getEmail();
        String assunto = "Novo Funcionário Cadastrado";
        String conteudoHtml = String.format(
                "<html><body>" +
                        "<h2>Um novo funcionário foi cadastrado:</h2>" +
                        "<ul>" +
                        "<li><strong>Nome:</strong> %s</li>" +
                        "<li><strong>CPF:</strong> %s</li>" +
                        "<li><strong>Cargo:</strong> %s</li>" +
                        "</ul>" +
                        "<p>Atenciosamente,<br/>Equipe i9 Tech</p>" +
                        "</body></html>",
                funcionario.getNome(), funcionario.getCpf(), cargoGerado
        );

        try {
            emailService.enviarEmailObserver(destinatario, assunto, conteudoHtml);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static String determinarCargo(Funcionario funcionario) {
        if (funcionario.isProprietario()) {
            return "Proprietário";
        }

        List<String> cargos = new ArrayList<>();

        if (funcionario.isAcessoSetorAtendimento()) {
            cargos.add("Atendente");
        }
        if (funcionario.isAcessoSetorEstoque()) {
            cargos.add("Estoquista");
        }
        if (funcionario.isAcessoSetorCozinha()) {
            cargos.add("Cozinheiro(a)");
        }

        return String.join(" | ", cargos);
    }

}

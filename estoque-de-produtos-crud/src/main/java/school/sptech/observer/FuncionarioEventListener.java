package school.sptech.observer;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.service.emailService.EmailService;

@Component
public class FuncionarioEventListener {

    @Autowired
    private EmailService emailService;

    @EventListener
    public void handleFuncionarioEvent(FuncionarioEvent event) {
        Funcionario funcionario = event.getFuncionario();

        String destinatario = funcionario.getEmpresa().getEmail(); // Certifique-se de que Empresa tem um e-mail válido
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
                funcionario.getNome(), funcionario.getCpf(), funcionario.getCargo()
        );

        try {
            emailService.enviarEmailObserver(destinatario, assunto, conteudoHtml);
        } catch (MessagingException e) {
            // Aqui você pode logar o erro ou tomar uma ação de fallback
            e.printStackTrace();
        }
    }
}

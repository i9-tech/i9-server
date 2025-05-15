package school.sptech.service.emailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(String destinatario) throws MessagingException {
        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helperMensagem = new MimeMessageHelper(mensagem, true);

        helperMensagem.setTo(destinatario);
        helperMensagem.setSubject("i9 Tech — Agradecemos pelo seu interesse em nosso sistema");

        String htmlContent = "<html><body>" +
                "<p><strong>Olá, tudo bem?</strong></p>" +
                "<p>A equipe da i9 agradece o seu contato!</p>" +
                "<p>Ficamos muito felizes com o seu interesse no nosso projeto, que tem como objetivo facilitar o dia a dia de estabelecimentos com um sistema completo, integrado e 100% online. Nossa solução reúne, em um único ambiente, funcionalidades como:</p>" +
                "<ul>" +
                "<li><b>PDV (Ponto de Venda)</b>: rápido, intuitivo e personalizável;</li>" +
                "<li><b>Controle de Estoque</b>: atualizado em tempo real com alertas inteligentes;</li>" +
                "<li><b>Gestão de Comandas</b>: ideal para setores como cozinha, bar ou atendimento.</li>" +
                "</ul>" +
                "<p>Tudo isso acessível de qualquer lugar com conexão à internet — sem necessidade de instalação ou infraestrutura complexa.</p>" +
                "<p><strong>Plano e Investimento Mensal</strong></p>" +
                "<ul>" +
                "<li>Acesso completo ao sistema;</li>" +
                "<li>Suporte técnico dedicado;</li>" +
                "<li>Atualizações e melhorias constantes.</li>" +
                "</ul>" +
                "<p>Se quiser, podemos agendar uma demonstração rápida para te mostrar o sistema funcionando na prática.</p>" +
                "<p>Ficamos à disposição para qualquer dúvida ou para dar o próximo passo!</p>" +
                "<p>Obrigado por se interessar pelo i9. Em breve entraremos em contato com você.</p>" +
                "<p><strong>Atenciosamente,</strong></p>" +
                "<b>i9 Tech</b>" +
                "</body></html>";


        helperMensagem.setText(htmlContent, true);
        mailSender.send(mensagem);
    }
}


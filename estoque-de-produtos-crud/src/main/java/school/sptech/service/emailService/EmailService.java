package school.sptech.service.emailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService{

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void enviarEmail(String destinatario) throws MessagingException {
        try {
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
        } catch (MailException e) {
            System.err.println("Erro ao enviar e-mail para " + destinatario + ": " + e.getMessage());
            e.printStackTrace();
        }

    }


    public void enviarEmailObserver(String destinatario, String assunto, String conteudoHtml) throws MessagingException {

        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helperMensagem = new MimeMessageHelper(mensagem, true, "UTF-8");

            helperMensagem.setTo(destinatario);
            helperMensagem.setSubject(assunto);
            helperMensagem.setText(conteudoHtml, true); // Usa o conteúdo passado no parâmetro

            mailSender.send(mensagem);

        } catch (MessagingException e) {
            e.printStackTrace(); // Você pode trocar isso por um logger
            throw e; // Repropaga a exceção para tratamento externo, se necessário
        }
    }

    public void enviarEmailRecuperacao(String destinatario, String nomeFuncionario, String cpfFuncionario, String linkRecuperacao) { // Removi 'throws MessagingException' temporariamente para a sugestão
        try {
            String assunto = "Recuperação de Senha para " + nomeFuncionario;

            String corpoEmail = "Prezado(a) responsável pela empresa " + nomeFuncionario + ",\n\n"
                    + "Foi solicitada uma recuperação de senha para o CPF " + cpfFuncionario + ".\n"
                    + "Para redefinir a senha, clique no link abaixo:\n"
                    + linkRecuperacao + "\n\n"
                    + "Este link expirará em 30 minutos. Se você não solicitou esta recuperação, por favor, ignore este e-mail.\n\n"
                    + "Atenciosamente,\nSua Aplicação";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(destinatario);
            message.setSubject(assunto);
            message.setText(corpoEmail);

            mailSender.send(message);

        } catch (MailException e) {
            System.err.println("Erro ao enviar e-mail de recuperação para " + destinatario + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}


package school.sptech.controller.javamail;

import school.sptech.service.emailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/envio-email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("interesse")
    public ResponseEntity<String> enviarEmail(@RequestBody String emailRecebido) {
        try {
            emailService.enviarEmail(emailRecebido);
            return ResponseEntity.ok("Email enviado com sucesso");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao enviar email: " + e.getMessage());
        }
    }
}

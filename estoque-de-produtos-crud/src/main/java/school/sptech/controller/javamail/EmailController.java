package school.sptech.controller.javamail;

import org.springframework.web.bind.annotation.*;
import school.sptech.service.emailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/envio-email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("interesse")
    public ResponseEntity<String> enviarEmail(@RequestBody String emailRecebido) throws MessagingException{
        try {
            emailService.enviarEmail(emailRecebido);
            return ResponseEntity.ok("Email enviado com sucesso");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao enviar email: " + e.getMessage());
        }
    }
}

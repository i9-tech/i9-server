package school.sptech.controller.javamail;

import org.springframework.web.bind.annotation.*;
import school.sptech.service.emailService.NotificacaoProducer;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/envio-email")
public class EmailController {

    private final NotificacaoProducer notificacaoProducer;

    public EmailController(NotificacaoProducer notificacaoProducer) {
        this.notificacaoProducer = notificacaoProducer;
    }

    @PostMapping("/interesse")
    public ResponseEntity<String> solicitarEnvioEmail(@RequestBody String email) {
        notificacaoProducer.publicarEventoChamadaAcao(email);
        return ResponseEntity.ok("Solicitação de envio de email recebida com sucesso.");
    }
}
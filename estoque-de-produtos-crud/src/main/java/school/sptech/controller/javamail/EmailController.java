package school.sptech.controller.javamail;

import org.springframework.web.bind.annotation.*;
import school.sptech.controller.javamail.dto.InteressadoDto;
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
    public ResponseEntity<String> solicitarEnvioEmail(@RequestBody InteressadoDto dto) {

        notificacaoProducer.publicarEventoChamadaAcao(dto.getEmail(), dto.getPlano(), dto.getPeriodo());

        return ResponseEntity.ok("Solicitação de envio de email recebida com sucesso.");
    }

}
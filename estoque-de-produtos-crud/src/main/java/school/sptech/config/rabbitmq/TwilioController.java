package school.sptech.config.rabbitmq;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/twilio")
public class TwilioController {

    private final TwilioPublisher twilioPublisher;

    public TwilioController(TwilioPublisher twilioPublisher) {
        this.twilioPublisher = twilioPublisher;
    }

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody TwilioRequest request) {
        twilioPublisher.sendToQueue(request);
        return ResponseEntity.ok("Mensagem enviada para a fila!");
    }
}
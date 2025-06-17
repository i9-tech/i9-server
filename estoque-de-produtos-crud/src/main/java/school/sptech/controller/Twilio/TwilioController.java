package school.sptech.controller.Twilio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import school.sptech.service.Twilio.TwilioService;

import java.util.List;

@Controller
public class TwilioController {

    private final TwilioService twilioService;

    public TwilioController(TwilioService twilioService) {
        this.twilioService = twilioService;
    }

    public void enviarMensagem(List<String> numeroTelefones, String mensagem) {
        twilioService.enviarMensagem(numeroTelefones, mensagem);
    }
}

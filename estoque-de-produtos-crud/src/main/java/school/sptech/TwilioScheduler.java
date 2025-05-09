package school.sptech;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sptech.service.Twilio.TwilioService;

@Component
public class TwilioScheduler {
    private final TwilioService twilioService;

    public TwilioScheduler(TwilioService twilioService) {
        this.twilioService = twilioService;
    }

    @Scheduled(cron = "0 */1 * * * *") // a cada 1 minuto
    public void enviarMensagemAgendada() {
        twilioService.enviarMensagemAutomatica();
    }
}
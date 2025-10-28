package school.sptech.config.twilio;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sptech.entity.empresa.Empresa;
import school.sptech.repository.empresa.EmpresaRepository;
import java.util.List;

@Component
public class TwilioScheduler {

    private final TwilioMessageProducer twilioProducer;
    private final EmpresaRepository empresaRepository;

    public TwilioScheduler(TwilioMessageProducer twilioProducer, EmpresaRepository empresaRepository) {
        this.twilioProducer = twilioProducer;
        this.empresaRepository = empresaRepository;
    }

    @Scheduled(cron = "0 0 23 * * *", zone = "America/Sao_Paulo")
    public void sendScheduledSms() {
        List<Empresa> empresas = empresaRepository.findAll();
        for (Empresa empresa : empresas){
            if (empresa.isAtivo()) {
                // Cria o DTO com o número/mensagem
                TwilioRequest request = new TwilioRequest("+5511942780654", "Mensagem agendada!");
                twilioProducer.sendToQueue(request);
            }
        }
        System.out.println("Agendamento de mensagens Twilio executado.");
    }
}
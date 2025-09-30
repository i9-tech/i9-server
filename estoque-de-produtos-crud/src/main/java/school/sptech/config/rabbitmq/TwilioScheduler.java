package school.sptech.config.rabbitmq;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sptech.entity.empresa.Empresa;
import school.sptech.repository.empresa.EmpresaRepository;

import java.util.List;

@Component
public class TwilioScheduler {

    private final TwilioPublisher twilioPublisher;
    private final EmpresaRepository empresaRepository;


    public TwilioScheduler(TwilioPublisher twilioPublisher, EmpresaRepository empresaRepository) {
        this.twilioPublisher = twilioPublisher;
        this.empresaRepository = empresaRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void sendScheduledSms() {
        List<Empresa> empresas = empresaRepository.findAll();
        for (Empresa empresa : empresas){
            if (empresa.isAtivo()) {
                TwilioRequest request = new TwilioRequest("+5511942780654", "Mensagem agendada!");
                twilioPublisher.sendToQueue(request);
            }
        }
    }
}


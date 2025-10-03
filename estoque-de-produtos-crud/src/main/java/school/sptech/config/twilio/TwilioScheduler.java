package school.sptech.config.twilio;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sptech.entity.empresa.Empresa;
import school.sptech.repository.empresa.EmpresaRepository;

import java.util.List;

@Component
public class TwilioScheduler {

    private final EmpresaRepository empresaRepository;
    private final TwilioService twilioService;

    public TwilioScheduler(EmpresaRepository empresaRepository, TwilioService twilioService) {
        this.empresaRepository = empresaRepository;
        this.twilioService = twilioService;
    }

    @Scheduled(cron = "0 0 23 * * *", zone = "America/Sao_Paulo")
    public void agendarEnvioMensagensRelatorio() {
        List<Empresa> empresas = empresaRepository.findAll();

        for (Empresa empresa : empresas) {
            if (empresa.isAtivo()) {
                twilioService.enviarMensagemRelatorioCompletoHoje(empresa.getId());
            }
        }

        System.out.println("✅ Agendamento de mensagens Twilio executado.");
    }
}

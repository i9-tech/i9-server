package school.sptech;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sptech.entity.empresa.Empresa;
import school.sptech.repository.empresa.EmpresaRepository;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.service.Twilio.TwilioService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TwilioScheduler {
    private final TwilioService twilioService;
    private final EmpresaRepository empresaRepository;


    public TwilioScheduler(TwilioService twilioService, EmpresaRepository empresaRepository) {
        this.twilioService = twilioService;
        this.empresaRepository = empresaRepository;
    }

    @Scheduled(cron = "0 0 23 * * *")
    public void enviarRelatorioDiarioParaTodasEmpresas() {
        List<Empresa> empresas = empresaRepository.findAll();
        for (Empresa empresa : empresas) {
            if (empresa.isAtivo()) {
                twilioService.enviarMensagemRelatorioCompletoHoje(empresa.getId());
            }
        }
    }
}
package school.sptech;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.service.Twilio.TwilioService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TwilioScheduler {
    private final TwilioService twilioService;
    private final FuncionarioRepository funcionarioRepository;



    public TwilioScheduler(TwilioService twilioService, FuncionarioRepository funcionarioRepository) {
        this.twilioService = twilioService;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Scheduled(cron = "0 */1 * * * *") // a cada 1 minuto
    public void enviarMensagemAgendada() {
        twilioService.enviarMensagemAutomatica();
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void enviarRelatorioDiarioParaTodosFuncionarios() {
        // Pega todos os funcionários ativos (ou filtre como quiser)
        List<Integer> idsFuncionarios = funcionarioRepository.findAll()
                .stream()
                .map(f -> f.getId())
                .collect(Collectors.toList());

        for (Integer idFuncionario : idsFuncionarios) {
            try {
                twilioService.enviarMensagemLucroTotalPorFuncionario(idFuncionario);
            } catch (Exception e) {
                // Loga o erro mas continua para os próximos
                System.err.println("Erro ao enviar mensagem para funcionário id " + idFuncionario + ": " + e.getMessage());
            }
        }
    }

}
package school.sptech.service.relatorio;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sptech.entity.empresa.Empresa;
import school.sptech.repository.empresa.EmpresaRepository;

import java.util.List;

@Component
public class RelatorioScheduler {

    private final EmpresaRepository empresaRepository;
    private final RelatorioService relatorioService;

    public RelatorioScheduler(EmpresaRepository empresaRepository, RelatorioService relatorioService) {
        this.empresaRepository = empresaRepository;
        this.relatorioService = relatorioService;
    }

     @Scheduled(cron = "0 0 23 * * *", zone = "America/Sao_Paulo")
    // @Scheduled(cron = "0 0/3 * * * *", zone = "America/Sao_Paulo")
    public void agendarEnvioRelatorioNotificacao() {
        List<Empresa> empresas = empresaRepository.findAll();

        for (Empresa empresa : empresas) {
            if (empresa.isAtivo()) {
                relatorioService.enviarRelatorioNotificacao(empresa.getId());
            }
        }

        System.out.println("✅ Notificação de relatório para app executado.");
    }
}
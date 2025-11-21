package school.sptech.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sptech.entity.plano.GerenciamentoPlano;
import school.sptech.repository.plano.GerenciamentoPlanoRepository;
import school.sptech.service.plano.GerenciamentoPlanoService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class PlanoScheduler {

    private final GerenciamentoPlanoRepository gerenciamentoPlanoRepository;
    private final GerenciamentoPlanoService gerenciamentoPlanoService;

    public PlanoScheduler(GerenciamentoPlanoRepository gerenciamentoPlanoRepository,
                          GerenciamentoPlanoService gerenciamentoPlanoService) {
        this.gerenciamentoPlanoRepository = gerenciamentoPlanoRepository;
        this.gerenciamentoPlanoService = gerenciamentoPlanoService;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "America/Sao_Paulo")
    public void verificarPlanos() {
        List<GerenciamentoPlano> planosAtivos = gerenciamentoPlanoRepository.findAll()
                .stream()
                .filter(GerenciamentoPlano::isAtivo)
                .toList();

        LocalDate hoje = LocalDate.now();

        for (GerenciamentoPlano plano : planosAtivos) {

            LocalDate dataInicio = plano.getDataInicio();
            LocalDate dataFim = plano.getDataFim();

            if (plano.isTesteGratis()) {
                LocalDate fimTeste = dataInicio.plusDays(plano.getDiasTeste());

                if (hoje.isBefore(fimTeste)) {
                    long diasRestantes = ChronoUnit.DAYS.between(hoje, fimTeste);
                    System.out.println("[INFO] Plano da empresa " + plano.getEmpresa().getId()
                            + " está no período de teste gratuito. Dias restantes: " + diasRestantes
                            + ". Data fim do plano: " + dataFim);
                    continue;
                }

                if (!hoje.isBefore(fimTeste)) {
                    gerenciamentoPlanoService.cancelarPlano(plano.getEmpresa().getId());
                    System.out.println("[CANCELAMENTO] Plano da empresa " + plano.getEmpresa().getId()
                            + " cancelado automaticamente. Motivo: período de teste gratuito finalizado."
                            + " Dias de teste: " + plano.getDiasTeste()
                            + ". Data fim do plano: " + dataFim
                            + ". Período: " + plano.getPeriodo()
                            + ". Valor cobrado: " + plano.getValorCobrado());
                    continue;
                }
            }

            // Plano expirado normalmente por periodo
            if (dataFim != null && hoje.isAfter(dataFim)) {
                gerenciamentoPlanoService.cancelarPlano(plano.getEmpresa().getId());
                System.out.println("[CANCELAMENTO] Plano da empresa " + plano.getEmpresa().getId()
                        + " cancelado automaticamente. Motivo: plano expirado."
                        + " Data fim do plano: " + dataFim
                        + ". Período: " + plano.getPeriodo()
                        + ". Valor cobrado: " + plano.getValorCobrado()
                        + ". Teste grátis: " + plano.isTesteGratis());
            }
        }
    }
}

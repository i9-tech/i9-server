package school.sptech.service.plano;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.controller.plano.dto.AlterarPlanoDto;
import school.sptech.controller.plano.dto.ContratarPlanoDto;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.plano.GerenciamentoPlano;
import school.sptech.entity.plano.Periodo;
import school.sptech.entity.plano.PlanoTemplate;
import school.sptech.repository.empresa.EmpresaRepository;
import school.sptech.repository.plano.GerenciamentoPlanoRepository;
import school.sptech.repository.plano.PlanoTemplateRepository;
import school.sptech.service.empresa.port.EmpresaPort;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class GerenciamentoPlanoService {

    private static final int DEFAULT_DIAS_TESTE_GRATIS = 7;

    private final PlanoTemplateRepository planoTemplateRepository;
    private final EmpresaRepository empresaRepository;
    private final GerenciamentoPlanoRepository gerenciamentoPlanoRepository;
    private final EmpresaPort empresaPort;

    public GerenciamentoPlanoService(EmpresaPort empresaPort,
                                     PlanoTemplateRepository planoTemplateRepository,
                                     EmpresaRepository empresaRepository,
                                     GerenciamentoPlanoRepository gerenciamentoPlanoRepository) {
        this.empresaPort = empresaPort;
        this.planoTemplateRepository = planoTemplateRepository;
        this.empresaRepository = empresaRepository;
        this.gerenciamentoPlanoRepository = gerenciamentoPlanoRepository;
    }

    @Transactional
    public GerenciamentoPlano contratarPlano(ContratarPlanoDto dto) {
        Integer empresaId = dto.getEmpresaId();
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada: " + empresaId));

        // desativa antigo se existir
        if (empresa.getGerenciamentoPlano() != null && empresa.getGerenciamentoPlano().isAtivo()) {
            GerenciamentoPlano antigo = empresa.getGerenciamentoPlano();
            antigo.setAtivo(false);
            antigo.setDataFim(LocalDate.now());
            gerenciamentoPlanoRepository.save(antigo);
        }

        GerenciamentoPlano novoPlano = new GerenciamentoPlano();
        Integer planoTemplateId = dto.getPlanoTemplateId();
        Periodo periodo = dto.getPeriodo();

        // buscar template por id (lançar erro se não achar)
        PlanoTemplate template = planoTemplateRepository.findById(planoTemplateId)
                .orElseThrow(() -> new IllegalArgumentException("PlanoTemplate não encontrado: " + planoTemplateId));

        novoPlano.setPlanoTemplate(template);
        novoPlano.setPeriodo(periodo);
        novoPlano.setDataAdesao(LocalDate.now());
        novoPlano.setTesteGratis(dto.isTesteGratis());
        novoPlano.setDiasTeste(dto.isTesteGratis() ? DEFAULT_DIAS_TESTE_GRATIS : 0);

        LocalDate dataInicio = dto.isTesteGratis() ? LocalDate.now().plusDays(novoPlano.getDiasTeste()) : LocalDate.now();
        novoPlano.setDataInicio(dataInicio);
        novoPlano.setDataAdesao(dataInicio);
        novoPlano.setDataFim(calcularDataFim(dataInicio, periodo));

        // preço: pegar do template
        if (periodo == Periodo.MENSAL) {
            novoPlano.setValorCobrado(template.getPrecoMensal() != null ? template.getPrecoMensal().doubleValue() : null);
        } else {
            novoPlano.setValorCobrado(
                    template.getPrecoMensalComDescontoAnual() != null
                            ? template.getPrecoMensalComDescontoAnual().doubleValue()
                            : (template.getPrecoAnual() != null ? template.getPrecoAnual().doubleValue() : null)
            );
        }

        novoPlano.setAtivo(true);
        novoPlano.setEmpresa(empresa);

        novoPlano = gerenciamentoPlanoRepository.save(novoPlano);

        empresa.setAtivo(true);
        empresa.setGerenciamentoPlano(novoPlano);
        empresaPort.save(empresa);
        return novoPlano;
    }


    private LocalDate calcularDataFim(LocalDate inicio, Periodo periodo) {
        if (periodo == Periodo.MENSAL) {
            return inicio.plusMonths(1).minusDays(1);
        } else {
            return inicio.plusYears(1).minusDays(1);
        }
    }

    @Transactional
    public GerenciamentoPlano alterarPlano(Integer gerenciamentoPlanoId, AlterarPlanoDto dto) {
        if (gerenciamentoPlanoId == null) {
            throw new IllegalArgumentException("Id do GerenciamentoPlano é obrigatório.");
        }
        if (dto == null) {
            throw new IllegalArgumentException("Dados para alteração não informados.");
        }

        GerenciamentoPlano gp = gerenciamentoPlanoRepository.findById(gerenciamentoPlanoId)
                .orElseThrow(() -> new EntityNotFoundException("GerenciamentoPlano não encontrado: " + gerenciamentoPlanoId));

        PlanoTemplate novoTemplate = planoTemplateRepository.findById(dto.getPlanoTemplateId())
                .orElseThrow(() -> new EntityNotFoundException("PlanoTemplate não encontrado: " + dto.getPlanoTemplateId()));


        gp.setPlanoTemplate(novoTemplate);

        Periodo novoPeriodo = dto.getPeriodo();
        if (novoPeriodo == null) {
            throw new IllegalArgumentException("Periodo é obrigatório no DTO.");
        }
        gp.setPeriodo(novoPeriodo);

        Double novoValor = null;
        if (novoPeriodo == Periodo.MENSAL) {
            novoValor = novoTemplate.getPrecoMensal() != null ? novoTemplate.getPrecoMensal().doubleValue() : null;
        } else {
            novoValor = novoTemplate.getPrecoMensalComDescontoAnual() != null
                    ? novoTemplate.getPrecoMensalComDescontoAnual().doubleValue()
                    : (novoTemplate.getPrecoAnual() != null ? novoTemplate.getPrecoAnual().doubleValue() : null);
        }
        gp.setValorCobrado(novoValor);

        LocalDate dataInicio = gp.getDataInicio() != null ? gp.getDataInicio() : LocalDate.now();
        gp.setDataInicio(dataInicio);
        gp.setDataFim(calcularDataFim(dataInicio, novoPeriodo));

        gp = gerenciamentoPlanoRepository.save(gp);


        if (gp.getEmpresa() != null) {
                var empresa = gp.getEmpresa();
                empresa.setGerenciamentoPlano(gp);
                empresaPort.save(empresa);
            }

        return gp;
    }

    @Transactional
    public void cancelarPlano(Integer empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));
        var gp = empresa.getGerenciamentoPlano();
        if (gp == null || !gp.isAtivo()) {
            throw new IllegalStateException("Empresa não possui um plano ativo");
        }
        if (!empresa.isAtivo()) {
            throw new IllegalStateException("Empresa não está ativa!");
        }
        empresa.setAtivo(false);
        gp.setAtivo(false);
        gp.setDataFim(LocalDate.now());
        empresaPort.save(empresa);
    }

    public Optional<GerenciamentoPlano> buscarPorEmpresa(Integer empresaId) {
        return empresaRepository.findById(empresaId).map(Empresa::getGerenciamentoPlano);
    }

    @Transactional
    public GerenciamentoPlano renovarPlano(Integer empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        GerenciamentoPlano planoAtual = empresa.getGerenciamentoPlano();
        if (planoAtual == null || !planoAtual.isAtivo()) {
            throw new IllegalStateException("Empresa não possui um plano ativo para renovar");
        }

        // Atualiza datas do plano existente
        LocalDate novaDataInicio = LocalDate.now();
        planoAtual.setDataInicio(novaDataInicio);
        planoAtual.setDataFim(calcularDataFim(novaDataInicio, planoAtual.getPeriodo()));

        // Mantém valor, template e período
        planoAtual.setValorCobrado(planoAtual.getValorCobrado());
        planoAtual.setAtivo(true);

        // ATUALIZA E SALVA A RENOVACAO
        gerenciamentoPlanoRepository.save(planoAtual);

        System.out.println("[RENOVAÇÃO] Plano da empresa " + empresaId + " renovado. "
                + "Data início: " + planoAtual.getDataInicio()
                + ", Data fim: " + planoAtual.getDataFim());

        return planoAtual;
    }

}

package school.sptech.controller.plano.dto;

import org.springframework.stereotype.Component;
import school.sptech.entity.plano.GerenciamentoPlano;
import school.sptech.entity.plano.PlanoTemplate;

@Component
public class GerenciamentoPlanoMapper {

    public GerenciamentoPlanoResponse toResponse(GerenciamentoPlano gp) {
        if (gp == null) return null;

        GerenciamentoPlanoResponse r = new GerenciamentoPlanoResponse();
        r.setId(gp.getId());
        r.setPeriodo(gp.getPeriodo());
        r.setDataAdesao(gp.getDataAdesao());
        r.setDataInicio(gp.getDataInicio());
        r.setDataFim(gp.getDataFim());
        r.setTesteGratis(gp.isTesteGratis());
        r.setDiasTeste(gp.getDiasTeste());
        r.setAtivo(gp.isAtivo());
        r.setValorCobrado(gp.getValorCobrado());

        if (gp.getEmpresa() != null) {
            r.setEmpresaId(gp.getEmpresa().getId());
            r.setEmpresaNome(gp.getEmpresa().getNome());
        }

        PlanoTemplate t = gp.getPlanoTemplate();
        if (t != null) {
            PlanoTemplateResponse ptr = new PlanoTemplateResponse();
            ptr.setId(t.getId());
            ptr.setTipo(t.getTipo() != null ? t.getTipo() : null);
            ptr.setDescricao(t.getDescricao());
            ptr.setPrecoMensal(t.getPrecoMensal());
            ptr.setPrecoMensalComDescontoAnual(t.getPrecoMensalComDescontoAnual());
            ptr.setPrecoAnual(t.getPrecoAnual());
            ptr.setQtdUsuarios(t.getQtdUsuarios());
            ptr.setQtdSuperUsuarios(t.getQtdSuperUsuarios());
            ptr.setAcessoRelatorioWhatsApp(Boolean.valueOf(t.isAcessoRelatorioWhatsApp()));
            ptr.setAcessoDashboard(Boolean.valueOf(t.isAcessoDashboard()));
            r.setPlanoTemplate(ptr);
        }

        return r;
    }
}

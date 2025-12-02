package school.sptech.controller.plano.dto;
import school.sptech.entity.plano.PlanoTemplate;
import java.util.List;
import java.util.stream.Collectors;

public class PlanoTemplateMapper {

    private PlanoTemplateMapper() {}

    public static PlanoTemplateResponse toResponse(PlanoTemplate p) {
        if (p == null) return null;

        PlanoTemplateResponse r = new PlanoTemplateResponse();
        r.setId(p.getId());
        r.setTipo(p.getTipo());
        r.setDescricao(p.getDescricao());
        r.setPrecoMensal(p.getPrecoMensal());
        r.setPrecoMensalComDescontoAnual(p.getPrecoMensalComDescontoAnual());
        r.setPrecoAnual(p.getPrecoAnual());
        r.setQtdUsuarios(p.getQtdUsuarios());
        r.setQtdSuperUsuarios(p.getQtdSuperUsuarios());
        r.setAcessoRelatorioWhatsApp(p.isAcessoRelatorioWhatsApp());
        r.setAcessoDashboard(p.isAcessoDashboard());

        return r;
    }

    public static List<PlanoTemplateResponse> toResponse(List<PlanoTemplate> list) {
        return list.stream()
                .map(PlanoTemplateMapper::toResponse)
                .collect(Collectors.toList());
    }
}

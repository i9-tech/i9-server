package school.sptech.controller.plano.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import school.sptech.entity.plano.Periodo;

public class ContratarPlanoDto {

    @NotNull(message = "O ID da empresa é obrigatório.")
    @Min(value = 1, message = "O ID da empresa deve ser maior que zero.")
    private Integer empresaId;

    @NotNull(message = "O ID do plano é obrigatório.")
    @Min(value = 1, message = "O ID do plano deve ser maior que zero.")
    private Integer planoTemplateId;

    @NotNull(message = "O período do plano é obrigatório.")
    private Periodo periodo;

    private boolean testeGratis;

    public ContratarPlanoDto() {}

    public ContratarPlanoDto(Integer empresaId, Integer planoTemplateId, Periodo periodo, boolean testeGratis) {
        this.empresaId = empresaId;
        this.planoTemplateId = planoTemplateId;
        this.periodo = periodo;
        this.testeGratis = testeGratis;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public Integer getPlanoTemplateId() {
        return planoTemplateId;
    }

    public void setPlanoTemplateId(Integer planoTemplateId) {
        this.planoTemplateId = planoTemplateId;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public boolean isTesteGratis() {
        return testeGratis;
    }

    public void setTesteGratis(boolean testeGratis) {
        this.testeGratis = testeGratis;
    }
}

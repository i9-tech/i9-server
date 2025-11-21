package school.sptech.controller.plano.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import school.sptech.entity.plano.Periodo;

@Schema(
        name = "ContratarPlanoDTO",
        description = "DTO para transferência de dados necessários para contratar um plano de assinatura.")
public class ContratarPlanoDto {

    @NotNull(message = "O ID da empresa é obrigatório.")
    @Min(value = 1, message = "O ID da empresa deve ser maior que zero.")
    @Schema(
            description = "ID da empresa que irá contratar o plano.",
            example = "1",
            required = true
    )
    private Integer empresaId;

    @NotNull(message = "O ID do plano é obrigatório.")
    @Min(value = 1, message = "O ID do plano deve ser maior que zero.")
    @Schema(
            description = "ID do template do plano que será contratado.",
            example = "2",
            required = true
    )
    private Integer planoTemplateId;

    @NotNull(message = "O período do plano é obrigatório.")
    @Schema(
            description = "Período de contratação do plano. Pode ser MENSAL ou ANUAL.",
            example = "MENSAL",
            required = true
    )
    private Periodo periodo;

    @Schema(
            description = "Indica se o plano será iniciado com teste grátis.",
            example = "true",
            required = false
    )
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

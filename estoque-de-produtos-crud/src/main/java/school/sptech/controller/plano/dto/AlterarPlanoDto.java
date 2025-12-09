package school.sptech.controller.plano.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import school.sptech.entity.plano.Periodo;

@Schema(
        name = "AlterarPlanoDTO",
        description = "DTO para transferência de dados de alteração de um plano de assinatura.")
public class AlterarPlanoDto {

    @NotNull(message = "O ID do plano é obrigatório.")
    @Min(value = 1, message = "O ID do plano deve ser maior que zero.")
    @Schema(
            description = "ID do template do plano que será alterado.",
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

    public AlterarPlanoDto() {
    }

    public AlterarPlanoDto(Integer planoTemplateId, Periodo periodo) {
        this.planoTemplateId = planoTemplateId;
        this.periodo = periodo;
    }

    public @NotNull(message = "O ID do plano é obrigatório.") @Min(value = 1, message = "O ID do plano deve ser maior que zero.") Integer getPlanoTemplateId() {
        return planoTemplateId;
    }

    public void setPlanoTemplateId(@NotNull(message = "O ID do plano é obrigatório.") @Min(value = 1, message = "O ID do plano deve ser maior que zero.") Integer planoTemplateId) {
        this.planoTemplateId = planoTemplateId;
    }

    public @NotNull(message = "O período do plano é obrigatório.") Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(@NotNull(message = "O período do plano é obrigatório.") Periodo periodo) {
        this.periodo = periodo;
    }
}

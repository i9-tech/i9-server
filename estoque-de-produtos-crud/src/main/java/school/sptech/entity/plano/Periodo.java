package school.sptech.entity.plano;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Periodo", description = "Define o período do plano: mensal ou anual")
public enum Periodo {
    @Schema(description = "Plano mensal")
    MENSAL,

    @Schema(description = "Plano anual")
    ANUAL
}

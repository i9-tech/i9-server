package school.sptech.entity.funcionario;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TipoIdentificador {
    @JsonProperty("CPF")
    CPF,
    @JsonProperty("EMAIL")
    EMAIL,
    @JsonProperty("TELEFONE")
    TELEFONE,
    @JsonProperty("MATRICULA")
    MATRICULA
}

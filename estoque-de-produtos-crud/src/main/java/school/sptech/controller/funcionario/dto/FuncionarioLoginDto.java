package school.sptech.controller.funcionario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class FuncionarioLoginDto {

    @Schema(
            description = "CPF da pessoa empregada. Precisa ser informado com formatação para login.",
            example = "999.999.999-99",
            maxLength = 14,
            type = "string"
    )
    private String cpf;

    @Schema(
            description = "Senha que dará acesso ao sistema para a pessoa contratada.",
            type = "string"
    )
    private String senha;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

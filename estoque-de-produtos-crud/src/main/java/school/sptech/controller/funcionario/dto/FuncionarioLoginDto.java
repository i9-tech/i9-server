package school.sptech.controller.funcionario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class FuncionarioLoginDto {
    @Schema(description = "Login do usuário", example = "502.3400.085-6")
    private String cpf;
    @Schema(description = "Senha do usuário", example = "10@5023400856")
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

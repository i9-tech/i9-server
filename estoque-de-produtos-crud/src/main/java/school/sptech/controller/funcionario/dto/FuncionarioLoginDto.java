package school.sptech.controller.funcionario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class FuncionarioLoginDto {
    @Schema(description = "Login do usuário", example = "502.3400.085-6")
    @NotBlank(message = "CPF não pode ser vazio")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido. O formato correto é xxx.xxx.xxx-xx")
    private String cpf;

    @Schema(description = "Senha do usuário", example = "10@5023400856")
    @NotBlank(message = "Senha não pode ser vazia")
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

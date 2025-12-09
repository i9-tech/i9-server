package school.sptech.controller.funcionario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RedefinirSenhaDto {
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 11, message = "A senha deve ter no mínimo 11 caracteres.")
    private String senha;
    private boolean primeiroAcesso;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isPrimeiroAcesso() {
        return primeiroAcesso;
    }

    public void setPrimeiroAcesso(boolean primeiroAcesso) {
        this.primeiroAcesso = primeiroAcesso;
    }
}

package school.sptech.controller.funcionario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AlterarSenhaDto {

    @NotBlank(message = "A senha atual é obrigatória")
    private String senhaAtual;

    @NotBlank(message = "A nova senha é obrigatória")
    @Size(min = 8, message = "A nova senha deve ter no mínimo 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).+$",
            message = "A nova senha deve conter pelo menos: 1 letra maiúscula, 1 minúscula, 1 número e 1 caractere especial"
    )
    private String novaSenha;

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }
}
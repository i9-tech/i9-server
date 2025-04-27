package school.sptech.controller.empresa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmpresaAtualizarDto {

    @NotBlank(message = "O endereco não pode estar em branco")
    @Size(max = 100, message = "O endereco deve ter no máximo 100 caracteres")
    private String endereco;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    public @NotBlank(message = "O endereco não pode estar em branco") @Size(max = 100, message = "O endereco deve ter no máximo 100 caracteres") String getEndereco() {
        return endereco;
    }

    public void setEndereco(@NotBlank(message = "O endereco não pode estar em branco") @Size(max = 100, message = "O endereco deve ter no máximo 100 caracteres") String endereco) {
        this.endereco = endereco;
    }

    public @NotBlank(message = "O nome não pode estar em branco") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome não pode estar em branco") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres") String nome) {
        this.nome = nome;
    }
}

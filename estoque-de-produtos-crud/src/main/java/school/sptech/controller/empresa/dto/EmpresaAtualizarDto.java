package school.sptech.controller.empresa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "EmpresaAtualizarDTO",
        description = "DTO para transferência de novos dados da empresa para atualização.")
public class EmpresaAtualizarDto {

    @NotBlank(message = "O endereço não pode estar em branco.")
    @Size(max = 100, message = "O endereço deve ter no máximo 100 caracteres.")
    @NotBlank(message = "O endereço não pode estar em branco.")
    @Schema(
            description = "Endereço físico ou comercial da empresa.",
            example = "Rua Pedro Mineiro, 262 - Paulista, São Paulo - SP",
            type = "string"
    )
    private String endereco;

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Schema(
            description = "Nome fantasia ou razão social da empresa.",
            example = "Restaurante Tauá - Lanches & Pastéis",
            type = "string"
    )
    private String nome;

    private String whatsapp;

    public @NotBlank(message = "O endereço não pode estar em branco.") @Size(max = 100, message = "O endereço deve ter no máximo 100 caracteres.") @NotBlank(message = "O endereço não pode estar em branco.") String getEndereco() {
        return endereco;
    }

    public void setEndereco(@NotBlank(message = "O endereço não pode estar em branco.") @Size(max = 100, message = "O endereço deve ter no máximo 100 caracteres.") @NotBlank(message = "O endereço não pode estar em branco.") String endereco) {
        this.endereco = endereco;
    }

    public @NotBlank(message = "O nome não pode estar em branco.") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome não pode estar em branco.") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.") String nome) {
        this.nome = nome;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
}

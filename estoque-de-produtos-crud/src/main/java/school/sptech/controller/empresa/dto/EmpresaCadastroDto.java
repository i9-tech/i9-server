package school.sptech.controller.empresa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(
        name = "EmpresaCadastroDTO",
        description = "DTO para transferência de dados cadastrais da empresa.")
public class EmpresaCadastroDto {

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Schema(
            description = "Nome fantasia ou razão social da empresa.",
            example = "Restaurante Tauá",
            type = "string"
    )
    private String nome;

    @NotBlank(message = "O CNPJ não pode estar em branco.")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter no 14 caracteres.")
    @Schema(
            description = "CNPJ da empresa. Pode ser informado com ou sem formatação (ex: 12345678000195 ou 12.345.678/0001-95).",
            example = "12.345.678/0001-95",
            minLength = 14,
            maxLength = 18,
            type = "string"
    )
    private String cnpj;

    @NotBlank(message = "O endereço não pode estar em branco.")
    @Schema(
            description = "Endereço físico ou comercial da empresa.",
            example = "Rua Haddock Lobo, 595 - Consolação, São Paulo - SP",
            type = "string"
    )
    private String endereco;

    @NotNull(message = "A data não pode estar em branco.")
    @Schema(
            description = "Data em que a empresa foi cadastrada no sistema.",
            example = "25-04-2025",
            format = "date",
            type = "string"
    )
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataCadastro;

    private String whatsapp;

    public @NotBlank(message = "O nome não pode estar em branco.") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome não pode estar em branco.") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.") String nome) {
        this.nome = nome;
    }

    public @NotBlank(message = "O CNPJ não pode estar em branco.") @Size(min = 14, max = 14, message = "O CNPJ deve ter no 14 caracteres.") String getCnpj() {
        return cnpj;
    }

    public void setCnpj(@NotBlank(message = "O CNPJ não pode estar em branco.") @Size(min = 14, max = 14, message = "O CNPJ deve ter no 14 caracteres.") String cnpj) {
        this.cnpj = cnpj;
    }

    public @NotBlank(message = "O endereço não pode estar em branco.") String getEndereco() {
        return endereco;
    }

    public void setEndereco(@NotBlank(message = "O endereço não pode estar em branco.") String endereco) {
        this.endereco = endereco;
    }

    public @NotNull(message = "A data não pode estar em branco.") LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(@NotNull(message = "A data não pode estar em branco.") LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
}

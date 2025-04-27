package school.sptech.controller.empresa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class EmpresaCadastroDto {

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "O cnpj não pode estar em branco")
    @Size(min = 14, max = 14, message = "O cnpj deve ter no 14 caracteres")
    private String cnpj;

    @NotBlank(message = "O endereco não pode estar em branco")
    private String endereco;

    @NotNull(message = "A data não pode estar em branco")
    private LocalDate dataCadastro;

    public @NotBlank(message = "O nome não pode estar em branco") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome não pode estar em branco") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres") String nome) {
        this.nome = nome;
    }

    public @NotBlank(message = "O cnpj não pode estar em branco") @Size(min = 14, max = 14, message = "O cnpj deve ter no 14 caracteres") String getCnpj() {
        return cnpj;
    }

    public void setCnpj(@NotBlank(message = "O cnpj não pode estar em branco") @Size(min = 14, max = 14, message = "O cnpj deve ter no 14 caracteres") String cnpj) {
        this.cnpj = cnpj;
    }

    public @NotBlank(message = "O endereco não pode estar em branco") String getEndereco() {
        return endereco;
    }

    public void setEndereco(@NotBlank(message = "O endereco não pode estar em branco") String endereco) {
        this.endereco = endereco;
    }

    public @NotNull(message = "A data não pode estar em branco") LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(@NotNull(message = "A data não pode estar em branco") LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}

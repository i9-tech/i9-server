package school.sptech.controller.funcionario.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.cglib.core.Local;
import school.sptech.entity.empresa.Empresa;

import java.time.LocalDate;
import java.util.Date;

@Schema(
        name = "FuncionarioRequestDto",
        description = "DTO para transferência de dados cadastrais ou de atualização do funcionário.")
public class FuncionarioRequestDto {

    @Size(min = 3, max = 40)
    @NotBlank(message = "O nome é obrigatório")
    @Schema(
            description = "Nome de registro ou social de pessoas que empregam uma determinada empresa.",
            example = "Agatha Nunes",
            type = "string"
    )
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "O cpf deve ser no formato 999.999.999-99 ")
    @Schema(
            description = "CPF da pessoa empregada. Precisa ser informado com formatação.",
            example = "999.999.999-99",
            maxLength = 14,
            type = "string"
    )
    private String cpf;

    @NotBlank(message = "O cargo é obrigatório")
    @Schema(
            description = "Cargo da pessoa contratada pela empresa.",
            example = "Atendente",
            type = "string"
    )
    private String cargo;

    @NotNull(message = "A data de admissão é obrigatória.")
    @PastOrPresent(message = "A data de admissão não pode ser no futuro.")
    @Schema(
            description = "Data em que a pessoa empregada foi admitida. A data não pode estar no futuro.",
            example = "2025-04-26T16:45:32.000Z",
            format = "date-time",
            type = "string"
    )
    //definir um padrão de armazenamento
    private LocalDate dataAdmissao;

    @Schema(
            description = "Indica se a pessoa tem acesso ao setor da cozinha. 'true' define acesso, 'false' restringe o acesso.",
            example = "true",
            type = "boolean"
    )
    private boolean acessoSetorCozinha;

    @Schema(
            description = "Indica se a pessoa tem acesso ao setor de estoque. 'true' define acesso, 'false' restringe o acesso.",
            example = "true",
            type = "boolean"
    )
    private boolean acessoSetorEstoque;

    @Schema(
            description = "Indica se a pessoa tem acesso ao setor de atendimento. 'true' define acesso, 'false' restringe o acesso.",
            example = "true",
            type = "boolean"
    )
    private boolean acessoSetorAtendimento;

    @Schema(
            description = "Indica se a pessoa tem acesso proprietário ao sistema, uma permissão que dá acesso a todos os setores e funcionalidades. 'true' define acesso, 'false' restringe o acesso.",
            example = "true",
            type = "boolean"
    )
    private boolean proprietario;

    @Size(min = 11,
            message = "A senha deve ter no mínimo 11 caracteres.")
    @Schema(
            description = "Senha que dará acesso ao sistema para a pessoa contratada.",
            hidden = true,
            type = "string"
    )
    private String senha;


    public @Size(min = 3, max = 40) @NotBlank(message = "O nome é obrigatório") String getNome() {
        return nome;
    }

    public void setNome(@Size(min = 3, max = 40) @NotBlank(message = "O nome é obrigatório") String nome) {
        this.nome = nome;
    }

    public @NotBlank(message = "O CPF é obrigatório") @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "O cpf deve ser no formato 999.999.999-99 ") String getCpf() {
        return cpf;
    }

    public void setCpf(@NotBlank(message = "O CPF é obrigatório") @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "O cpf deve ser no formato 999.999.999-99 ") String cpf) {
        this.cpf = cpf;
    }

    public @NotBlank(message = "O cargo é obrigatório") String getCargo() {
        return cargo;
    }

    public void setCargo(@NotBlank(message = "O cargo é obrigatório") String cargo) {
        this.cargo = cargo;
    }

    public @NotNull(message = "A data de admissão é obrigatória.") @PastOrPresent(message = "A data de admissão não pode ser no futuro.") LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(@NotNull(message = "A data de admissão é obrigatória.") @PastOrPresent(message = "A data de admissão não pode ser no futuro.") LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public boolean isAcessoSetorCozinha() {
        return acessoSetorCozinha;
    }

    public void setAcessoSetorCozinha(boolean acessoSetorCozinha) {
        this.acessoSetorCozinha = acessoSetorCozinha;
    }

    public boolean isAcessoSetorEstoque() {
        return acessoSetorEstoque;
    }

    public void setAcessoSetorEstoque(boolean acessoSetorEstoque) {
        this.acessoSetorEstoque = acessoSetorEstoque;
    }

    public boolean isAcessoSetorAtendimento() {
        return acessoSetorAtendimento;
    }

    public void setAcessoSetorAtendimento(boolean acessoSetorAtendimento) {
        this.acessoSetorAtendimento = acessoSetorAtendimento;
    }

    public boolean isProprietario() {
        return proprietario;
    }

    public void setProprietario(boolean proprietario) {
        this.proprietario = proprietario;
    }

    public @Size(min = 11,
            message = "A senha deve ter no mínimo 11 caracteres.") String getSenha() {
        return senha;
    }

    public void setSenha(@Size(min = 11,
            message = "A senha deve ter no mínimo 11 caracteres.") String senha) {
        this.senha = senha;
    }
}

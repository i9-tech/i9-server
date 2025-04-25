package school.sptech.controller.funcionario.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import school.sptech.entity.empresa.Empresa;

import java.time.LocalDate;
import java.util.Date;

public class FuncionarioRequestDto {
    @Size(min = 3, max = 20)
    @Schema(description = "Nome do usuário", example = "Isabela Rosa")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "O cpf deve ser no formato 999.999.999-99 ")
    @Schema(description = "CPF do usuário", example = "123.456.789-00")
    private String cpf;

    @NotBlank(message = "O cargo é obrigatório")
    @Schema(description = "Cargo do funcionário", example = "Cozinheira")
    private String cargo;

    @NotNull(message = "A data de admissão é obrigatória")
    @PastOrPresent(message = "A data de admissão não pode ser no futuro")
    @Schema(description = "Data de admissão do funcionário", example = "17/07/2004")
    //definir um padrão de armazenamento
    private Date dataAdmissao;

    @Schema(description = "Acesso ao setor da cozinha", example = "true")
    private boolean acessoSetorCozinha;

    @Schema(description = "Acesso ao setor do estoque", example = "true")
    private boolean acessoSetorEstoque;

    @Schema(description = "Acesso ao setor do atendimento", example = "true")
    private boolean acessoSetorAtendimento;

    @Schema(description = "Se o funcionário é proprietário", example = "false")
    private boolean proprietario;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 11,
            message = "A senha deve ter no mínimo 11 caracteres")
    @Schema(description = "Senha de acesso", example = "10@50234000856")
    private String senha;

    public FuncionarioRequestDto(String nome, String cpf, String cargo, Date dataAdmissao, boolean acessoSetorCozinha, boolean acessoSetorEstoque, boolean acessoSetorAtendimento, boolean proprietario, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.dataAdmissao = dataAdmissao;
        this.acessoSetorCozinha = acessoSetorCozinha;
        this.acessoSetorEstoque = acessoSetorEstoque;
        this.acessoSetorAtendimento = acessoSetorAtendimento;
        this.proprietario = proprietario;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

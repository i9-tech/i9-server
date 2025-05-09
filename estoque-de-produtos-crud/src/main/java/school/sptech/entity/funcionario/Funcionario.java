package school.sptech.entity.funcionario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import school.sptech.controller.empresa.dto.EmpresaListagemDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.entity.empresa.Empresa;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "funcionario")
@Schema(
        name = "Funcionário",
        description = "Entidade que representa os funcionários cadastrados de uma determinada empresa. É a responsável para execução direta de operações relacionadas à gestão empresarial.")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único do funcionário.",
            example = "1",
            type = "integer"
    )
    private int id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 20)
    @Schema(
            description = "Nome de registro ou social de pessoas que empregam uma determinada empresa.",
            example = "Agatha Nunes",
            type = "string"
    )
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve estar no formato 999.999.999-99")
    @Schema(
            description = "CPF da pessoa empregada. Precisa ser informado com formatação.",
            example = "999.999.999-99",
            maxLength = 14,
            type = "string"
    )
    private String cpf;

    @NotBlank(message = "O cargo é obrigatório.")
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

    @Schema(
            description = "Indica se o contrato da pessoa está ativo no sistema da empresa. 'true' para ativa, 'false' para inativa.",
            example = "true",
            type = "boolean"
    )
    private boolean ativo = true;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 11, message = "A senha deve ter no mínimo 11 caracteres.")
    @Schema(
            description = "Senha que dará acesso ao sistema para a pessoa contratada.",
            hidden = true,
            type = "string"
    )
    private String senha;

    @NotNull(message = "A empresa vinculada é obrigatória.")
    @ManyToOne
    @Schema(
            description = "Empresa associada à pessoa contratada.",
            implementation = EmpresaListagemDto.class
    )
    private Empresa empresa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
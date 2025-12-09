package school.sptech.controller.funcionario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.entity.funcionario.TipoIdentificador;

import java.time.LocalDate;

public class FuncionarioResponseDto {

    private Integer id;

    @Schema(
            description = "Nome de registro ou social de pessoas que empregam uma determinada empresa.",
            example = "Agatha Nunes",
            type = "string"
    )
    private String nome;

    @Schema(
            description = "CPF da pessoa empregada. Precisa ser informado com formatação.",
            example = "999.999.999-99",
            maxLength = 14,
            type = "string"
    )
    private String cpf;

    @Schema(
            description = "Define qual identificador será usado para login.",
            example = "CPF",
            allowableValues = {"CPF", "EMAIL", "MATRICULA", "TELEFONE"}
    )
    private TipoIdentificador identificadorPrincipal;

    @Schema(
            description = "Login do funcionário, conforme definido pelo tipo de identificador escolhido (CPF, email, telefone ou matrícula).",
            example = "999.999.999-99"
    )
    private String login;


    @Schema(
            description = "Cargo da pessoa contratada pela empresa.",
            example = "Atendente",
            type = "string"
    )
    private String cargo;

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
            description = "Indica se a pessoa já fez o primeiro acesso ao sistema, uma flag que caso verdadeira exigirá a redefinição de senha.",
            example = "true",
            type = "boolean"
    )
    private boolean primeiroAcesso;


    @Schema(
            description = "Endereço de e-mail do funcionário utilizado para reset de senha.",
            example = "funcionario@empresa.com",
            type = "string"
    )
    private String email;

    public FuncionarioResponseDto(Integer id, String nome, String cpf, TipoIdentificador identificadorPrincipal, String login, String cargo,
                                  LocalDate dataAdmissao, boolean acessoSetorCozinha, boolean acessoSetorEstoque,
                                  boolean acessoSetorAtendimento, boolean proprietario,  boolean primeiroAcesso, String email) {

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.identificadorPrincipal = identificadorPrincipal;
        this.login = login;
        this.cargo = cargo;
        this.dataAdmissao = dataAdmissao;
        this.acessoSetorCozinha = acessoSetorCozinha;
        this.acessoSetorEstoque = acessoSetorEstoque;
        this.acessoSetorAtendimento = acessoSetorAtendimento;
        this.proprietario = proprietario;
        this.primeiroAcesso = primeiroAcesso;
        this.email = email;

    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public TipoIdentificador getIdentificadorPrincipal() {
        return identificadorPrincipal;
    }

    public void setIdentificadorPrincipal(TipoIdentificador identificadorPrincipal) {
        this.identificadorPrincipal = identificadorPrincipal;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public boolean isPrimeiroAcesso() {return primeiroAcesso;}

    public void setPrimeiroAcesso(boolean primeiroAcesso) {this.primeiroAcesso = primeiroAcesso;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

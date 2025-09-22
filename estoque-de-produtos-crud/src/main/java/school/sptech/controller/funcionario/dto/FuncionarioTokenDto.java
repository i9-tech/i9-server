package school.sptech.controller.funcionario.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class FuncionarioTokenDto {

    @Schema(
            description = "Identificador único do funcionário.",
            example = "1",
            type = "integer"
    )
    private Integer userId;

    @Schema(
            description = "Empresa da qual o usuário foi contratado.",
            example = "1",
            type = "integer"
    )
    private Integer empresaId;

    @Schema(
            description = "Nome de registro ou social de pessoas que empregam uma determinada empresa.",
            example = "Agatha Nunes",
            type = "string"
    )
    private String nome;

    @Schema(
            description = "Token de acesso que será gerado uma vez que o usuário tem o login bem sucedido.",
            type = "string"
    )
    private String token;

    @Schema(
            description = "Indica se a pessoa já fez o primeiro acesso ao sistema, uma flag que caso verdadeira exigirá a redefinição de senha.",
            example = "true",
            type = "boolean"
    )
    private boolean primeiroAcesso;


    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isPrimeiroAcesso() {
        return primeiroAcesso;
    }

    public void setPrimeiroAcesso(boolean primeiroAcesso) {
        this.primeiroAcesso = primeiroAcesso;
    }
}
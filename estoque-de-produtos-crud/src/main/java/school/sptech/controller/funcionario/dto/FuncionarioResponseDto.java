package school.sptech.controller.funcionario.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class FuncionarioResponseDto {

    @Schema(description = "Nome do usuário", example = "Isabela Rosa")
    private String nome;

    @Schema(description = "CPF do usuário", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Cargo do usuário", example = "Cozinheira")
    private String cargo;

    @Schema(description = "Data de admissão", example = "17/07/2004")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataAdmissao;

    @Schema(description = "Acesso ao setor da cozinha", example = "true")
    private boolean acessoSetorCozinha;

    @Schema(description = "Acesso ao setor do estoque", example = "true")
    private boolean acessoSetorEstoque;

    @Schema(description = "Acesso ao setor do atendimento", example = "true")
    private boolean acessoSetorAtendimento;

    @Schema(description = "Se o funcionário é proprietário", example = "false")
    private boolean proprietario;

    public FuncionarioResponseDto( String nome, String cpf, String cargo,
                                   LocalDate dataAdmissao, boolean acessoSetorCozinha, boolean acessoSetorEstoque,
                                   boolean acessoSetorAtendimento, boolean proprietario) {

        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.dataAdmissao = dataAdmissao;
        this.acessoSetorCozinha = acessoSetorCozinha;
        this.acessoSetorEstoque = acessoSetorEstoque;
        this.acessoSetorAtendimento = acessoSetorAtendimento;
        this.proprietario = proprietario;

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

    public void setId(long id) {
    }
}

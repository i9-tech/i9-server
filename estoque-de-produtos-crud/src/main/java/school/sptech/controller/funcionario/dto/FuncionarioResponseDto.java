package school.sptech.controller.funcionario.dto;

import java.time.LocalDate;

public class FuncionarioResponseDto {
    private String nome;
    private String cpf;
    private String cargo;
    private LocalDate dataAdmissao;
    private boolean acessoSetorCozinha;
    private boolean acessoSetorEstoque;
    private boolean acessoSetorAtendimento;
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

}

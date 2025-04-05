package school.sptech.entity.funcionario;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String cpf;
    private String cargo;
    private LocalDate dataAdmissao;
    private boolean acessoSetorCozinha;
    private boolean acessoSetorEstoque;
    private boolean acessoSetorAtendimento;
    private boolean proprietario;
    private int fkEmpresa;
    private String senha;


    public void gerarSenha() {
        if (cpf != null) {
            this.senha = fkEmpresa + "@" + cpf;
        }
    }

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

    public boolean isProprietario() {
        return proprietario;
    }

    public void setProprietario(boolean proprietario) {
        this.proprietario = proprietario;
    }

    public int getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(int fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
}
package school.sptech.controller.colaborador.dto;

import school.sptech.entity.funcionario.Funcionario;

import java.time.LocalDate;

public class FuncionarioRequestDto {
    private String nome;
    private String cpf;
    private String cargo;
    private LocalDate dataAdmissao;
    private boolean acessoSetorCozinha;
    private boolean acessoSetorEstoque;
    private boolean acessoSetorAtendimento;
    private boolean proprietario;
    private String senha;

    //convertendo dto em entity
    public static Funcionario toEntity(FuncionarioRequestDto requestDto, int fkEmpresa){
        if (requestDto == null){
            return null;
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(requestDto.getNome());
        funcionario.setCpf(requestDto.getCpf());
        funcionario.setCargo(requestDto.getCargo());
        funcionario.setDataAdmissao(requestDto.getDataAdmissao());
        funcionario.setAcessoSetorCozinha(requestDto.isAcessoSetorCozinha());
        funcionario.setAcessoSetorEstoque(requestDto.isAcessoSetorEstoque());
        funcionario.setAcessoSetorAtendimento(requestDto.isAcessoSetorAtendimento());
        funcionario.setProprietario(requestDto.isProprietario());
        funcionario.setFkEmpresa(fkEmpresa);
        funcionario.setSenha(requestDto.getSenha());

        return funcionario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
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

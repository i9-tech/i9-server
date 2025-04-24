package school.sptech.entity.funcionario;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import school.sptech.entity.empresa.Empresa;

import java.time.LocalDate;


@Entity
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 20)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve estar no formato 999.999.999-99")
    private String cpf;

    @NotBlank(message = "O cargo é obrigatório")
    private String cargo;

    @NotNull(message = "A data de admissão é obrigatória")
    @PastOrPresent(message = "A data de admissão não pode ser no futuro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataAdmissao;

    private boolean acessoSetorCozinha;
    private boolean acessoSetorEstoque;
    private boolean acessoSetorAtendimento;
    private boolean proprietario;
    private boolean ativo = true;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 11, message = "A senha deve ter no mínimo 11 caracteres")
    private String senha;

    @NotNull(message = "A empresa vinculada é obrigatória")

    @ManyToOne
    private Empresa empresa;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public long getId() {
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
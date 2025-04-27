package school.sptech.controller.categoria.dto;

import jakarta.persistence.ManyToOne;
import school.sptech.entity.funcionario.Funcionario;

public class CategoriaListagemDto {
    private Integer id;
    private String nome;

    @ManyToOne
    private Funcionario funcionario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}

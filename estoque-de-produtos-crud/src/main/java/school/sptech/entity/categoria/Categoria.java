package school.sptech.entity.categoria;

import jakarta.persistence.*;
import school.sptech.controller.categoria.dto.CategoriaListagemDto;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.produto.Produto;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @ManyToOne
    private Funcionario funcionario;

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

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
}


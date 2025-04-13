package school.sptech.entity.categoria;

import jakarta.persistence.*;
import school.sptech.controller.categoria.dto.CategoriaListagemDto;
import school.sptech.entity.empresa.Empresa;

@Entity
@Table(name = "categoria")
public class Categoria extends CategoriaListagemDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @ManyToOne
    private Empresa empresa;

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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}


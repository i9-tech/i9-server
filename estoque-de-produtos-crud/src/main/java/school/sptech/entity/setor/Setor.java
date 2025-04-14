package school.sptech.entity.setor;

import jakarta.persistence.*;
import school.sptech.controller.setor.dto.SetorListagemDto;
import school.sptech.entity.empresa.Empresa;

@Entity
@Table(name = "setor")
public class Setor extends SetorListagemDto {

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

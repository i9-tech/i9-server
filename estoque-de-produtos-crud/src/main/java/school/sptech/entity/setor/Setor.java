package school.sptech.entity.setor;

import jakarta.persistence.*;
import school.sptech.controller.setor.dto.SetorListagemDto;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.funcionario.Funcionario;

@Entity
@Table(name = "setor")
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

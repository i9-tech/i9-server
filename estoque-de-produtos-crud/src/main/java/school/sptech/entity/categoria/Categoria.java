package school.sptech.entity.categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import school.sptech.controller.categoria.dto.CategoriaListagemDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.produto.Produto;

@Entity
@Table(name = "categoria")
@Schema(
        name = "Categoria",
        description = "Entidade que representa as categorias cadastradas de uma determinada empresa. É uma das bases para execução de operações relacionadas à gestão de produtos e pratos.")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único da categoria.",
            example = "1",
            type = "integer"
    )
    private Integer id;

    @Schema(
            description = "Nome da categoria desejada. É possível existir mais de um tipo de categoria por setor.",
            example = "Lanches Naturais",
            type = "string"
    )
    private String nome;

    @ManyToOne
    @Schema(
            description = "Funcionário associado à criação da categoria.",
            implementation = FuncionarioResponseDto.class
    )
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


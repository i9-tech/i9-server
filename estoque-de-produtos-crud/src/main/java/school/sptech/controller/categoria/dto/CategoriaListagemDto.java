package school.sptech.controller.categoria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.entity.funcionario.Funcionario;

@Schema(
        name = "CategoriaListagemDTO",
        description = "DTO para transferência de dados da categoria para listagem.")
public class CategoriaListagemDto {
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

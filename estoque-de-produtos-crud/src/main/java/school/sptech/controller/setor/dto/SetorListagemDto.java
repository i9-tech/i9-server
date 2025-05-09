package school.sptech.controller.setor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.entity.funcionario.Funcionario;

@Schema(
        name = "SetorListagemDTO",
        description = "DTO para transferência de dados do setor para listagem.")
public class SetorListagemDto {

    @Schema(
            description = "Identificador único do setor.",
            example = "1",
            type = "integer"
    )
    private Integer id;

    @Schema(
            description = "Nome do setor desejado. Não é possível criar mais de um setor com o mesmo nome.",
            example = "Pastelaria",
            type = "string"
    )
    private String nome;

    @ManyToOne
    @Schema(
            description = "Funcionário associado à criação do setor.",
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

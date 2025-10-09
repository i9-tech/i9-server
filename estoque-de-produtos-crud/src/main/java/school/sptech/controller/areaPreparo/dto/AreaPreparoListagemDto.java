package school.sptech.controller.areaPreparo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.entity.funcionario.Funcionario;

@Schema(
        name = "AreaPreparoListagemDTO",
        description = "DTO para transferência de dados da área de preparo para listagem.")
public class AreaPreparoListagemDto {
    @Schema(
            description = "Identificador único da área.",
            example = "1",
            type = "integer"
    )
    private Integer id;

    @Schema(
            description = "Nome da área desejada.",
            example = "Chaperia",
            type = "string"
    )
    private String nome;

    @ManyToOne
    @Schema(
            description = "Funcionário associado à criação da area.",
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

package school.sptech.controller.categoria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.entity.funcionario.Funcionario;

@Schema(
        name = "CategoriaAtualizarDTO",
        description = "DTO para transferência de novos dados da categoria para atualização.")
public class CategoriaAtualizarDto {

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Schema(
            description = "Nome da categoria desejada. É possível existir mais de um tipo de categoria por setor.",
            example = "Sobremesas",
            type = "string"
    )
    private String nome;

    @ManyToOne
    @Schema(
            description = "Funcionário associado à criação da categoria.",
            implementation = FuncionarioResponseDto.class
    )
    private Funcionario funcionario;

    public @NotBlank(message = "O nome não pode estar em branco.") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome não pode estar em branco.") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.") String nome) {
        this.nome = nome;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}

package school.sptech.controller.categoria.dto;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import school.sptech.entity.funcionario.Funcionario;

public class CategoriaAtualizarDto {

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @ManyToOne
    private Funcionario funcionario;

    public @NotBlank(message = "O nome não pode estar em branco") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome não pode estar em branco") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres") String nome) {
        this.nome = nome;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}

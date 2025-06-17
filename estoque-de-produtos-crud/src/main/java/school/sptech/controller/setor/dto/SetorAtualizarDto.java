package school.sptech.controller.setor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.entity.funcionario.Funcionario;

@Schema(
        name = "SetorAtualizarDTO",
        description = "DTO para transferência de novos dados do setor para atualização.")
public class SetorAtualizarDto {

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Schema(
            description = "Nome do setor desejado. Não é possível criar mais de um setor com o mesmo nome.",
            example = "Lanchonete",
            type = "string"
    )
    private String nome;

    @ManyToOne
    @Schema(
            description = "Funcionário associado à criação do setor.",
            implementation = FuncionarioResponseDto.class
    )
    private Funcionario funcionario;

    private String imagem;

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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}

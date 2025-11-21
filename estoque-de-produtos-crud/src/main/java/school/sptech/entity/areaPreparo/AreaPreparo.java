package school.sptech.entity.areaPreparo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.entity.funcionario.Funcionario;

@Entity
@Table(name = "area_preparo")
@Schema(
        name = "Área de Preparo",
        description = "Representa a área de preparo dos pratos na cozinha de uma empresa.")
public class AreaPreparo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único da área.",
            example = "1",
            type = "integer"
    )
    private Integer id;
    @Schema(
            description = "Nome da área desejada.",
            example = "Chapeiro",
            type = "string"
    )
    private String nome;

    @ManyToOne
    @Schema(
            description = "Funcionário associado à criação da área.",
            implementation = FuncionarioResponseDto.class
    )
    private Funcionario funcionario;

    public AreaPreparo(Integer id, String nome, Funcionario funcionario) {
        this.id = id;
        this.nome = nome;
        this.funcionario = funcionario;
    }

    public AreaPreparo() {
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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}

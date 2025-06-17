package school.sptech.controller.prato.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import school.sptech.controller.categoria.dto.CategoriaListagemDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.controller.setor.dto.SetorListagemDto;
import school.sptech.entity.categoria.Categoria;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.setor.Setor;

@Schema(
        name = "AtualizarPratoDTO",
        description = "DTO para transferência de novos dados do prato para atualização.")
public class AtualizarPratoDto {

    @NotBlank
    @Schema(
            description = "Nome do prato a ser atualizado.",
            example = "Lanche Natural",
            type = "string"
    )
    private String nome;


    @DecimalMin("1.0") @Positive
    @Schema(
            description = "Valor que prato será vendido. Deve ser maior que 0.",
            example = "22.00",
            type = "double"
    )
    private Double valorVenda;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    @Schema(
            description = "Descrição do prato a ser atualizado. Campo opcional que facilita visualização do prato.",
            example = "Pão com Gergilim, Alface, Frango e Molho.",
            type = "string",
            nullable = true
    )
    private String descricao;

    @Schema(
            description = "URL da imagem ligada ao prato atualizado.",
            example = "https://img.freepik.com/fotos-gratis/sanduiche_1339-1108.jpg",
            type = "string"
    )
    private String imagem;

    @Schema(
            description = "Indica se o prato está disponível no momento. 'true' define disponibilidade, 'false' define indisponibilidade.",
            example = "false",
            type = "boolean"
    )
    public boolean disponivel;

    @ManyToOne
    @Schema(
            description = "Funcionário associado à atualização do prato.",
            implementation = FuncionarioResponseDto.class
    )
    private Funcionario funcionario;


    @ManyToOne
    @Schema(
            description = "Setor que o prato pertencerá. É necessário informar setor e categoria para cadastro de prato.",
            implementation = SetorListagemDto.class
    )
    private Setor setor;


    @ManyToOne
    @Schema(
            description = "Categoria que o prato pertencerá. É necessário informar uma categoria existente do setor anteriormente informado.",
            implementation = CategoriaListagemDto.class
    )
    private Categoria categoria;

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }

    public @DecimalMin("1.0") @Positive Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(@DecimalMin("1.0") @Positive Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres") String getDescricao() {
        return descricao;
    }

    public void setDescricao(@Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres") String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}

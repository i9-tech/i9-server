package school.sptech.controller.produto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import school.sptech.controller.categoria.dto.CategoriaListagemDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.controller.setor.dto.SetorListagemDto;
import school.sptech.entity.categoria.Categoria;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.setor.Setor;

import java.time.LocalDate;

@Schema(
        name = "ProdutoCadastroDTO",
        description = "DTO para transferência de dados cadastrais do produto.")
public class ProdutoCadastroDto {

    @NotNull
    @Schema(
            description = "Código de identificação do produto.",
            example = "0122339475",
            type = "int"
    )
    private Integer codigo;

    private String imagem;

    @Schema(
            description = "Nome do produto a ser cadastrado.",
            example = "Bolinho Ana Maria",
            type = "string"
    )
    private String nome;

    @NotNull
    @Min(1)
    @Schema(
            description = "Quantidade a ser cadastrada do produto informado. Deve ser maior que 0 e menor que a quantidade máxima.",
            example = "15",
            type = "int"
    )
    private Integer quantidade;

    @NotNull
    @Schema(
            description = "Valor que produto foi comprado. Deve ser menor que o valor de venda.",
            example = "3.00",
            type = "double"
    )
    private Double valorCompra;

    @NotNull
    @Schema(
            description = "Valor que produto comprado será vendido. Deve ser maior que o valor de compra.",
            example = "5.00",
            type = "double"
    )
    private Double valorUnitario;

    @NotNull
    @Schema(
            description = "Quantidade mínima do estoque. Esse valor serve de parâmetro para possíveis alertas de estoque baixo. Deve ser menor que a quantidade máxima.",
            example = "3",
            type = "int"
    )
    private Integer quantidadeMin;

    @NotNull
    @Schema(
            description = "Quantidade máxima do estoque. Esse valor serve de parâmetro para possíveis alertas de estoque superlotado. Deve ser maior que a quantidade mínima.",
            example = "30",
            type = "int"
    )
    private Integer quantidadeMax;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    @Schema(
            description = "Descrição do produto a ser cadastrado. Campo opcional que facilita visualização do produto",
            example = "O Bolinho Ana Maria é um produto de confeitaria, embalado individualmente, ideal para lanches rápidos.",
            type = "string",
            nullable = true
    )
    private String descricao;

    @NotNull
    @Schema(
            description = "Data em que o produto foi registrado no sistema da empresa. A data não pode ser informada no futuro.",
            example = "2025-04-25",
            format = "date",
            type = "string"
    )
    private LocalDate dataRegistro;

    @Schema(
            description = "Setor que o produto pertencerá. É necessário informar setor e categoria para cadastro de produto.",
            implementation = SetorListagemDto.class
    )
    private Setor setor;

    @Schema(
            description = "Categoria que o produto pertencerá. É necessário informar uma categoria existente do setor anteriormente informado.",
            implementation = CategoriaListagemDto.class
    )
    private Categoria categoria;

    @Schema(
            description = "Funcionário associado à criação da categoria.",
            implementation = FuncionarioResponseDto.class
    )
    private Funcionario funcionario;

    public @NotNull Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(@NotNull Integer codigo) {
        this.codigo = codigo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public @NotNull @Min(1) Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(@NotNull @Min(1) Integer quantidade) {
        this.quantidade = quantidade;
    }

    public @NotNull Double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(@NotNull Double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public @NotNull Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(@NotNull Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public @NotNull Integer getQuantidadeMin() {
        return quantidadeMin;
    }

    public void setQuantidadeMin(@NotNull Integer quantidadeMin) {
        this.quantidadeMin = quantidadeMin;
    }

    public @NotNull Integer getQuantidadeMax() {
        return quantidadeMax;
    }

    public void setQuantidadeMax(@NotNull Integer quantidadeMax) {
        this.quantidadeMax = quantidadeMax;
    }

    public @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres") String getDescricao() {
        return descricao;
    }

    public void setDescricao(@Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres") String descricao) {
        this.descricao = descricao;
    }

    public @NotNull LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(@NotNull LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}

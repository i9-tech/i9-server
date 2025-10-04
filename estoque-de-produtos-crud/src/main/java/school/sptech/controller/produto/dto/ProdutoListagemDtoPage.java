package school.sptech.controller.produto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import school.sptech.controller.categoria.dto.CategoriaListagemDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.controller.setor.dto.SetorListagemDto;
import school.sptech.entity.categoria.Categoria;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.setor.Setor;

import java.time.LocalDate;

@Schema(
        name = "ProdutoListagemDTOPage",
        description = "DTO para transferência de dados do produto para listagem paginada.")
public class ProdutoListagemDtoPage {

    @Schema(
            description = "Identificador único do produto.",
            example = "1",
            type = "integer"
    )
    private Integer id;

    @Schema(
            description = "Código de identificação do produto.",
            example = "0122339475",
            type = "int"
    )
    private int codigo;

    private String imagem;


    @Schema(
            description = "Nome do produto a ser cadastrado.",
            example = "Bolinho Ana Maria",
            type = "string"
    )
    private String nome;


    @Schema(
            description = "Quantidade a ser cadastrada do produto informado. Deve ser maior que 0 e menor que a quantidade máxima.",
            example = "15",
            type = "int"
    )
    private int quantidade;


    @Schema(
            description = "Valor que produto foi comprado. Deve ser menor que o valor de venda.",
            example = "3.00",
            type = "double"
    )
    private double valorCompra;


    @Schema(
            description = "Valor que produto comprado será vendido. Deve ser maior que o valor de compra.",
            example = "5.00",
            type = "double"
    )
    private double valorUnitario;


    @Schema(
            description = "Quantidade mínima do estoque. Esse valor serve de parâmetro para possíveis alertas de estoque baixo. Deve ser menor que a quantidade máxima.",
            example = "3",
            type = "int"
    )
    private int quantidadeMin;


    @Schema(
            description = "Quantidade máxima do estoque. Esse valor serve de parâmetro para possíveis alertas de estoque superlotado. Deve ser maior que a quantidade mínima",
            example = "30",
            type = "int"
    )
    private int quantidadeMax;


    @Schema(
            description = "Descrição do produto a ser cadastrado. Campo opcional que facilita visualização do produto.",
            example = "O Bolinho Ana Maria é um produto de confeitaria, embalado individualmente, ideal para lanches rápidos.",
            type = "string",
            nullable = true
    )
    private String descricao;

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


    public ProdutoListagemDtoPage(Integer id, int codigo, String imagem, String nome, int quantidade, double valorCompra, double valorUnitario, int quantidadeMin, int quantidadeMax, String descricao, LocalDate dataRegistro, Setor setor, Categoria categoria, Funcionario funcionario) {
        this.id = id;
        this.codigo = codigo;
        this.imagem = imagem;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorCompra = valorCompra;
        this.valorUnitario = valorUnitario;
        this.quantidadeMin = quantidadeMin;
        this.quantidadeMax = quantidadeMax;
        this.descricao = descricao;
        this.dataRegistro = dataRegistro;
        this.setor = setor;
        this.categoria = categoria;
        this.funcionario = funcionario;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getQuantidadeMin() {
        return quantidadeMin;
    }

    public void setQuantidadeMin(int quantidadeMin) {
        this.quantidadeMin = quantidadeMin;
    }

    public int getQuantidadeMax() {
        return quantidadeMax;
    }

    public void setQuantidadeMax(int quantidadeMax) {
        this.quantidadeMax = quantidadeMax;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
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

package school.sptech.controller.prato.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import school.sptech.controller.categoria.dto.CategoriaListagemDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.controller.setor.dto.SetorListagemDto;
import school.sptech.entity.areaPreparo.AreaPreparo;
import school.sptech.entity.categoria.Categoria;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.setor.Setor;

@Schema(
        name = "RespostaPratoDTO",
        description = "DTO para transferência de dados do prato para listagem.")
public class RespostaPratoDto {

    @Schema(
            description = "Identificador único do prato.",
            example = "1",
            type = "integer"
    )
    private int id;


    @Schema(
            description = "Nome do prato.",
            example = "Lanche Natural",
            type = "string"
    )
    private String nome;


    @Schema(
            description = "URL da imagem ligada ao prato.",
            example = "https://img.freepik.com/fotos-gratis/sanduiche_1339-1108.jpg",
            type = "string"
    )
    private String imagem;

    @Schema(
            description = "Valor que prato é vendido.",
            example = "18.00",
            type = "double"
    )
    private Double valorVenda;

    @Schema(
            description = "Descrição do prato. Campo opcional que facilita visualização do prato.",
            example = "Pão, Alface, Tomate e Molho.",
            type = "string",
            nullable = true
    )
    private String descricao;

    @Schema(
            description = "Indica se o prato está disponível no momento. 'true' define disponibilidade, 'false' define indisponibilidade.",
            example = "true",
            type = "boolean"
    )
    private Boolean disponivel;


    @ManyToOne
    @Schema(
            description = "Funcionário associado à criação ou edição do prato.",
            implementation = FuncionarioResponseDto.class
    )
    private Funcionario funcionario;


    @ManyToOne
    @Schema(
            description = "Setor que o prato pertence. É necessário informar setor e categoria para cadastro de prato.",
            implementation = SetorListagemDto.class
    )
    private Setor setor;


    @ManyToOne
    @Schema(
            description = "Categoria que o prato pertence. É necessário informar uma categoria existente do setor anteriormente informado.",
            implementation = CategoriaListagemDto.class
    )
    private Categoria categoria;

    @ManyToOne
    @Schema(
            description = "Área de preparo que o prato pertencerá na hora da preparação na cozinha.",
            implementation = CategoriaListagemDto.class
    )
    private AreaPreparo areaPreparo;

    public AreaPreparo getAreaPreparo() {
        return areaPreparo;
    }

    public void setAreaPreparo(AreaPreparo areaPreparo) {
        this.areaPreparo = areaPreparo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
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

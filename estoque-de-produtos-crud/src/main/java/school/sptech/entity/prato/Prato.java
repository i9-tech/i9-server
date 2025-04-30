package school.sptech.entity.prato;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import school.sptech.controller.categoria.dto.CategoriaListagemDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.controller.setor.dto.SetorListagemDto;
import school.sptech.entity.categoria.Categoria;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.setor.Setor;

@Entity
@Table(name = "prato")
@Schema(
        name = "Prato",
        description = "Representa os pratos cadastrados em um setor da empresa, controlando valores de venda e categorias. Essencial para a gestão de estoque e venda.")
public class Prato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único do prato.",
            example = "1",
            type = "integer"
    )
    private int id;


    @Schema(
            description = "Nome do prato a ser cadastrado.",
            example = "Lanche Natural",
            type = "string"
    )
    private String nome;


    @Schema(
            description = "URL da imagem ligada ao prato cadastrado.",
            example = "https://img.freepik.com/fotos-gratis/sanduiche_1339-1108.jpg",
            type = "string"
    )
    private String imagem;

    @Schema(
            description = "Valor que prato será vendido. Deve ser maior que 0.",
            example = "18.00",
            type = "double"
    )
    private Double valorVenda;

    @Schema(
            description = "Descrição do prato a ser cadastrado. Campo opcional que facilita visualização do prato.",
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
            description = "Funcionário associado à criação do prato.",
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













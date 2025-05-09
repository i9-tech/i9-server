package school.sptech.entity.itemCarrinho;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.controller.prato.dto.RespostaPratoDto;
import school.sptech.controller.produto.dto.ProdutoListagemDto;
import school.sptech.controller.setor.dto.SetorListagemDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.prato.Prato;
import school.sptech.entity.produto.Produto;

@Entity
@Table(name = "item_carrinho")
@Schema(
        name = "Item Carinho",
        description = "Representa os produtos e pratos adicionados em um carrinho de compras de uma venda da empresa. Essencial para o fluxo de vendas.")
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único do item no carrinho.",
            example = "1",
            type = "integer"
    )
    private Integer id;

    @Schema(
            description = "Nome de identificação da venda atual. Serve para agrupar todos os itens de uma venda.",
            example = "venda1",
            type = "string"
    )
    private String venda;


    @ManyToOne
    @Schema(
            description = "Prato que foi adicionado ao carrinho. Quando um prato é adicionado, o produto precisa ser nulo.",
            implementation = RespostaPratoDto.class
    )
    private Prato prato;


    @ManyToOne
    @Schema(
            description = "Produto que foi adicionado ao carrinho. Quando um produto é adicionado, o prato e observação precisam ser nulos.",
            implementation = ProdutoListagemDto.class
    )
    private Produto produto;

    @Schema(
            description = "Valor que item é vendido. Ele é puxado automaticamente do prato ou produto selecionado.",
            example = "15.00",
            type = "double"
    )
    private Double valorUnitario;

    @Schema(
            description = "Observação de um prato no carrinho. Campo opcional que informa desejos do cliente sobre o prato.",
            example = "Sem tomate, por favor.",
            type = "string",
            nullable = true
    )
    private String observacao;


    @ManyToOne
    @Schema(
            description = "Funcionário associado à adição do prato ou produto ao carrinho.",
            implementation = FuncionarioResponseDto.class
    )
    private Funcionario funcionario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVenda() {
        return venda;
    }

    public void setVenda(String venda) {
        this.venda = venda;
    }

    public Prato getPrato() {
        return prato;
    }

    public void setPrato(Prato prato) {
        this.prato = prato;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}

package school.sptech.controller.itemCarrinho.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.controller.produto.dto.ProdutoListagemDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.produto.Produto;

@Schema(
        name = "AdicionarProdutoItemCarrinhoDto",
        description = "DTO para transferência de dados do produto para adição no carrinho.")
public class AdicionarProdutoItemCarrinhoDto {

    @Schema(
            description = "Nome de identificação da venda atual. Serve para agrupar todos os itens de uma venda.",
            example = "venda1",
            type = "string"
    )
    private String venda;


    @ManyToOne
    @Schema(
            description = "Produto que foi adicionado ao carrinho. Quando um produto é adicionado, o prato e observação precisam ser nulos.",
            implementation = ProdutoListagemDto.class
    )
    private Produto produto;

    @Schema(
            description = "Valor que produto é vendido. Ele é puxado automaticamente do produto selecionado.",
            example = "8.00",
            type = "double"
    )
    private Double valorUnitario;


    @ManyToOne
    @Schema(
            description = "Funcionário associado à adição do produto ao carrinho.",
            implementation = FuncionarioResponseDto.class
    )
    private Funcionario funcionario;

    public String getVenda() {
        return venda;
    }

    public void setVenda(String venda) {
        this.venda = venda;
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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}

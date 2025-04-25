package school.sptech.controller.itemCarrinho.dto;

import jakarta.persistence.ManyToOne;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.produto.Produto;

public class AdicionarProdutoItemCarrinhoDto {
    private String venda;
    @ManyToOne
    private Produto produto;
    private Double valorUnitario;
    @ManyToOne
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

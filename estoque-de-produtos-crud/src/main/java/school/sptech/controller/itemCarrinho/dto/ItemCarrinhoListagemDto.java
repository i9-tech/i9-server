package school.sptech.controller.itemCarrinho.dto;

import jakarta.persistence.ManyToOne;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.prato.Prato;
import school.sptech.entity.produto.Produto;

public class ItemCarrinhoListagemDto {

    private Integer id;
    @ManyToOne
    private Prato prato;
    @ManyToOne
    private Produto produto;
    private Double valorUnitario;
    private String observacao;
    @ManyToOne
    private Funcionario funcionario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

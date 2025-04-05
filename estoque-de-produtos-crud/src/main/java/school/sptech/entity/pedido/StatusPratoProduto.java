package school.sptech.entity.pedido;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "status_prato_produto")
public class StatusPratoProduto {
    private Integer id;
    private Boolean statusProduto;
    private Boolean statusPedido;
    private String cozinheiro;
    private int fkPedidoPratoProduto;

    public int getFkPedidoPratoProduto() {
        return fkPedidoPratoProduto;
    }

    public void setFkPedidoPratoProduto(int fkPedidoPratoProduto) {
        this.fkPedidoPratoProduto = fkPedidoPratoProduto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getStatusProduto() {
        return statusProduto;
    }

    public void setStatusProduto(Boolean statusProduto) {
        this.statusProduto = statusProduto;
    }

    public Boolean getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(Boolean statusPedido) {
        this.statusPedido = statusPedido;
    }

    public String getCozinheiro() {
        return cozinheiro;
    }

    public void setCozinheiro(String cozinheiro) {
        this.cozinheiro = cozinheiro;
    }
}

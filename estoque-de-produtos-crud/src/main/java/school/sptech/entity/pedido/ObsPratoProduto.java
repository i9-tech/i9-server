package school.sptech.entity.pedido;

import jakarta.persistence.*;

@Entity
@Table(name = "obs_prato_produto")
public class ObsPratoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    private int fkPedidoPratoProduto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getFkPedidoPratoProduto() {
        return fkPedidoPratoProduto;
    }

    public void setFkPedidoPratoProduto(int fkPedidoPratoProduto) {
        this.fkPedidoPratoProduto = fkPedidoPratoProduto;
    }
}

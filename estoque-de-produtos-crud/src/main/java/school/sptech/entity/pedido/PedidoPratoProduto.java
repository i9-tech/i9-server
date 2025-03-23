package school.sptech.entity.pedido;

import jakarta.persistence.*;

@Entity
@Table(name = "pedido_prato_produto")
public class PedidoPratoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer codigoComanda;
    private String nomeCliente;
    private Integer mesa;
    private String pagamento;
    private Double total;
    private int fkFuncionario;

    public int getFkFuncionario() {
        return fkFuncionario;
    }

    public void setFkFuncionario(int fkFuncionario) {
        this.fkFuncionario = fkFuncionario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigoComanda() {
        return codigoComanda;
    }

    public void setCodigoComanda(Integer codigoComanda) {
        this.codigoComanda = codigoComanda;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}

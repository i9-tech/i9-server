package school.sptech.entity.prato;

<<<<<<< HEAD
import aj.org.objectweb.asm.commons.Remapper;
=======
>>>>>>> 68f745151a904fb362fb0fc9affa540a7a4bd68a
import jakarta.persistence.*;

@Entity
@Table(name = "prato")
public class Prato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private Double valorVenda;
    private String descricao;
    private Boolean disponivel;
    private int fkFuncionario;
    private int fkPedidoPratoProduto;

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

    public int getFkFuncionario() {
        return fkFuncionario;
    }

    public void setFkFuncionario(int fkFuncionario) {
        this.fkFuncionario = fkFuncionario;
    }

    public int getFkPedidoPratoProduto() {
        return fkPedidoPratoProduto;
    }

    public void setFkPedidoPratoProduto(int fkPedidoPratoProduto) {
        this.fkPedidoPratoProduto = fkPedidoPratoProduto;
    }
<<<<<<< HEAD

=======
>>>>>>> 68f745151a904fb362fb0fc9affa540a7a4bd68a
}

package school.sptech.entity.venda;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.itemCarrinho.ItemCarrinho;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "A data da venda é obrigatória")
    @PastOrPresent(message = "A data da venda não pode ser no futuro")
    private LocalDate dataVenda;

    @NotNull(message = "O valor total é obrigatório")
    @PositiveOrZero(message = "O valor total deve ser positivo ou zero")
    private Double valorTotal;

    @NotBlank(message = "A identificação da mesa é obrigatória")
    @Size(max = 10, message = "A mesa deve ter no máximo 10 caracteres")
    private String mesa;

    @NotNull(message = "O funcionário responsável é obrigatório")
    @ManyToOne
    @JoinColumn(name = "fk_funcionario")
    private Funcionario funcionario;

    @OneToMany
    @NotEmpty(message = "A venda deve conter pelo menos um item")
    private List<ItemCarrinho> itensCarrinho;

    @NotNull(message = "É necessário informar se a venda foi concluída")
    private Boolean vendaConcluida;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<ItemCarrinho> getItensCarrinho() {
        return itensCarrinho;
    }

    public void setItensCarrinho(List<ItemCarrinho> itensCarrinho) {
        this.itensCarrinho = itensCarrinho;
    }

    public Boolean getVendaConcluida() {
        return vendaConcluida;
    }

    public void setVendaConcluida(Boolean vendaConcluida) {
        this.vendaConcluida = vendaConcluida;
    }
}

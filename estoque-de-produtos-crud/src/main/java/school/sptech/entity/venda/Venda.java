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

    @Size(max = 10, message = "A mesa deve ter no máximo 10 caracteres")
    private String mesa;

    private String cliente;

    private String formaPagamento;

    @NotNull(message = "O funcionário responsável é obrigatório")
    @ManyToOne
    @JoinColumn(name = "fk_funcionario")
    private Funcionario funcionario;

    @OneToMany
    @NotEmpty(message = "A venda deve conter pelo menos um item")
    private List<ItemCarrinho> itensCarrinho;

    @NotNull(message = "É necessário informar se a venda foi concluída")
    private Boolean vendaConcluida;

    private Double lucroDiario;

    private Double lucroDiarioOntem;

    private Integer vendasDiaria;

    private Integer vendasDiariaOntem;

    private Double lucroLiquidoDiario;

    private Double totalMercadoriaDiario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull(message = "A data da venda é obrigatória") @PastOrPresent(message = "A data da venda não pode ser no futuro") LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(@NotNull(message = "A data da venda é obrigatória") @PastOrPresent(message = "A data da venda não pode ser no futuro") LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public @NotNull(message = "O valor total é obrigatório") @PositiveOrZero(message = "O valor total deve ser positivo ou zero") Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(@NotNull(message = "O valor total é obrigatório") @PositiveOrZero(message = "O valor total deve ser positivo ou zero") Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public @Size(max = 10, message = "A mesa deve ter no máximo 10 caracteres") String getMesa() {
        return mesa;
    }

    public void setMesa(@Size(max = 10, message = "A mesa deve ter no máximo 10 caracteres") String mesa) {
        this.mesa = mesa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public @NotNull(message = "O funcionário responsável é obrigatório") Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(@NotNull(message = "O funcionário responsável é obrigatório") Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public @NotEmpty(message = "A venda deve conter pelo menos um item") List<ItemCarrinho> getItensCarrinho() {
        return itensCarrinho;
    }

    public void setItensCarrinho(@NotEmpty(message = "A venda deve conter pelo menos um item") List<ItemCarrinho> itensCarrinho) {
        this.itensCarrinho = itensCarrinho;
    }

    public @NotNull(message = "É necessário informar se a venda foi concluída") Boolean getVendaConcluida() {
        return vendaConcluida;
    }

    public void setVendaConcluida(@NotNull(message = "É necessário informar se a venda foi concluída") Boolean vendaConcluida) {
        this.vendaConcluida = vendaConcluida;
    }

    public Double getLucroDiario() {
        return lucroDiario;
    }

    public void setLucroDiario(Double lucroDiario) {
        this.lucroDiario = lucroDiario;
    }

    public Double getLucroDiarioOntem() {
        return lucroDiarioOntem;
    }

    public void setLucroDiarioOntem(Double lucroDiarioOntem) {
        this.lucroDiarioOntem = lucroDiarioOntem;
    }

    public Integer getVendasDiaria() {
        return vendasDiaria;
    }

    public void setVendasDiaria(Integer vendasDiaria) {
        this.vendasDiaria = vendasDiaria;
    }

    public Integer getVendasDiariaOntem() {
        return vendasDiariaOntem;
    }

    public void setVendasDiariaOntem(Integer vendasDiariaOntem) {
        this.vendasDiariaOntem = vendasDiariaOntem;
    }

    public Double getLucroLiquidoDiario() {
        return lucroLiquidoDiario;
    }

    public void setLucroLiquidoDiario(Double lucroLiquidoDiario) {
        this.lucroLiquidoDiario = lucroLiquidoDiario;
    }

    public Double getTotalMercadoriaDiario() {
        return totalMercadoriaDiario;
    }

    public void setTotalMercadoriaDiario(Double totalMercadoriaDiario) {
        this.totalMercadoriaDiario = totalMercadoriaDiario;
    }
}

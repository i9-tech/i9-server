package school.sptech.controller.venda.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.entity.itemCarrinho.ItemCarrinho;


import java.time.LocalDate;
import java.util.List;

public class VendaResponseDto {

    @Schema(description = "ID da venda", example = "1")
    private Integer id;

    @Schema(description = "Data da venda", example = "2025-04-18")
    private LocalDate dataVenda;

    @Schema(description = "Valor total da venda", example = "150.75")
    private Double valorTotal;

    @Schema(description = "Identificação da mesa", example = "A05")
    private String mesa;

    @Schema(description = "Nome do funcionário responsável")
    private String funcionarioNome;

    @Schema(description = "Lista de itens do carrinho")
    private List<ItemCarrinho> itensCarrinho;

    @Schema(description = "Indica se a venda foi concluída", example = "true")
    private Boolean vendaConcluida;

    public VendaResponseDto(Integer id, LocalDate dataVenda, Double valorTotal, String mesa, String funcionarioNome, List<ItemCarrinho> itensCarrinho, Boolean vendaConcluida) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.mesa = mesa;
        this.funcionarioNome = funcionarioNome;
        this.itensCarrinho = itensCarrinho;
        this.vendaConcluida = vendaConcluida;
    }

    public VendaResponseDto() {
    }

    public VendaResponseDto(Integer id, LocalDate dataVenda, Double valorTotal, String mesa, Boolean vendaConcluida, String nome) {
    }

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

    public String getFuncionarioNome() {
        return funcionarioNome;
    }

    public void setFuncionarioNome(String funcionarioNome) {
        this.funcionarioNome = funcionarioNome;
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

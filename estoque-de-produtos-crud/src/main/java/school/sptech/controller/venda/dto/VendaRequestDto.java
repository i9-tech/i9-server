package school.sptech.controller.venda.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;


import java.time.LocalDate;
import java.util.List;

public class VendaRequestDto {
    @Schema(description = "Identificação da mesa onde ocorreu a venda", example = "Mesa 7")
    @NotBlank(message = "A identificação da mesa é obrigatória")
    @Size(max = 10, message = "A mesa deve ter no máximo 10 caracteres")
    private String mesa;

    @Schema(description = "Data em que a venda foi realizada", example = "2024-04-18")
    @NotNull(message = "A data da venda é obrigatória")
    @PastOrPresent(message = "A data da venda não pode ser futura")
    private LocalDate dataVenda;

    @Schema(description = "Lista de itens que compõem a venda")
    @NotEmpty(message = "A venda deve conter pelo menos um item")
    private List<Integer> itens;


    @Schema(description = "ID do funcionário responsável pela venda", example = "1")
    @NotNull(message = "O ID do funcionário é obrigatório")
    private Integer funcionarioId;

    @Schema(description = "Valor total da venda", example = "150.75")
    @NotNull(message = "O valor total é obrigatório")
    @PositiveOrZero(message = "O valor total deve ser positivo ou zero")
    private Double valorTotal;

    @Schema(description = "Indica se a venda foi concluída", example = "true")
    @NotNull(message = "É necessário informar se a venda foi concluída")
    private Boolean vendaConcluida;

    public VendaRequestDto(String mesa, LocalDate dataVenda, List<Integer> itens, Integer funcionarioId, Double valorTotal, Boolean vendaConcluida) {
        this.mesa = mesa;
        this.dataVenda = dataVenda;
        this.itens = itens;
        this.funcionarioId = funcionarioId;
        this.valorTotal = valorTotal;
        this.vendaConcluida = vendaConcluida;
    }

    public VendaRequestDto() {

    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public List<Integer> getItens() {
        return itens;
    }

    public void setItens(List<Integer> itens) {
        this.itens = itens;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Boolean getVendaConcluida() {
        return vendaConcluida;
    }

    public void setVendaConcluida(Boolean vendaConcluida) {
        this.vendaConcluida = vendaConcluida;
    }
}

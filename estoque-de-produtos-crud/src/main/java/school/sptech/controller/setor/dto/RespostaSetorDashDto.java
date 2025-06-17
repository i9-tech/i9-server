package school.sptech.controller.setor.dto;

public class RespostaSetorDashDto {
    private String nomeSetor;
    private Long quantidadeVendida;
    private Double valorTotal;

    public RespostaSetorDashDto(String nomeSetor, Long quantidadeVendida, Double valorTotal) {
        this.nomeSetor = nomeSetor;
        this.quantidadeVendida = quantidadeVendida;
        this.valorTotal = valorTotal;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    public Long getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(Long quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}


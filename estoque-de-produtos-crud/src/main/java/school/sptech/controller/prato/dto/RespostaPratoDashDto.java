package school.sptech.controller.prato.dto;

public class RespostaPratoDashDto {
    private String nome;
    private Integer quantidadeVendida;
    private Double totalVendas;

    public RespostaPratoDashDto() {
    }

    public RespostaPratoDashDto(String nome, Integer quantidadeVendida, Double totalVendas) {
        this.nome = nome;
        this.quantidadeVendida = quantidadeVendida;
        this.totalVendas = totalVendas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(Integer quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public Double getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(Double totalVendas) {
        this.totalVendas = totalVendas;
    }
}

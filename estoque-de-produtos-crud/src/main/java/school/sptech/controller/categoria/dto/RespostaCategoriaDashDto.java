package school.sptech.controller.categoria.dto;

public class RespostaCategoriaDashDto {
    private String nomeCategoria;
    private Double totalVendido;
    private Long quantidadeVendida;

    public RespostaCategoriaDashDto() {
    }

    public RespostaCategoriaDashDto(String nomeCategoria, Double totalVendido, Long quantidadeVendida) {
        this.nomeCategoria = nomeCategoria;
        this.totalVendido = totalVendido;
        this.quantidadeVendida = quantidadeVendida;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Double getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(Double totalVendido) {
        this.totalVendido = totalVendido;
    }

    public Long getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(Long quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }
}

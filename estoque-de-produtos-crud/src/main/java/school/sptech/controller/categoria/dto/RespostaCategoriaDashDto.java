package school.sptech.controller.categoria.dto;

public class RespostaCategoriaDashDto {
    private String nomeCategoria;
    private Double totalVendido;

    public RespostaCategoriaDashDto() {
    }

    public RespostaCategoriaDashDto(String nomeCategoria, Double totalVendido) {
        this.nomeCategoria = nomeCategoria;
        this.totalVendido = totalVendido;
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
}

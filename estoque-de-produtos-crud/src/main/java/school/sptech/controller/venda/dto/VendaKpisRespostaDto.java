package school.sptech.controller.venda.dto;

public class VendaKpisRespostaDto {

    private Double lucroDiario;
    private Double lucroDiarioOntem;
    private Integer vendasDiaria;
    private Integer vendasDiariaOntem;
    private Double lucroLiquidoDiario;
    private Double totalMercadoriaDiario;

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

    public VendaKpisRespostaDto() {}

    public VendaKpisRespostaDto(Double lucroDiario, Double lucroDiarioOntem, Integer vendasDiaria, Integer vendasDiariaOntem, Double lucroLiquidoDiario, Double totalMercadoriaDiario) {
        this.lucroDiario = lucroDiario;
        this.lucroDiarioOntem = lucroDiarioOntem;
        this.vendasDiaria = vendasDiaria;
        this.vendasDiariaOntem = vendasDiariaOntem;
        this.lucroLiquidoDiario = lucroLiquidoDiario;
        this.totalMercadoriaDiario = totalMercadoriaDiario;
    }
}

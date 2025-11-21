package school.sptech.controller.plano.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public class PlanoTemplateCriacaoDto {
    @NotBlank(message = "tipo é obrigatório")
    private String tipo;

    @NotBlank(message = "descricao é obrigatória")
    private String descricao;

    @NotNull(message = "precoMensal é obrigatório")
    @Min(value = 0, message = "precoMensal deve ser >= 0")
    private BigDecimal precoMensal;

    private BigDecimal precoMensalComDescontoAnual;
    private BigDecimal precoAnual;

    @NotNull(message = "qtdUsuarios é obrigatória")
    @Min(value = 0, message = "qtdUsuarios deve ser >= 0")
    private Integer qtdUsuarios;

    @NotNull(message = "qtdSuperUsuarios é obrigatória")
    @Min(value = 0, message = "qtdSuperUsuarios deve ser >= 0")
    private Integer qtdSuperUsuarios;

    private Boolean acessoRelatorioWhatsApp = false;
    private Boolean acessoDashboard = false;


    public PlanoTemplateCriacaoDto() {
    }

    public PlanoTemplateCriacaoDto(String tipo, String descricao, BigDecimal precoMensal, BigDecimal precoMensalComDescontoAnual, BigDecimal precoAnual, Integer qtdUsuarios, Integer qtdSuperUsuarios, Boolean acessoRelatorioWhatsApp, Boolean acessoDashboard) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.precoMensal = precoMensal;
        this.precoMensalComDescontoAnual = precoMensalComDescontoAnual;
        this.precoAnual = precoAnual;
        this.qtdUsuarios = qtdUsuarios;
        this.qtdSuperUsuarios = qtdSuperUsuarios;
        this.acessoRelatorioWhatsApp = acessoRelatorioWhatsApp;
        this.acessoDashboard = acessoDashboard;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPrecoMensal() { return precoMensal; }
    public void setPrecoMensal(BigDecimal precoMensal) { this.precoMensal = precoMensal; }

    public BigDecimal getPrecoMensalComDescontoAnual() { return precoMensalComDescontoAnual; }
    public void setPrecoMensalComDescontoAnual(BigDecimal precoMensalComDescontoAnual) { this.precoMensalComDescontoAnual = precoMensalComDescontoAnual; }

    public BigDecimal getPrecoAnual() { return precoAnual; }
    public void setPrecoAnual(BigDecimal precoAnual) { this.precoAnual = precoAnual; }

    public Integer getQtdUsuarios() { return qtdUsuarios; }
    public void setQtdUsuarios(Integer qtdUsuarios) { this.qtdUsuarios = qtdUsuarios; }

    public Integer getQtdSuperUsuarios() { return qtdSuperUsuarios; }
    public void setQtdSuperUsuarios(Integer qtdSuperUsuarios) { this.qtdSuperUsuarios = qtdSuperUsuarios; }

    public Boolean getAcessoRelatorioWhatsApp() { return acessoRelatorioWhatsApp; }
    public void setAcessoRelatorioWhatsApp(Boolean acessoRelatorioWhatsApp) { this.acessoRelatorioWhatsApp = acessoRelatorioWhatsApp; }

    public Boolean getAcessoDashboard() { return acessoDashboard; }
    public void setAcessoDashboard(Boolean acessoDashboard) { this.acessoDashboard = acessoDashboard; }
}


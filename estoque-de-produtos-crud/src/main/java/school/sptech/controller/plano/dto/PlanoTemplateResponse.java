package school.sptech.controller.plano.dto;
import java.math.BigDecimal;

public class PlanoTemplateResponse {
    private Integer id;
    private String tipo;
    private String descricao;
    private BigDecimal precoMensal;
    private BigDecimal precoMensalComDescontoAnual;
    private BigDecimal precoAnual;
    private Integer qtdUsuarios;
    private Integer qtdSuperUsuarios;
    private Boolean acessoRelatorioWhatsApp;
    private Boolean acessoDashboard;

    public PlanoTemplateResponse() {}

    public PlanoTemplateResponse(Integer id, String tipo, String descricao, BigDecimal precoMensal, BigDecimal precoMensalComDescontoAnual, BigDecimal precoAnual, Integer qtdUsuarios, Integer qtdSuperUsuarios, Boolean acessoRelatorioWhatsApp, Boolean acessoDashboard) {
        this.id = id;
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

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

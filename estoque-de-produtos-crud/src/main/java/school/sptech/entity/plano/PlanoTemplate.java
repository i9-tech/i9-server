package school.sptech.entity.plano;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class PlanoTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tipo;
    private String descricao;
    private BigDecimal precoMensal;
    private BigDecimal precoMensalComDescontoAnual;
    private BigDecimal precoAnual;
    private Integer qtdUsuarios;
    private Integer qtdSuperUsuarios;
    private boolean acessoRelatorioWhatsApp;
    private boolean acessoDashboard;

    public PlanoTemplate() {}

    public PlanoTemplate(Integer id, String tipo, String descricao, BigDecimal precoMensal, BigDecimal precoMensalComDescontoAnual, BigDecimal precoAnual, Integer qtdUsuarios, Integer qtdSuperUsuarios, boolean acessoRelatorioWhatsApp, boolean acessoDashboard) {
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

    public boolean isAcessoDashboard() {
        return acessoDashboard;
    }

    public void setAcessoDashboard(boolean acessoDashboard) {
        this.acessoDashboard = acessoDashboard;
    }

    public boolean isAcessoRelatorioWhatsApp() {
        return acessoRelatorioWhatsApp;
    }

    public void setAcessoRelatorioWhatsApp(boolean acessoRelatorioWhatsApp) {
        this.acessoRelatorioWhatsApp = acessoRelatorioWhatsApp;
    }

    public Integer getQtdSuperUsuarios() {
        return qtdSuperUsuarios;
    }

    public void setQtdSuperUsuarios(Integer qtdSuperUsuarios) {
        this.qtdSuperUsuarios = qtdSuperUsuarios;
    }

    public Integer getQtdUsuarios() {
        return qtdUsuarios;
    }

    public void setQtdUsuarios(Integer qtdUsuarios) {
        this.qtdUsuarios = qtdUsuarios;
    }

    public BigDecimal getPrecoAnual() {
        return precoAnual;
    }

    public void setPrecoAnual(BigDecimal precoAnual) {
        this.precoAnual = precoAnual;
    }

    public BigDecimal getPrecoMensalComDescontoAnual() {
        return precoMensalComDescontoAnual;
    }

    public void setPrecoMensalComDescontoAnual(BigDecimal precoMensalComDescontoAnual) {
        this.precoMensalComDescontoAnual = precoMensalComDescontoAnual;
    }

    public BigDecimal getPrecoMensal() {
        return precoMensal;
    }

    public void setPrecoMensal(BigDecimal precoMensal) {
        this.precoMensal = precoMensal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}

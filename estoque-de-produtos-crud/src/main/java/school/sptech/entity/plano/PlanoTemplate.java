package school.sptech.entity.plano;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class PlanoTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do template do plano", example = "1")
    private Integer id;

    @Schema(description = "Tipo do plano", example = "Essencial")
    private String tipo;

    @Schema(description = "Descrição do plano", example = "Para quem quer fazer a gestão completa do negócio em uma única plataforma")
    private String descricao;

    @Schema(description = "Preço mensal do plano", example = "99.90")
    private BigDecimal precoMensal;

    @Schema(description = "Preço mensal com desconto aplicado no pagamento anual", example = "89.90")
    private BigDecimal precoMensalComDescontoAnual;

    @Schema(description = "Preço anual do plano", example = "1078.80")
    private BigDecimal precoAnual;

    @Schema(description = "Quantidade de usuários permitidos", example = "10")
    private Integer qtdUsuarios;

    @Schema(description = "Quantidade de super usuários permitidos", example = "2")
    private Integer qtdSuperUsuarios;

    @Schema(description = "Indica se há acesso a relatórios via WhatsApp", example = "false")
    private boolean acessoRelatorioWhatsApp;

    @Schema(description = "Indica se há acesso ao dashboard analítico", example = "false")
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

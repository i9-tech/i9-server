package school.sptech.controller.plano.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(
        name = "PlanoTemplateAtualizarDTO",
        description = "DTO para atualização de informações de um template de plano.")
public class PlanoTemplateAtualizarDto {

    @Schema(description = "Tipo do plano", example = "Essencial")
    private String tipo;

    @Schema(description = "Descrição detalhada do plano", example = "Plano para gestão completa do negócio em uma única plataforma.")
    private String descricao;

    @Schema(description = "Preço mensal do plano", example = "99.00")
    private BigDecimal precoMensal;

    @Schema(description = "Preço mensal com desconto caso optado pelo pagamento anual", example = "89.00")
    private BigDecimal precoMensalComDescontoAnual;

    @Schema(description = "Preço total do plano no período anual", example = "999.00")
    private BigDecimal precoAnual;

    @Schema(description = "Quantidade de usuários permitidos", example = "10")
    private Integer qtdUsuarios;

    @Schema(description = "Quantidade de super usuários permitidos", example = "2")
    private Integer qtdSuperUsuarios;

    @Schema(description = "Indica se o envio de relatório via WhatsApp está disponível", example = "true")
    private Boolean acessoRelatorioWhatsApp;

    @Schema(description = "Indica se o dashboard analítico está disponível", example = "true")
    private Boolean acessoDashboard;

    public PlanoTemplateAtualizarDto() {}

    public PlanoTemplateAtualizarDto(String tipo, String descricao, BigDecimal precoMensal, BigDecimal precoMensalComDescontoAnual,
                                     BigDecimal precoAnual, Integer qtdUsuarios, Integer qtdSuperUsuarios,
                                     Boolean acessoRelatorioWhatsApp, Boolean acessoDashboard) {
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

package school.sptech.controller.plano.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.entity.plano.Periodo;

import java.time.LocalDate;

@Schema(
        name = "GerenciamentoPlanoResponse",
        description = "DTO para retorno das informações de um plano contratado por uma empresa.")
public class GerenciamentoPlanoResponse {

    @Schema(description = "ID do plano contratado", example = "1")
    private Integer id;

    @Schema(description = "Período do plano contratado (MENSAL ou ANUAL)", example = "MENSAL")
    private Periodo periodo;

    @Schema(description = "Data da adesão ao plano", example = "2025-11-21")
    private LocalDate dataAdesao;

    @Schema(description = "Data de início do plano", example = "2025-11-21")
    private LocalDate dataInicio;

    @Schema(description = "Data de término do plano", example = "2026-11-21")
    private LocalDate dataFim;

    @Schema(description = "Indica se o plano está em período de teste grátis", example = "true")
    private boolean testeGratis;

    @Schema(description = "Quantidade de dias de teste grátis restantes", example = "7")
    private Integer diasTeste;

    @Schema(description = "Indica se o plano está ativo", example = "true")
    private boolean ativo;

    @Schema(description = "Valor cobrado pelo plano", example = "249.99")
    private Double valorCobrado;

    @Schema(description = "ID da empresa contratante", example = "1")
    private Integer empresaId;

    @Schema(description = "Nome da empresa contratante", example = "Empresa XYZ")
    private String empresaNome;

    @Schema(description = "Informações do template do plano contratado")
    private PlanoTemplateResponse planoTemplate;

    public GerenciamentoPlanoResponse() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Periodo getPeriodo() { return periodo; }
    public void setPeriodo(Periodo periodo) { this.periodo = periodo; }

    public LocalDate getDataAdesao() { return dataAdesao; }
    public void setDataAdesao(LocalDate dataAdesao) { this.dataAdesao = dataAdesao; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public boolean isTesteGratis() { return testeGratis; }
    public void setTesteGratis(boolean testeGratis) { this.testeGratis = testeGratis; }

    public Integer getDiasTeste() { return diasTeste; }
    public void setDiasTeste(Integer diasTeste) { this.diasTeste = diasTeste; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public Double getValorCobrado() { return valorCobrado; }
    public void setValorCobrado(Double valorCobrado) { this.valorCobrado = valorCobrado; }

    public Integer getEmpresaId() { return empresaId; }
    public void setEmpresaId(Integer empresaId) { this.empresaId = empresaId; }

    public String getEmpresaNome() { return empresaNome; }
    public void setEmpresaNome(String empresaNome) { this.empresaNome = empresaNome; }

    public PlanoTemplateResponse getPlanoTemplate() { return planoTemplate; }
    public void setPlanoTemplate(PlanoTemplateResponse planoTemplate) { this.planoTemplate = planoTemplate; }
}

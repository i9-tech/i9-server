package school.sptech.controller.plano.dto;

import school.sptech.entity.plano.Periodo;

import java.time.LocalDate;
import java.math.BigDecimal;

public class GerenciamentoPlanoResponse {
    private Integer id;
    private Periodo periodo;
    private LocalDate dataAdesao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean testeGratis;
    private Integer diasTeste;
    private boolean ativo;
    private Double valorCobrado;
    private Integer empresaId;
    private String empresaNome;

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

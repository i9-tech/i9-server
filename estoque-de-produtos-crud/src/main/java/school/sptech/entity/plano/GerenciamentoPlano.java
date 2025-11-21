package school.sptech.entity.plano;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import school.sptech.entity.empresa.Empresa;

import java.time.LocalDate;

@Entity
public class GerenciamentoPlano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do gerenciamento do plano", example = "1")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Período do plano", example = "MENSAL")
    private Periodo periodo;

    @Schema(description = "Data de adesão ao plano", example = "2025-11-21")
    private LocalDate dataAdesao;

    @Schema(description = "Data de início do plano", example = "2025-11-21")
    private LocalDate dataInicio;

    @Schema(description = "Data de término do plano", example = "2026-11-21")
    private LocalDate dataFim;

    @Schema(description = "Indica se o plano possui teste grátis", example = "true")
    private boolean testeGratis;

    @Schema(description = "Quantidade de dias de teste grátis", example = "7")
    private Integer diasTeste;

    @Schema(description = "Indica se o plano está ativo", example = "true")
    private boolean ativo;

    @Schema(description = "Valor cobrado pelo plano", example = "99.90")
    private Double valorCobrado;

    @OneToOne
    @JoinColumn(name = "empresa_id")
    @Schema(description = "Empresa associada ao plano")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "plano_template_id")
    @Schema(description = "Template do plano associado")
    private PlanoTemplate planoTemplate;
    public GerenciamentoPlano() {
    }

    public GerenciamentoPlano(Integer id, Periodo periodo, LocalDate dataAdesao, LocalDate dataInicio, LocalDate dataFim, boolean testeGratis, Integer diasTeste, boolean ativo, Double valorCobrado, Empresa empresa, PlanoTemplate planoTemplate) {
        this.id = id;
        this.periodo = periodo;
        this.dataAdesao = dataAdesao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.testeGratis = testeGratis;
        this.diasTeste = diasTeste;
        this.ativo = ativo;
        this.valorCobrado = valorCobrado;
        this.empresa = empresa;
        this.planoTemplate = planoTemplate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public LocalDate getDataAdesao() {
        return dataAdesao;
    }

    public void setDataAdesao(LocalDate dataAdesao) {
        this.dataAdesao = dataAdesao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isTesteGratis() {
        return testeGratis;
    }

    public void setTesteGratis(boolean testeGratis) {
        this.testeGratis = testeGratis;
    }

    public Integer getDiasTeste() {
        return diasTeste;
    }

    public void setDiasTeste(Integer diasTeste) {
        this.diasTeste = diasTeste;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Double getValorCobrado() {
        return valorCobrado;
    }

    public void setValorCobrado(Double valorCobrado) {
        this.valorCobrado = valorCobrado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public PlanoTemplate getPlanoTemplate() {
        return planoTemplate;
    }

    public void setPlanoTemplate(PlanoTemplate planoTemplate) {
        this.planoTemplate = planoTemplate;
    }
}

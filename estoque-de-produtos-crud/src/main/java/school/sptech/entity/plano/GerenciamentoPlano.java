package school.sptech.entity.plano;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import school.sptech.entity.empresa.Empresa;

import java.time.LocalDate;

@Entity
public class GerenciamentoPlano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Periodo periodo;
    private LocalDate dataAdesao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean testeGratis;
    private Integer diasTeste;
    private boolean ativo;
    private Double valorCobrado;

    @OneToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    @ManyToOne
    @JoinColumn(name = "plano_template_id")
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

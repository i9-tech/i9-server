package school.sptech.controller.produto.dto;

import school.sptech.entity.funcionario.Funcionario;

import java.time.LocalDate;

public class ProdutoListagemDto {
    private Integer id;
    private Integer codigo;
    private String nome;
    private Integer quantidade;
    private LocalDate dataVencimento;
    private Double valorCompra;
    private Double valorUnitario;
    private Integer quantidadeMin;
    private Integer quantidadeMax;
    private String descricao;
    private String categoria;
    private String setor;
    private LocalDate dataRegistro;
    private Funcionario funcionario;


    public ProdutoListagemDto(Integer id, String nome, int codigo, int quantidade, LocalDate dataVencimento, double valorCompra, double valorUnitario, int quantidadeMin, int quantidadeMax, String descricao, String categoria, String setor, LocalDate dataRegistro, Funcionario funcionario) {
        this.id = id; // Atribuindo o ID
        this.nome = nome;
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.dataVencimento = dataVencimento;
        this.valorCompra = valorCompra;
        this.valorUnitario = valorUnitario;
        this.quantidadeMin = quantidadeMin;
        this.quantidadeMax = quantidadeMax;
        this.descricao = descricao;
        this.categoria = categoria;
        this.setor = setor;
        this.dataRegistro = dataRegistro;
        this.funcionario = funcionario;
    }


    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(Double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Integer getQuantidadeMin() {
        return quantidadeMin;
    }

    public void setQuantidadeMin(Integer quantidadeMin) {
        this.quantidadeMin = quantidadeMin;
    }

    public Integer getQuantidadeMax() {
        return quantidadeMax;
    }

    public void setQuantidadeMax(Integer quantidadeMax) {
        this.quantidadeMax = quantidadeMax;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }


}

package school.sptech.controller.prato.dto;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import school.sptech.entity.funcionario.Funcionario;

public class CadastroPratoDto {

    @NotBlank
    private String nome;
    @DecimalMin("1.0") @Positive
    private Double valorVenda;
    private String descricao;
    private String imagem;

    @ManyToOne
    private Funcionario funcionario;
    //@ManyToOne
    private String setor;
    //@ManyToOne
    private String categoria;

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }

    public @DecimalMin("1.0") @Positive Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(@DecimalMin("1.0") @Positive Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}

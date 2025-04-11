package school.sptech.controller.prato.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CadastroPratoDto {

    @NotBlank
    private String nome;
    @DecimalMin("1.0") @Positive
    private Double valorVenda;
    private String descricao;
    private int fkFuncionario;

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

    public int getFkFuncionario() {
        return fkFuncionario;
    }

    public void setFkFuncionario(int fkFuncionario) {
        this.fkFuncionario = fkFuncionario;
    }
}

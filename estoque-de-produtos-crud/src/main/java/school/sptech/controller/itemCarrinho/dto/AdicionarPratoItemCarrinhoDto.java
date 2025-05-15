package school.sptech.controller.itemCarrinho.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.controller.prato.dto.RespostaPratoDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.prato.Prato;
import school.sptech.entity.produto.Produto;

@Schema(
        name = "AdicionarPratoItemCarrinhoDto",
        description = "DTO para transferência de dados do prato para adição no carrinho.")
public class AdicionarPratoItemCarrinhoDto {

    @Schema(
            description = "Nome de identificação da venda atual. Serve para agrupar todos os itens de uma venda.",
            example = "venda1",
            type = "string"
    )
    private String venda;


    @ManyToOne
    @Schema(
            description = "Prato que foi adicionado ao carrinho. Quando um prato é adicionado, o produto precisa ser nulo.",
            implementation = RespostaPratoDto.class
    )
    private Prato prato;

    @Schema(
            description = "Observação de um prato no carrinho. Campo opcional que informa desejos do cliente sobre o prato.",
            example = "Sem tomate, por favor.",
            type = "string",
            nullable = true
    )
    private String observacao;


    @Schema(
            description = "Valor que prato é vendido. Ele é puxado automaticamente do prato selecionado.",
            example = "15.00",
            type = "double"
    )
    private Double valorUnitario;


    @ManyToOne
    @Schema(
            description = "Funcionário associado à adição do prato ao carrinho.",
            implementation = FuncionarioResponseDto.class
    )
    private Funcionario funcionario;

    public String getVenda() {
        return venda;
    }

    public void setVenda(String venda) {
        this.venda = venda;
    }

    public Prato getPrato() {
        return prato;
    }

    public void setPrato(Prato prato) {
        this.prato = prato;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}

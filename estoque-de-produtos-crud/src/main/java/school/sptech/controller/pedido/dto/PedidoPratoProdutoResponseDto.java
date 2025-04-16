package school.sptech.controller.pedido.dto;

import lombok.Getter;
import lombok.Setter;
import school.sptech.entity.pedido.PedidoPratoProduto;

@Setter
@Getter
public class PedidoPratoProdutoResponseDto {

    private Integer id;
    private Integer codigoComanda;
    private String nomeCliente;
    private Integer mesa;
    private String pagamento;
    private Double total;
    private Integer fkFuncionario;


    public PedidoPratoProdutoResponseDto(PedidoPratoProduto pedido) {
        this.id = pedido.getId();
        this.codigoComanda = pedido.getCodigoComanda();
        this.nomeCliente = pedido.getNomeCliente();
        this.mesa = pedido.getMesa();
        this.pagamento = pedido.getPagamento();
        this.total = pedido.getTotal();
        this.fkFuncionario = pedido.getFkFuncionario();
    }


}

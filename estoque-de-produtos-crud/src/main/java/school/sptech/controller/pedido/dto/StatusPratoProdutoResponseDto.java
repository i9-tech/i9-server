package school.sptech.controller.pedido.dto;

import lombok.Getter;
import lombok.Setter;
import school.sptech.entity.pedido.StatusPratoProduto;

@Setter
@Getter
public class StatusPratoProdutoResponseDto {

    private Integer id;
    private Boolean statusProduto;
    private Boolean statusPedido;
    private String cozinheiro;
    private Integer fkPedidoPratoProduto;

    public StatusPratoProdutoResponseDto(StatusPratoProduto status) {
        this.id = status.getId();
        this.statusProduto = status.getStatusProduto();
        this.statusPedido = status.getStatusPedido();
        this.cozinheiro = status.getCozinheiro();
        this.fkPedidoPratoProduto = status.getFkPedidoPratoProduto();
    }

}

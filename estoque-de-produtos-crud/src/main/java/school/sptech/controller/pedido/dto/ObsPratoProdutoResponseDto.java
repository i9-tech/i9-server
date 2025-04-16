package school.sptech.controller.pedido.dto;

import lombok.Getter;
import lombok.Setter;
import school.sptech.entity.pedido.ObsPratoProduto;

@Getter
@Setter
public class ObsPratoProdutoResponseDto {

    private Integer id;
    private String descricao;
    private int fkPedidoPratoProduto;

    public ObsPratoProdutoResponseDto(ObsPratoProduto obs) {
        this.id = obs.getId();
        this.descricao = obs.getDescricao();
        this.fkPedidoPratoProduto = obs.getFkPedidoPratoProduto();
    }

}

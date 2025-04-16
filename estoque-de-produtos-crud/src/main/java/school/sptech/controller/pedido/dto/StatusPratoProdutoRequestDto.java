package school.sptech.controller.pedido.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusPratoProdutoRequestDto {

    @NotNull(message = "O status do produto é obrigatório")
    private Boolean statusProduto;

    @NotNull(message = "O status do pedido é obrigatório")
    private Boolean statusPedido;

    @NotBlank(message = "O nome do cozinheiro é obrigatório")
    private String cozinheiro;

    @NotNull(message = "O ID do pedido é obrigatório")
    @Positive(message = "O ID do pedido deve ser maior que zero")
    private Integer fkPedidoPratoProduto;

}

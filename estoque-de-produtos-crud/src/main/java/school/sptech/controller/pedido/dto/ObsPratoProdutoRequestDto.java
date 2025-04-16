package school.sptech.controller.pedido.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ObsPratoProdutoRequestDto {

    @NotBlank(message = "A descrição não pode estar vazia")
    private String descricao;

    @NotNull(message = "O ID do pedido prato produto é obrigatório")
    private Integer fkPedidoPratoProduto;

}

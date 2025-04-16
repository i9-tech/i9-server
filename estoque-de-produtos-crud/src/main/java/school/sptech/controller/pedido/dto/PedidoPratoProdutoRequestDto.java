package school.sptech.controller.pedido.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoPratoProdutoRequestDto {

    @NotNull(message = "O código da comanda não pode ser nulo")
    @Positive(message = "O código da comanda deve ser maior que zero")
    private Integer codigoComanda;

    @NotBlank(message = "O nome do cliente não pode estar em branco")
    private String nomeCliente;

    @NotNull(message = "O número da mesa é obrigatório")
    @Min(value = 1, message = "A mesa deve ser maior ou igual a 1")
    private Integer mesa;

    @NotBlank(message = "O tipo de pagamento é obrigatório")
    private String pagamento;

    @NotNull(message = "O total é obrigatório")
    @PositiveOrZero(message = "O total deve ser maior ou igual a zero")
    private Double total;

    @NotNull(message = "O código do funcionário é obrigatório")
    @Positive(message = "O código do funcionário deve ser maior que zero")
    private Integer fkFuncionario;

}

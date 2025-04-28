package school.sptech.controller.itemCarrinho;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.itemCarrinho.dto.AdicionarPratoItemCarrinhoDto;
import school.sptech.controller.itemCarrinho.dto.AdicionarProdutoItemCarrinhoDto;
import school.sptech.controller.itemCarrinho.dto.ItemCarrinhoListagemDto;
import school.sptech.controller.itemCarrinho.dto.ItemCarrinhoMapper;
import school.sptech.entity.prato.Prato;
import school.sptech.service.itemCarrinho.ItemCarrinhoService;

import java.util.List;

@RestController
@RequestMapping("/itens-carrinho")
@Tag(name = "Itens Carrinho", description = "Operações relacionadas aos itens em um carrinho de venda.")
public class ItemCarrinhoController {

    private final ItemCarrinhoService itemCarrinhoService;

    public ItemCarrinhoController(ItemCarrinhoService itemCarrinhoService) {
        this.itemCarrinhoService = itemCarrinhoService;
    }

    @PostMapping("/prato/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Adicionar prato ao carrinho", description = "Adiciona um prato ao carrinho de um funcionário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prato adicionado ao carrinho com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "id": 11,
                                        "valorUnitario": 42.0,
                                        "observacao": "Sem cebola, por favor",
                                        "prato": {
                                            "id": 2,
                                            "nome": "Nome do Prato",
                                            "descricao": "Descrição do Prato",
                                            "preco": 0.0,
                                            "disponivel": true,
                                            "categoria": {
                                                "id": 2,
                                                "nome": "Nome da Categoria"
                                            },
                                            "setor": {
                                                "id": 2,
                                                "nome": "Nome do Setor"
                                            }
                                        },
                                        "produto": null
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
                                    }
                                    """))
            ),
            @ApiResponse(responseCode = "404", description = "Prato ou funcionário não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "mensagem": "Prato ou funcionário não encontrado."
                                    }
                                    """))
            )
    })
    public ResponseEntity<ItemCarrinhoListagemDto> adicionarPrato(
            @Parameter(description = "Dados para adicionar o prato ao carrinho.", required = true)
            @RequestBody AdicionarPratoItemCarrinhoDto request,
            @Parameter(description = "ID do funcionário que está adicionando o prato ao carrinho.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(ItemCarrinhoMapper.
                        toPratoResponseDto(
                                itemCarrinhoService.adicionarPrato(ItemCarrinhoMapper.toEntity(request), idFuncionario)));
    }

    @PostMapping("/produto/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Adicionar produto ao carrinho", description = "Adiciona um produto ao carrinho de um funcionário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto adicionado ao carrinho com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "id": 12,
                                        "valorUnitario": 22.0,
                                        "observacao": null,
                                        "prato": null,
                                        "produto": {
                                            "id": 1,
                                            "codigo": 0,
                                            "nome": "Nome do Produto",
                                            "quantidade": 0,
                                            "valorCompra": 0.0,
                                            "valorUnitario": 0.0,
                                            "quantidadeMin": 0,
                                            "quantidadeMax": 0,
                                            "descricao": "Descrição do Produto",
                                            "dataRegistro": "2025-04-28",
                                            "setor": {
                                                "id": 1,
                                                "nome": "Nome do Setor"
                                            },
                                            "categoria": {
                                                "id": 1,
                                                "nome": "Nome da Categoria"
                                            },
                                            "funcionario": {
                                                "id": 1
                                            }
                                        }
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
                                    }
                                    """))
            ),
            @ApiResponse(responseCode = "404", description = "Produto ou funcionário não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "mensagem": "Produto ou funcionário não encontrado."
                                    }
                                    """))
            )
    })
    public ResponseEntity<ItemCarrinhoListagemDto> adicionarProduto(
            @Parameter(description = "Dados para adicionar o produto ao carrinho.", required = true)
            @RequestBody AdicionarProdutoItemCarrinhoDto request,
            @Parameter(description = "ID do funcionário que está adicionando o produto ao carrinho.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(ItemCarrinhoMapper.
                        toProdutoResponseDto(
                                itemCarrinhoService.adicionarProduto(
                                        ItemCarrinhoMapper.toEntity(request), idFuncionario)));
    }

    @GetMapping("/{venda}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar itens do carrinho", description = "Lista todos os itens presentes em um carrinho de venda específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pratos listados com sucesso.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    example = """
                                            [
                                               {
                                                 "id": 11,
                                                 "valorUnitario": 42.0,
                                                 "observacao": "Sem cebola, por favor",
                                                 "prato": {
                                                   "id": 2,
                                                   "nome": "Nome do Prato",
                                                   "descricao": "Descrição do Prato",
                                                   "preco": 0.0,
                                                   "disponivel": true,
                                                   "categoria": {
                                                     "id": 2,
                                                     "nome": "Nome da Categoria"
                                                   },
                                                   "setor": {
                                                     "id": 2,
                                                     "nome": "Nome do Setor"
                                                   }
                                                 },
                                                 "produto": null
                                               },
                                               {
                                                 "id": 12,
                                                 "valorUnitario": 22.0,
                                                 "observacao": null,
                                                 "prato": null,
                                                 "produto": {
                                                   "id": 1,
                                                   "codigo": 3456789,
                                                   "nome": "Arroz Branco",
                                                   "quantidade": 12,
                                                   "valorCompra": 6.0,
                                                   "valorUnitario": 12.0,
                                                   "quantidadeMin": 3,
                                                   "quantidadeMax": 20,
                                                   "descricao": null,
                                                   "dataRegistro": "2025-04-28",
                                                   "setor": {
                                                     "id": 1,
                                                   },
                                                   "categoria": {
                                                     "id": 1,
                                                   },
                                                   "funcionario": {
                                                     "id": 1,
                                                     "empresa": {
                                                        "id": 1
                                                   }
                                                   }
                                                 }
                                               }
                                             ]
                                    """)))),
            @ApiResponse(responseCode = "404", description = "Carrinho de venda não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "mensagem": "Carrinho de venda não encontrado."
                                    }
                                    """))
            )
    })
    public ResponseEntity<List<ItemCarrinhoListagemDto>> listarItens(
            @Parameter(description = "Identificador da venda (código do carrinho).", required = true)
            @PathVariable String venda,
            @Parameter(description = "ID do funcionário associado ao carrinho de venda.", required = true)
            @PathVariable Integer idFuncionario
    ){
        return ResponseEntity
                .ok(ItemCarrinhoMapper.
                        toResponseDtoList(
                                itemCarrinhoService.listarItens(venda, idFuncionario)));
    }

    @DeleteMapping("/{id}/{venda}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Remover item do carrinho", description = "Remove um item específico do carrinho de venda.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item removido do carrinho com sucesso.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item ou carrinho de venda não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "mensagem": "Item ou carrinho de venda não encontrado."
                                    }
                                    """))
            )
    })
    public ResponseEntity<Void> removerItem(
            @Parameter(description = "ID do item a ser removido.", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Identificador da venda (código do carrinho) do qual o item será removido.", required = true)
            @PathVariable String venda
    ) {
        itemCarrinhoService.removerItem(id, venda);
        return ResponseEntity.ok().build();
    }
}

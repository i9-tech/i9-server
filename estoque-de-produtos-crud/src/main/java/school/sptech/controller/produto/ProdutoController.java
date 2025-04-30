package school.sptech.controller.produto;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.produto.dto.ProdutoCadastroDto;
import school.sptech.controller.produto.dto.ProdutoEdicaoDto;
import school.sptech.controller.produto.dto.ProdutoListagemDto;
import school.sptech.entity.setor.Setor;
import school.sptech.service.produto.ProdutoService;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Operações relacionadas aos produtos.")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping("/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Cadastrar novo produto", description = "Cadastra um novo produto de uma determinada empresa na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
            }
            """))
            )

    })
    public ResponseEntity<ProdutoListagemDto> cadastrar(
            @Parameter(description = "Dados do produto para cadastro.", required = true)
            @Valid @RequestBody ProdutoCadastroDto produtoParaCadastrar,
            @Parameter(description = "ID do funcionário responsável pelo cadastro do produto.", required = true)
            @PathVariable Integer idFuncionario) {
        ProdutoListagemDto produtoCadastrado = service.cadastrarProduto(produtoParaCadastrar, idFuncionario);
        return ResponseEntity.status(201).body(produtoCadastrado);
    }

    @GetMapping("/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar produtos", description = "Lista todos os produtos de uma determinada empresa presentes na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    example = """
    [
      {
       "id": 1,
        "codigo": 1001,
        "nome": "Arroz Branco Tipo 1",
        "quantidade": 50,
        "valorCompra": 15.9,
        "valorUnitario": 22,
        "quantidadeMin": 80,
        "quantidadeMax": 100,
        "descricao": "Arroz tipo 1 de ótima qualidade",
        "dataRegistro": "2025-04-11",
        "setor": {
           "id": 1
           },
        "categoria": {
           "id": 1
           },
        "funcionario": {
           "id": 1,
           "empresa": {
              "id": 1
           }
        }
      },
      {
      "id": 2,
      "codigo": 1002,
      "nome": "Feijão Preto",
      "quantidade": 30,
      "valorCompra": 12.5,
      "valorUnitario": 18,
      "quantidadeMin": 40,
      "quantidadeMax": 60,
      "descricao": "Feijão preto de boa qualidade, ideal para pratos tradicionais.",
      "dataRegistro": "2025-04-12",
      "setor": {
        "id": 2
      },
      "categoria": {
        "id": 2
      },
      "funcionario": {
        "id": 1,
        "empresa": {
          "id": 1
        }
      }
    }
    ]""")))),
            @ApiResponse(responseCode = "404", description = "Produtos não encontrados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Não foi encontrado nenhum produto nessa empresa."
            }
            """))
            )
    })
    public ResponseEntity<List<ProdutoListagemDto>> listarProduto(
            @Parameter(description = "ID do funcionário para listagem de produtos.", required = true)
            @PathVariable Integer idFuncionario) {
        List<ProdutoListagemDto> responseDto = service.listarProdutoPorEmpresa(idFuncionario);
        return ResponseEntity.status(200).body(responseDto);
    }

    @PatchMapping("/{id}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar produto existente de determinada empresa", description = "Atualiza um produto existente de determinada empresa da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
{
      "id": 2,
      "codigo": 1002,
      "nome": "Feijão Tropeiro",
      "quantidade": 30,
      "valorCompra": 12.5,
      "valorUnitario": 18,
      "quantidadeMin": 40,
      "quantidadeMax": 60,
      "descricao": "Feijão tropeiro de boa qualidade, ideal para pratos tradicionais.",
      "dataRegistro": "2025-04-12",
      "setor": {
        "id": 2
      },
      "categoria": {
        "id": 2
      },
      "funcionario": {
        "id": 1,
        "empresa": {
          "id": 1
        }
      }
}"""))
            ),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
            }
            """))
            ),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "O produto não foi encontrado."
            }
            """))
            )
    })
    public ResponseEntity<ProdutoListagemDto> editarProduto(
            @Parameter(description = "ID do produto a ser atualizado.", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Dados do produto para atualização.", required = true)
            @Valid @RequestBody ProdutoEdicaoDto produtoParaEditar,
            @Parameter(description = "ID do funcionário responsável pela atualização do produto.", required = true)
            @PathVariable Integer idFuncionario) {
        ProdutoListagemDto responseDto = service.editarProduto(id, idFuncionario, produtoParaEditar);
        return ResponseEntity.status(200).body(responseDto);
    }

    @DeleteMapping("/{id}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Remover produto", description = "Remove um produto da base de dados usando o ID do produto e o ID do funcionário responsável.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Produto não encontrado."
            }
            """))
            )
    })
    public ResponseEntity<Void> removerProduto(
            @Parameter(description = "ID do produto para busca.", required = true)
            @PathVariable int id,
            @Parameter(description = "ID do funcionário que removerá o produto.", required = true)
            @PathVariable Integer idFuncionario) {
        service.removerPorId(id, idFuncionario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/valor-total-estoque/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Valor total do estoque", description = "Calcula o valor total de todos os produtos no estoque de uma empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valor total do estoque calculado com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "1500.75"))
            ),
            @ApiResponse(responseCode = "404", description = "Não foi possível calcular o valor total do estoque.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Não foi possível calcular o valor do estoque."
            }
            """))
            )
    })
    public ResponseEntity<Double> valorTotalEstoque(
            @Parameter(description = "ID do funcionário para cálculo de valor do estoque.", required = true)
            @PathVariable Integer idFuncionario) {
        Double valorTotal = service.valorTotalEstoqueProduto(idFuncionario);
        return ResponseEntity.status(200).body(valorTotal);
    }

    @GetMapping("/lucro-previsto-estoque/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Lucro previsto do estoque", description = "Calcula o lucro previsto a partir dos produtos no estoque de uma empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lucro previsto calculado com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "500.50"))
            ),
            @ApiResponse(responseCode = "404", description = "Não foi possível calcular o lucro previsto.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Não foi possível calcular o lucro previsto."
            }
            """))
            )
    })
    public ResponseEntity<Double> lucroPrevistoEstoque(
            @Parameter(description = "ID do funcionário para cálculo de valor do estoque.", required = true)
            @PathVariable Integer idFuncionario) {
        Double valorTotal = service.lucroPrevistoEstoqueProduto(idFuncionario);
        return ResponseEntity.status(200).body(valorTotal);
    }

    @GetMapping("/quantidade-estoque/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Quantidade total no estoque", description = "Obtém a quantidade total de produtos no estoque de uma empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade total obtida com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "120"))
            ),
            @ApiResponse(responseCode = "404", description = "Não foi possível obter a quantidade total.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Não foi possível obter a quantidade total."
            }
            """))
            )
    })
    public ResponseEntity<Integer> quantidadeProdutosEstoque(
            @Parameter(description = "ID do funcionário para cálculo total de produtos no estoque.", required = true)
            @PathVariable Integer idFuncionario) {
        Integer valorTotal = service.quantidadeProdutoEstoque(idFuncionario);
        return ResponseEntity.status(200).body(valorTotal);
    }

    @GetMapping("/quantidade-estoque-baixo/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Quantidade de produtos com estoque baixo", description = "Obtém a quantidade de produtos com estoque abaixo do limite mínimo de uma empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade de produtos obtida com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "15"))
            ),
            @ApiResponse(responseCode = "404", description = "Não foi possível obter a quantidade de produtos com estoque baixo.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Não foi possível obter a quantidade de produtos com estoque baixo."
            }
            """))
            )
    })
    public ResponseEntity<Integer> quantidadeProdutosEstoqueBaixo(
            @Parameter(description = "ID do funcionário para cálculo de produtos em estoque baixo.", required = true)
            @PathVariable Integer idFuncionario) {
        Integer quantidadeProdutos = service.quantidadeProdutoEstoqueBaixo(idFuncionario);
        return ResponseEntity.status(200).body(quantidadeProdutos);
    }

    @GetMapping("/quantidade-estoque-alto/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Quantidade de produtos com estoque alto", description = "Obtém a quantidade de produtos com estoque acima do limite máximo de uma empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade de produtos obtida com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "20"))
            ),
            @ApiResponse(responseCode = "404", description = "Não foi possível obter a quantidade de produtos com estoque alto.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Não foi possível obter a quantidade de produtos com estoque alto."
            }
            """))
            )
    })
    public ResponseEntity<Integer> quantidadeProdutosEstoqueAlto(
            @Parameter(description = "ID do funcionário para cálculo de produtos em estoque alto.", required = true)
            @PathVariable Integer idFuncionario) {
        Integer quantidadeProdutos = service.quantidadeProdutoEstoqueAlto(idFuncionario);
        return ResponseEntity.status(200).body(quantidadeProdutos);
    }


    @GetMapping("/categoria/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar produtos por categoria", description = "Lista todos os produtos de uma determinada categoria na empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos da categoria listados com sucesso.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    example = """
                          [
                            {
                              "id": 1,
                              "nome": "Arroz Branco",
                              "quantidade": 50,
                              "valorCompra": 15.9,
                              "valorUnitario": 22
                            },
                            {
                              "id": 2,
                              "nome": "Feijão Preto",
                              "quantidade": 30,
                              "valorCompra": 12.5,
                              "valorUnitario": 18
                            }
                          ]""")))),
            @ApiResponse(responseCode = "404", description = "Produtos não encontrados nessa categoria.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Não foram encontrados produtos nessa categoria."
            }
            """))
            )
    })
    public ResponseEntity<List<ProdutoListagemDto>> listarProdutosPorCategoria(
            @Parameter(description = "ID da categoria para listagem de produtos", required = true)
            @RequestParam Integer categoriaId,
            @Parameter(description = "ID do funcionário que requisitou a listagem", required = true)
            @PathVariable Integer idFuncionario) {
        List<ProdutoListagemDto> responseDto = service.listarProdutoPorCategoriaEmpresa(categoriaId, idFuncionario);
        return ResponseEntity.status(200).body(responseDto);
    }


    @GetMapping("/setor/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar produtos por setor", description = "Lista todos os produtos de um determinado setor na empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos do setor listados com sucesso.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    example = """
                      [
                        {
                          "id": 1,
                          "nome": "Arroz Branco",
                          "quantidade": 50,
                          "valorCompra": 15.9,
                          "valorUnitario": 22
                        },
                        {
                          "id": 2,
                          "nome": "Feijão Preto",
                          "quantidade": 30,
                          "valorCompra": 12.5,
                          "valorUnitario": 18
                        }
                      ]""")))),
            @ApiResponse(responseCode = "404", description = "Produtos não encontrados nesse setor.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
        {
          "mensagem": "Não foram encontrados produtos nesse setor."
        }
        """))
            )
    })
    public ResponseEntity<List<ProdutoListagemDto>> listarProdutosPorSetor(
            @Parameter(description = "ID do setor para listagem de produtos.", required = true)
            @RequestParam Integer setorId,
            @Parameter(description = "ID do funcionário que requisitou a listagem.", required = true)
            @PathVariable Integer idFuncionario) {
        List<ProdutoListagemDto> responseDto = service.listarProdutoPorSetorEmpresa(setorId, idFuncionario);
        return ResponseEntity.status(200).body(responseDto);
    }


    @GetMapping("/nome/{nome}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar produtos por nome", description = "Lista todos os produtos com base no nome na empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso pelo nome",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    example = """
                      [
                        {
                          "id": 1,
                          "nome": "Arroz Branco",
                          "quantidade": 50,
                          "valorCompra": 15.9,
                          "valorUnitario": 22
                        },
                        {
                          "id": 2,
                          "nome": "Feijão Preto",
                          "quantidade": 30,
                          "valorCompra": 12.5,
                          "valorUnitario": 18
                        }
                      ]""")))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado com esse nome.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
        {
          "mensagem": "Não foram encontrados produtos com esse nome."
        }
        """))
            )
    })
    public ResponseEntity<List<ProdutoListagemDto>> listarProdutosPorNome(
            @Parameter(description = "Nome do produto que está sendo procurado.", required = true)
            @PathVariable String nome,
            @Parameter(description = "ID do funcionário que requisitou a listagem.", required = true)
            @PathVariable Integer idFuncionario) {
        List<ProdutoListagemDto> responseDto = service.listarProdutoPorNomeEmpresa(nome, idFuncionario);
        return ResponseEntity.status(200).body(responseDto);
    }


}
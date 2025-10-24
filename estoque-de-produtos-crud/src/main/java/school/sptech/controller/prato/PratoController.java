package school.sptech.controller.prato;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.controller.prato.dto.AtualizarPratoDto;
import school.sptech.controller.prato.dto.CadastroPratoDto;
import school.sptech.controller.prato.dto.PratoMapper;
import school.sptech.controller.prato.dto.RespostaPratoDto;
import school.sptech.service.prato.PratoService;

import java.util.List;

@RestController
@RequestMapping("/pratos")
@Tag(name = "Pratos", description = "Operações relacionadas aos pratos.")
public class PratoController {

    private final PratoService pratoService;

    public PratoController(PratoService pratoService) {
        this.pratoService = pratoService;
    }

    @GetMapping("/todos-pratos/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar pratos", description = "Lista todos os pratos de uma determinada empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pratos listados com sucesso.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    example = """
                                    [
                                        {
                                            "id": 1,
                                            "nome": "Feijoada",
                                            "descricao": "Feijão preto com carnes diversas.",
                                            "preco": 45.90,
                                            "disponivel": true,
                                            "categoria": {
                                                "id": 3,
                                                "nome": "Pratos Típicos"
                                            },
                                            "setor": {
                                                "id": 3,
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
                                            "nome": "Salada Caesar",
                                            "descricao": "Alface americana, croutons, parmesão e molho Caesar.",
                                            "preco": 29.50,
                                            "disponivel": true,
                                            "categoria": {
                                                "id": 4,
                                            },
                                            "setor": {
                                                "id": 4,
                                            },
                                            "funcionario": {
                                                "id": 1,
                                                "empresa": {
                                                    "id": 1
                                                  }
                                            }
                                        }
                                    ]
                                    """)))),
            @ApiResponse(responseCode = "404", description = "Nenhum prato encontrado para esta empresa.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "mensagem": "Não foram encontrados pratos para esta empresa."
                                    }
                                    """))
            )
    })
    public ResponseEntity<List<RespostaPratoDto>> listarTodosPratos(
            @Parameter(description = "ID do funcionário para listagem de pratos.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(PratoMapper
                        .toResponseDtoList(pratoService.listarTodosPratos(idFuncionario)));
    }

    @GetMapping("/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Listar pratos com paginação",
            description = "Lista todos os pratos de uma determinada empresa, com paginação, ordenação e filtro por nome."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pratos listados com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RespostaPratoDto.class),
                            examples = @ExampleObject(value = """
                                {
                                    "content": [
                                        {
                                            "id": 1,
                                            "nome": "Feijoada",
                                            "descricao": "Feijão preto com carnes diversas.",
                                            "preco": 45.90,
                                            "disponivel": true,
                                            "categoria": { "id": 3, "nome": "Pratos Típicos" },
                                            "setor": { "id": 3, "nome": "Cozinha" },
                                            "funcionario": { "id": 1, "empresa": { "id": 1 } }
                                        },
                                        {
                                            "id": 2,
                                            "nome": "Salada Caesar",
                                            "descricao": "Alface americana, croutons, parmesão e molho Caesar.",
                                            "preco": 29.50,
                                            "disponivel": true,
                                            "categoria": { "id": 4, "nome": "Saladas" },
                                            "setor": { "id": 4, "nome": "Cozinha" },
                                            "funcionario": { "id": 1, "empresa": { "id": 1 } }
                                        }
                                    ],
                                    "number": 0,
                                    "size": 10,
                                    "totalElements": 2,
                                    "totalPages": 1
                                }
                                """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhum prato encontrado para esta empresa.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = """
                                {
                                    "mensagem": "Não foram encontrados pratos para esta empresa."
                                }
                                """)
                    )
            )
    })
    public ResponseEntity<Page<RespostaPratoDto>> listarPratosPaginado(
            @Parameter(description = "ID do funcionário para listagem de pratos.", required = true)
            @PathVariable Integer idFuncionario,
            @Parameter(description = "Número da página a ser retornada. Padrão: 0")
            @RequestParam(defaultValue = "0") int pagina,
            @Parameter(description = "Quantidade de itens por página. Padrão: 10")
            @RequestParam(defaultValue = "10") int quantidadePorPagina,
            @Parameter(description = "Ordem de exibição: 'asc' ou 'desc'. Padrão: 'asc'")
            @RequestParam(defaultValue = "asc") String ordem,
            @Parameter(description = "Filtro opcional pelo nome do prato.")
            @RequestParam(required = false) String termoBusca,
            @Parameter(description = "Filtro opcional por disponibilidade do prato.")
            @RequestParam(required = false) Boolean disponivel,
            @Parameter(description = "Filtro opcional pelo setor do prato.")
            @RequestParam(required = false) Integer setorId,
            @Parameter(description = "Filtro opcional pela categoria do prato.")
            @RequestParam(required = false) Integer categoriaId,
            @Parameter(description = "Filtro opcional pela area do prato.")
            @RequestParam(required = false) Integer areaId
    ) {
        return ResponseEntity.ok(
                PratoMapper.toResponseDtoPage(
                        pratoService.listarPratosPaginado(idFuncionario, pagina, quantidadePorPagina, ordem, termoBusca, disponivel, setorId, categoriaId, areaId)
                )
        );
    }

    @GetMapping("/{id}/{idFuncionario}")
    public ResponseEntity<RespostaPratoDto> buscarPratoPorId(
            @Parameter(description = "ID do prato para busca.", required = true)
            @PathVariable Integer id,
            @Parameter(description = "ID do funcionário para busca de prato.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(PratoMapper
                        .toResponseDto(pratoService.buscarPratoPorId(id, idFuncionario)));
    }

    @PostMapping("/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Cadastrar novo prato", description = "Cadastra um novo prato para uma determinada empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prato cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
                                    }
                                    """))
            )
    })
    public ResponseEntity<RespostaPratoDto> cadastrarPrato(
            @Parameter(description = "ID do funcionário responsável pelo cadastro do prato.", required = true)
            @Valid @RequestPart CadastroPratoDto request,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem,
            @Parameter(description = "Dados do prato para cadastro.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(PratoMapper.toResponseDto
                        (pratoService.cadastrarPrato(
                                PratoMapper.toEntity(request), imagem, idFuncionario)));
    }

    @PatchMapping("/{id}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar prato existente de determinada empresa", description = "Atualiza os dados de um prato existente de determinada empresa da base de dados, a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prato atualizado com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "id": 2,
                                        "nome": "Lanche Natural",
                                        "descricao": "Pão com Gergilim, Alface, Frango e Molho.",
                                        "preco": 22.00,
                                        "disponivel": true,
                                        "categoria": {
                                            "id": 4
                                        },
                                        "setor": {
                                            "id": 4
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
            @ApiResponse(responseCode = "404", description = "Prato não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "mensagem": "Prato não encontrado."
                                    }
                                    """))
            )
    })
    public ResponseEntity<RespostaPratoDto> atualizarPrato(
            @Parameter(description = "Dados do prato para atualização.", required = true)
            @Valid @RequestBody AtualizarPratoDto request,
            @Parameter(description = "ID do prato a ser atualizado.", required = true)
            @PathVariable Integer id,
            @Parameter(description = "ID do funcionário responsável pela atualização do prato.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(PratoMapper.toResponseDto
                        (pratoService.atualizarPrato(
                                PratoMapper.toEntity(request),  id, idFuncionario)));
    }

    @DeleteMapping("/{id}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Remover prato", description = "Remove um prato da base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prato removido com sucesso.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Prato não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "mensagem": "Prato não encontrado."
                                    }
                                    """))
            )
    })
    public ResponseEntity<Void> deletarPrato(
            @Parameter(description = "ID do prato a ser removido.", required = true)
            @PathVariable Integer id,
            @Parameter(description = "ID do funcionário responsável pela remoção do prato.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        pratoService.deletarPrato(id, idFuncionario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar pratos por nome", description = "Busca pratos pelo nome em uma determinada empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pratos encontrados com sucesso.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    example = """
                                    [
                                        {
                                            "id": 1,
                                            "nome": "Feijoada",
                                            "descricao": "Feijão preto com carnes diversas.",
                                            "preco": 45.90,
                                            "disponivel": true,
                                            "categoria": {
                                                "id": 3,
                                                "nome": "Pratos Típicos"
                                            },
                                            "setor": {
                                                "id": 3,
                                                "nome": "Cozinha Quente"
                                            }
                                        },
                                        {
                                            "id": 2,
                                            "nome": "Feijão Tropeiro",
                                            "descricao": "Feijão tropeiro, prato tipico do Brasil.",
                                            "preco": 29.90,
                                            "disponivel": true,
                                            "categoria": {
                                                "id": 3,
                                                "nome": "Pratos Típicos"
                                            },
                                            "setor": {
                                                "id": 3,
                                                "nome": "Cozinha Quente"
                                            }
                                        }
                                    ]
                                    """)))),
            @ApiResponse(responseCode = "404", description = "Nenhum prato encontrado com este nome.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "mensagem": "Nenhum prato encontrado com este nome."
                                    }
                                    """))
            )
    })
    public ResponseEntity<List<RespostaPratoDto>> buscarPratos(
            @Parameter(description = "Nome do prato a ser buscado.", required = true)
            @RequestParam String nome,
            @Parameter(description = "ID do funcionário que está buscando os pratos.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(PratoMapper
                        .toResponseDtoList(pratoService.buscarPratosPorNome(nome, idFuncionario)));
    }

    @GetMapping("/categoria/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar pratos por categoria", description = "Lista os pratos de uma determinada categoria em uma empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pratos da categoria encontrados com sucesso.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    example = """
                                    [
                                        {
                                            "id": 1,
                                            "nome": "Lanche de Frango",
                                            "descricao": "Pão, Frango, Requeijão e Milho.",
                                            "preco": 12.90,
                                            "disponivel": true
                                        },
                                        {
                                            "id": 2,
                                            "nome": "Lanche Natural",
                                            "descricao": "Pão, Alface, Tomate e Molho.",
                                            "preco": 18.00,
                                            "disponivel": true
                                        }
                                    ]
                                    """)))),
            @ApiResponse(responseCode = "404", description = "Nenhum prato encontrado nesta categoria.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "mensagem": "Nenhum prato encontrado nesta categoria."
                                    }
                                    """))
            )
    })
    public ResponseEntity<List<RespostaPratoDto>> buscarPratosPorCategoria(
            @Parameter(description = "ID da categoria para buscar os pratos.", required = true)
            @RequestParam Integer categoriaId,
            @Parameter(description = "ID do funcionário que está buscando os pratos.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(PratoMapper
                        .toResponseDtoList(pratoService.buscarPratosPorCategoria(categoriaId, idFuncionario)));
    }

    @GetMapping("/setor/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar pratos por setor", description = "Lista os pratos de um determinado setor em uma empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pratos do setor encontrados com sucesso.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    example = """
                                    [
                                        {
                                            "id": 1,
                                            "nome": "Feijoada",
                                            "descricao": "Feijão preto com carnes diversas.",
                                            "preco": 45.90,
                                            "disponivel": true
                                        },
                                        {
                                            "id": 2,
                                            "nome": "Lanche Natural",
                                            "descricao": "Feijão preto com carnes diversas.",
                                            "preco": 45.90,
                                            "disponivel": true
                                        }
                                    ]
                                    """)))),
            @ApiResponse(responseCode = "404", description = "Nenhum prato encontrado neste setor.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                        "mensagem": "Nenhum prato encontrado neste setor."
                                    }
                                    """))
            )
    })
    public ResponseEntity<List<RespostaPratoDto>> buscarPratosPorSetor(
            @Parameter(description = "ID do setor para buscar os pratos.", required = true)
            @RequestParam Integer setorId,
            @Parameter(description = "ID do funcionário que está buscando os pratos.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(PratoMapper
                        .toResponseDtoList(pratoService.buscarPratosPorSetor(setorId, idFuncionario)));
    }

    @GetMapping("/valor-pratos/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Double> buscarTotalPratos(
            @Parameter(description = "ID do funcionário que está buscando os pratos.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity.ok(pratoService.valorTotalEstoquePratos(idFuncionario));
    }

    @GetMapping("/valor-pratos-todos/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Double> buscarTotalPratosTodos(
            @Parameter(description = "ID do funcionário que está buscando os pratos.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity.ok(pratoService.valorTotalEstoquePratosInativosEAtivos(idFuncionario));
    }

    @GetMapping("/quantidade-pratos/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Integer> buscarQuantidadePratos(
            @Parameter(description = "ID do funcionário que está buscando os pratos.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity.ok(pratoService.totalPratosEstoque(idFuncionario));
    }

    @GetMapping("/pratos-ativos/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Integer> buscarQuantidadePratosAtivos(
            @Parameter(description = "ID do funcionário que está buscando os pratos.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity.ok(pratoService.totalPratosAtivos(idFuncionario));
    }

    @GetMapping("/pratos-inativos/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Integer> buscarQuantidadePratosInativos(
            @Parameter(description = "ID do funcionário que está buscando os pratos.", required = true)
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity.ok(pratoService.totalPratosInativos(idFuncionario));
    }

    @GetMapping("/total-setores-pratos/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Integer> buscarQuantidadeSetores(
            @Parameter(description = "ID do funcionário que está buscando a quantidade de setores por pratos.", required = true)
            @PathVariable Integer idFuncionario
    ){
        return ResponseEntity.ok(pratoService.totalSetoresPratosPorEmpresa(idFuncionario));
    }

    @GetMapping("/total-categorias-pratos/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Integer> buscarQuantidadeCategorias(
            @Parameter(description = "ID do funcionário que está buscando a quantidade de categorias por pratos.", required = true)
            @PathVariable Integer idFuncionario
    ){
        return ResponseEntity.ok(pratoService.totalCategoriasPratosPorEmpresa(idFuncionario));
    }
}
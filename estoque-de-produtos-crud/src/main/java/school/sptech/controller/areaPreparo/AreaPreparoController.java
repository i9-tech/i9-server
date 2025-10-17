package school.sptech.controller.areaPreparo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.areaPreparo.dto.AreaPreparoAtualizarDto;
import school.sptech.controller.areaPreparo.dto.AreaPreparoCadastroDto;
import school.sptech.controller.areaPreparo.dto.AreaPreparoListagemDto;
import school.sptech.controller.areaPreparo.dto.AreaPreparoMapper;
import school.sptech.entity.areaPreparo.AreaPreparo;
import school.sptech.service.areaPreparo.AreaPreparoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/areas-preparo")
public class AreaPreparoController {

    private final AreaPreparoService areaPreparoService;

    public AreaPreparoController(AreaPreparoService areaPreparoService) {
        this.areaPreparoService = areaPreparoService;
    }

    @GetMapping("/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar áreas de preparo", description = "Lista todas as áreas de preparo associadas ao funcionário informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Áreas de preparo listadas com sucesso.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    example = """
                [
                  {
                     "id": 1,
                     "nome": "Chaperia",
                     "funcionario": {
                       "id": 1,
                       "empresa": {
                         "id": 1
                        }
                      }
                    },
                    {
                      "id": 2,
                      "nome": "Massas",
                      "funcionario": {
                        "id": 1,
                        "empresa": {
                          "id": 1
                        }
                      }
                    },
                    {
                      "id": 3,
                      "nome": "Choperia",
                      "funcionario": {
                        "id": 2,
                        "empresa": {
                          "id": 1
                        }
                      }
                    }
                ]""")))),
            @ApiResponse(responseCode = "404", description = "Nenhuma área de preparo encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Não foi encontrada nenhuma área de preparo nessa empresa."
            }
            """))
            )
    })
    public ResponseEntity<List<AreaPreparoListagemDto>> listar(
            @Parameter(description = "Identificação do funcionário que está solicitando a listagem.", required = true)
            @PathVariable Integer idFuncionario) {

        List<AreaPreparo> areasPreparo = areaPreparoService.listarTodasAreasPreparo(idFuncionario);

        if (areasPreparo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(AreaPreparoMapper.transformarEmRespostaListaDto(areasPreparo));
    }

    @GetMapping("/{idAreaPreparo}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar área de preparo por ID", description = "Retorna uma área de preparo específica com base em seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área de preparo encontrada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Área de preparo com ID não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "A área de preparo não foi encontrada nesse ID."
            }
            """))
            )
    })
    public ResponseEntity<AreaPreparoListagemDto> buscarPorId(
            @PathVariable Integer idAreaPreparo,
            @PathVariable Integer idFuncionario) {

        Optional<AreaPreparo> areaPreparoEncontrada = areaPreparoService.buscarAreasPorId(idAreaPreparo, idFuncionario);

        if (areaPreparoEncontrada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        AreaPreparoListagemDto resposta = AreaPreparoMapper.transformarEmRespostaDto(areaPreparoEncontrada.get());
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PostMapping("/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Cadastrar nova área de preparo", description = "Cadastra uma nova área de preparo na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Área de preparo cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
            }
            """))
            )
    })
    public ResponseEntity<AreaPreparoListagemDto> cadastrar(
            @Parameter(description = "Dados da área de preparo para cadastro.", required = true)
            @Valid @RequestBody AreaPreparoCadastroDto areaPreparoDto,
            @Parameter(description = "Identificação do funcionário que está cadastrando a área de preparo.", required = true)
            @PathVariable Integer idFuncionario) {

        AreaPreparo novaAreaPreparo = areaPreparoService.cadastrarAreaPreparo(
                AreaPreparoMapper.transformarEmEntidade(areaPreparoDto), idFuncionario);

        AreaPreparoListagemDto resposta = AreaPreparoMapper.transformarEmRespostaDto(novaAreaPreparo);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping("/{id}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar área de preparo existente", description = "Atualiza uma área de preparo da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área de preparo atualizada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
             {
                  "id": 4,
                  "nome": "Sobremesas",
                  "funcionario": {
                    "id": 2,
                    "empresa": {
                      "id": 1
                    }
                  }
             }
            """))
            ),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
            }
            """))
            ),
            @ApiResponse(responseCode = "404", description = "Área de preparo não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "A área de preparo não foi encontrada."
            }
            """))
            )
    })
    public ResponseEntity<AreaPreparoListagemDto> atualizar(
            @Parameter(description = "ID da área de preparo", example = "1", required = true)
            @PathVariable Integer id,
            @Parameter(description = "ID do funcionário encarregado pela atualização.", example = "1", required = true)
            @PathVariable Integer idFuncionario,
            @Parameter(description = "Dados da área de preparo para atualização.", required = true)
            @Valid @RequestBody AreaPreparoAtualizarDto areaPreparoDto) {

        AreaPreparo entidadeAtualizada = areaPreparoService.atualizarArea(
                id, AreaPreparoMapper.transformarEmEntidade(areaPreparoDto), idFuncionario);

        AreaPreparoListagemDto resposta = AreaPreparoMapper.transformarEmRespostaDto(entidadeAtualizada);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @DeleteMapping("/{areaId}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Remover área de preparo existente", description = "Remove uma área de preparo da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Área de preparo removida com sucesso.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Área de preparo não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "A área de preparo não foi encontrada."
            }
            """)))
    })
    public ResponseEntity<Void> remover(
            @Parameter(description = "ID da área de preparo.", example = "1", required = true)
            @PathVariable Integer areaId,
            @Parameter(description = "ID do funcionário responsável pela remoção da área de preparo.", example = "1", required = true)
            @PathVariable Integer idFuncionario) {

        areaPreparoService.removerAreaPreparo(areaId, idFuncionario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

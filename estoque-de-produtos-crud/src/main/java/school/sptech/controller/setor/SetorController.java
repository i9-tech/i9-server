package school.sptech.controller.setor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.setor.dto.SetorAtualizarDto;
import school.sptech.controller.setor.dto.SetorCadastroDto;
import school.sptech.controller.setor.dto.SetorListagemDto;
import school.sptech.controller.setor.dto.SetorMapper;
import school.sptech.entity.setor.Setor;
import school.sptech.service.setor.SetorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/setores")
@Tag(name = "Setores", description = "Operações relacionadas aos setores de produtos e serviços.")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @GetMapping("/{idFuncionario}")
    @Operation(summary = "Listar setores", description = "Lista todos os setores presentes na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Setores listados com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    example = """
                [
                  {
                    "id": 1,
                    "nome": "Pastelaria",
                    "funcionario": {
                      "id": 1,
                      "empresa": {
                        "id": 1
                      }
                    }
                  },
                  {
                    "id": 2,
                    "nome": "Lanchonete",
                    "funcionario": {
                      "id": 2,
                      "empresa": {
                        "id": 1
                      }
                    }
                  },
                  {
                    "id": 3,
                    "nome": "Insdustrializados",
                    "funcionario": {
                      "id": 3,
                      "empresa": {
                        "id": 2
                      }
                    }
                  }
                ]""")))),
            @ApiResponse(responseCode = "404", description = "Setores não encontrados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Não foi encontrado nenhum setor nessa empresa."
            }
            """))
            )
    })
    public ResponseEntity<List<SetorListagemDto>> listagem(
            @Parameter(description = "Identificação do funcionário que está cadastrando setor.", required = true)
            @PathVariable Integer idFuncionario) {
        List<Setor> buscarSetor = setorService.listarTodosSetores(idFuncionario);
        if (buscarSetor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(SetorMapper.transformarEmListaRespostaDto(buscarSetor));
    }

    @GetMapping("/{idSetor}/{idFuncionario}")
    @Operation(summary = "Buscar setor por ID", description = "Retorna um setor presente na base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Setor encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Setor com ID não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "O setor não foi encontrado nesse ID"
            }
            """))
            )
    })
    public ResponseEntity<SetorListagemDto> listagemId(
            @PathVariable Integer idSetor,
            @PathVariable Integer idFuncionario) {
        Optional<Setor> setorEncontrado = setorService.buscarSetorPorId(idSetor, idFuncionario);

        if (setorEncontrado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        SetorListagemDto respostaListagemIdDto = SetorMapper.transformarEmRespostaDto(setorEncontrado.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaListagemIdDto);
    }

    @PostMapping("/{idFuncionario}")
    @Operation(summary = "Cadastrar novo setor", description = "Cadastra um novo setor na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Setor cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
            }
            """))
            )
    })
    public ResponseEntity<SetorListagemDto> cadastrar(
            @Parameter(description = "Dados do setor para cadastro.", required = true)
            @Valid @RequestBody SetorCadastroDto setorParaCadastro,
            @Parameter(description = "Identificação do funcionário que está cadastrando setor.", required = true)
            @PathVariable Integer idFuncionario) {
        Setor novoSetor = setorService.cadastrarSetor(SetorMapper.transformarEmEntidade(setorParaCadastro), idFuncionario);

        SetorListagemDto respostaSetorDto = SetorMapper.transformarEmRespostaDto(novoSetor);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaSetorDto);
    }

    @PutMapping("/{idSetor}/{idFuncionario}")
    @Operation(summary = "Atualizar setor existente", description = "Atualiza um setor da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Setor atualizado com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
             {
                  "id": 4,
                  "nome": "Lanchonete",
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
            @ApiResponse(responseCode = "404", description = "Setor não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "O setor não foi encontrado."
            }
            """))
            )
    })
    public ResponseEntity<SetorListagemDto> atualizar(
            @Parameter(description = "ID do setor.", example = "1", required = true)
            @PathVariable Integer idSetor,
            @Parameter(description = "Dados do setor para atualização.", required = true)
            @Valid @RequestBody SetorAtualizarDto setorParaAtualizar,
            @Parameter(description = "ID do funcionário encarregado pela atualização.", example = "1", required = true)
            @PathVariable Integer idFuncionario) {
        Setor entidadeParaAtualizar = setorService.atualizarSetor(idSetor, SetorMapper.transformarEmEntidade(setorParaAtualizar), idFuncionario);

        SetorListagemDto respostaAtualizadaDto = SetorMapper.transformarEmRespostaDto(entidadeParaAtualizar);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaAtualizadaDto);
    }

    @DeleteMapping("/{idSetor}/{idFuncionario}")
    @Operation(summary = "Remover setor existente", description = "Remove um setor da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Setor removido com sucesso.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Setor não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "O setor não foi encontrado."
            }
            """)))

    })
    public ResponseEntity<SetorListagemDto> remover(
            @Parameter(description = "ID do setor", example = "1", required = true)
            @PathVariable Integer idSetor,
            @Parameter(description = "ID do funcionário responsável pela remoção do setor.", example = "1", required = true)
            @PathVariable Integer idFuncionario
            ) {


        setorService.removerSetor(idSetor, idFuncionario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

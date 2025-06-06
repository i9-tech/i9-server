package school.sptech.controller.setor;

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
    @SecurityRequirement(name = "Bearer")
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
    public ResponseEntity<List<SetorListagemDto>> listagem(@PathVariable Integer idFuncionario) {
        List<SetorListagemDto> setores = setorService.listarTodosSetores(idFuncionario);
        if (setores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(setores);
    }

    @GetMapping("/{idSetor}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
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
        SetorListagemDto setor = setorService.buscarSetorPorId(idSetor, idFuncionario);
        return ResponseEntity.ok(setor);
    }

    @PostMapping("/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
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
            @Valid @RequestBody SetorCadastroDto setorParaCadastro,
            @PathVariable Integer idFuncionario) {
        SetorListagemDto novoSetor = setorService.cadastrarSetor(setorParaCadastro, idFuncionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoSetor);
    }

    @PutMapping("/{idSetor}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
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
            @PathVariable Integer idSetor,
            @Valid @RequestBody SetorAtualizarDto setorParaAtualizar,
            @PathVariable Integer idFuncionario) {
        SetorListagemDto atualizado = setorService.atualizarSetor(idSetor, setorParaAtualizar, idFuncionario);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{idSetor}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
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
    public ResponseEntity<Void> remover(
            @PathVariable Integer idSetor,
            @PathVariable Integer idFuncionario) {
        setorService.removerSetor(idSetor, idFuncionario);
        return ResponseEntity.noContent().build();
    }

}

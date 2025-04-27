package school.sptech.controller.categoria;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.categoria.dto.CategoriaAtualizarDto;
import school.sptech.controller.categoria.dto.CategoriaCadastroDto;
import school.sptech.controller.categoria.dto.CategoriaListagemDto;
import school.sptech.controller.categoria.dto.CategoriaMapper;
import school.sptech.entity.categoria.Categoria;
import school.sptech.service.categoria.CategoriaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Operações relacionadas as categorias de produtos e pratos.")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping({"/{idFuncionario}"})
    @Operation(summary = "Listar categorias", description = "Lista todas as categorias presentes na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    example = """
                [
                  {
                     "id": 1,
                     "nome": "Carnes e Proteínas",
                     "funcionario": {
                       "id": 1,
                       "empresa": {
                         "id": 1
                        }
                      }
                    },
                    {
                      "id": 2,
                      "nome": "Massas e Pizzas",
                      "funcionario": {
                        "id": 1,
                        "empresa": {
                          "id": 1
                        }
                      }
                    },
                    {
                      "id": 3,
                      "nome": "Bebidas",
                      "funcionario": {
                        "id": 2,
                        "empresa": {
                          "id": 1
                        }
                      }
                    }
                ]""")))),
            @ApiResponse(responseCode = "404", description = "Empresas não encontradas.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Não foi encontrada nenhuma categoria nessa empresa."
            }
            """))
            )
    })
    public ResponseEntity<List<CategoriaListagemDto>> listagem(
            @Parameter(description = "Identificação do funcionário que está cadastrando categoria.", required = true)
            @PathVariable Integer idFuncionario) {
        List<Categoria> buscarCategoria = categoriaService.listarTodasCategorias(idFuncionario);
        if (buscarCategoria.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(CategoriaMapper.transformarEmRespostaListaDto(buscarCategoria));
    }

    @GetMapping("/{id}/{idFuncionario}")
    @Operation(summary = "Buscar categoria por ID", description = "Retorna uma categoria presente na base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria com ID não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "A categoria não foi encontrada nesse ID."
            }
            """))
            )
    })
    public ResponseEntity<CategoriaListagemDto> listagemId(@PathVariable Integer idCategoria, @PathVariable Integer idFuncionario) {
        Optional<Categoria> categoriaEncontrado = categoriaService.buscarCategoriaPorId(idCategoria, idFuncionario);

        if (categoriaEncontrado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        CategoriaListagemDto respostaListagemIdDto = CategoriaMapper.transformarEmRespostaDto(categoriaEncontrado.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaListagemIdDto);
    }

    @PostMapping("/{idFuncionario}")
    @Operation(summary = "Cadastrar nova categoria", description = "Cadastra uma nova categoria na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
            }
            """))
            )
    })
    public ResponseEntity<CategoriaListagemDto> cadastrar(
            @Parameter(description = "Dados da categoria para cadastro.", required = true)
            @Valid @RequestBody CategoriaCadastroDto categoriaParaCadastro,
            @Parameter(description = "Identificação do funcionário que está cadastrando categoria.", required = true)
            @PathVariable Integer idFuncionario) {
        Categoria novaCategoria = categoriaService.cadastrarCategoria(CategoriaMapper.transformarEmEntidade(categoriaParaCadastro), idFuncionario);

        CategoriaListagemDto respostaCategoriaDto = CategoriaMapper.transformarEmRespostaDto(novaCategoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaCategoriaDto);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria existente", description = "Atualiza uma categoria da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso.",
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
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "A categoria não foi encontrada."
            }
            """))
            )
    })
    public ResponseEntity<CategoriaListagemDto> atualizar(
            @Parameter(description = "ID da categoria", example = "1", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Dados da categoria para atualização.", required = true)
            @Valid @RequestBody CategoriaAtualizarDto categoriaParaAtualizar) {
        Categoria entidadeParaAtualizar = categoriaService.atualizarCategoria(id, CategoriaMapper.transformarEmEntidade(categoriaParaAtualizar));

        CategoriaListagemDto respostaAtualizadaDto = CategoriaMapper.transformarEmRespostaDto(entidadeParaAtualizar);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaAtualizadaDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover categoria existente", description = "Remove uma categoria da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria removida com sucesso.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "A categoria não foi encontrada."
            }
            """)))
    })
    public ResponseEntity<CategoriaListagemDto> remover(
            @Parameter(description = "ID da categoria.", example = "1", required = true)
            @PathVariable Integer id) {
        categoriaService.removerCategoria(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
package school.sptech.controller.funcionario;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.funcionario.dto.FuncionarioRequestDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;

import school.sptech.service.funcionario.FuncionarioService;

import java.util.List;

//import java.util.List;

@RestController
@RequestMapping("/colaboradores")
@Tag(name = "Funcionários", description = "Operações relacionadas aos funcionários")
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @PostMapping("/{idEmpresa}")
    @Operation(summary = "Cadastrar novo funcionário", description = "Cadastra um novo funcionário na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
            }
            """))
            )

    })
    public ResponseEntity<FuncionarioResponseDto> cadastrar(
            @Parameter(description = "Dados do funcionário para cadastro", required = true)
            @Valid @RequestBody FuncionarioRequestDto requestDto,
            @Parameter(description = "ID da empresa contratante", required = true)
            @PathVariable Integer idEmpresa) {
        FuncionarioResponseDto responseDto = service.cadastrarFuncionario(requestDto, idEmpresa);
        return ResponseEntity.status(201).body(responseDto);
    }

    @GetMapping("/{idEmpresa}")
    @Operation(summary = "Listar funcionários", description = "Lista todos os funcionários de uma determinada empresa presentes na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários listados com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(
                                    example = """
                    [
                     	{
                     		"nome": "João Silva",
                     		"cpf": "123.456.789-00",
                     		"cargo": "Gerente",
                     		"dataAdmissao": "2023-05-15T03:00:00.000+00:00",
                     		"acessoSetorCozinha": true,
                     		"acessoSetorEstoque": true,
                     		"acessoSetorAtendimento": true,
                     		"proprietario": true
                     	},
                     	{
                     		"nome": "Maria Oliveira",
                     		"cpf": "987.654.321-11",
                     		"cargo": "Atendente",
                     		"dataAdmissao": "2023-08-20T03:00:00.000+00:00",
                     		"acessoSetorCozinha": false,
                     		"acessoSetorEstoque": false,
                     		"acessoSetorAtendimento": true,
                     		"proprietario": false
                     	}
                     ]""")))),
            @ApiResponse(responseCode = "404", description = "Funcionários não encontrados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Não foi encontrado nenhum funcionário nessa empresa"
            }
            """))
            )
    })
    public ResponseEntity<List<FuncionarioResponseDto>> listarPorEmpresa(
            @Parameter(description = "ID da empresa contratante", required = true)
            @PathVariable Integer idEmpresa) {
        List<FuncionarioResponseDto> responseDto = service.listarPorEmpresa(idEmpresa);
        return ResponseEntity.status(200).body(responseDto);
    }

    @GetMapping("/{id}/{idEmpresa}")
    @Operation(summary = "Buscar funcionário por ID", description = "Retorna um funcionário de uma determinada empresa presente na base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário com ID não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "O funcionário não foi encontrado nesse ID"
            }
            """))
            )
    })
    public ResponseEntity<FuncionarioResponseDto> buscarFuncionarioPorId(
            @Parameter(description = "ID do funcionário para busca", required = true)
            @PathVariable int id,
            @Parameter(description = "ID da empresa contratante", required = true)
            @PathVariable Integer idEmpresa) {
        FuncionarioResponseDto responseDto = service.buscarFuncionarioPorId(id, idEmpresa);
        return ResponseEntity.status(200).body(responseDto);
    }

    @DeleteMapping("/{id}/{idEmpresa}")
    @Operation(summary = "Remover funcionário existente em uma empresa específica", description = "Remove um funcionário de determinada empresa da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Funcionário removido com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "O funcionário não foi encontrado"
            }
            """)))
    })
    public ResponseEntity<Void> removerPorId(
            @Parameter(description = "ID do funcionário para busca", required = true)
            @PathVariable int id,
            @Parameter(description = "ID da empresa contratante", required = true)
            @PathVariable Integer idEmpresa) {
        service.removerPorId(id, idEmpresa);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/{idEmpresa}")
    @Operation(summary = "Atualizar funcionário existente em determinada empresa", description = "Atualiza um funcionário de uma determinada empresa da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
             	"nome": "Pedro José",
             	"cpf": "123.456.789-00",
             	"cargo": "Gerente",
             	"dataAdmissao": "2023-05-15T03:00:00.000+00:00",
             	"acessoSetorCozinha": false,
             	"acessoSetorEstoque": true,
             	"acessoSetorAtendimento": false,
             	"proprietario": false
            }
            """))
            ),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
            }
            """))
            ),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "A empresa não foi encontrada"
            }
            """))
            )
    })
    public ResponseEntity<FuncionarioResponseDto> editarFuncionario(
            @Parameter(description = "ID do funcionário para atualização", required = true)
            @PathVariable int id,
            @Parameter(description = "Dados do funcionáario para atualização", required = true)
            @Valid @RequestBody FuncionarioRequestDto funcionarioParaEditar,
            @Parameter(description = "ID da empresa contratante", required = true)
            @PathVariable Integer idEmpresa) {
        FuncionarioResponseDto responseDto = service.editarFuncionario(id, idEmpresa, funcionarioParaEditar);
        return ResponseEntity.status(200).body(responseDto);
    }
}

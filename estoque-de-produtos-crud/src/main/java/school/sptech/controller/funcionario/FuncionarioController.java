package school.sptech.controller.funcionario;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
import school.sptech.controller.funcionario.dto.*;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.service.funcionario.FuncionarioService;

import java.util.List;

@RestController
@RequestMapping("/colaboradores")
@Tag(name = "Funcionários", description = "Operações relacionadas aos funcionários.")
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @PostMapping("/{idEmpresa}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Cadastrar novo funcionário", description = "Cadastra um novo funcionário na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
            }
            """))
            )

    })
    public ResponseEntity<FuncionarioResponseDto> cadastrar(
            @Parameter(description = "Dados do funcionário para cadastro.", required = true)
            @Valid @RequestBody FuncionarioRequestDto requestDto,
            @Parameter(description = "ID da empresa contratante.", required = true)
            @PathVariable Integer idEmpresa) {


        FuncionarioResponseDto respostaFuncionarioDto = service.cadastrarFuncionario(
                FuncionarioMapper.toEntity(requestDto),
                idEmpresa
        );

        return ResponseEntity.status(201).body(respostaFuncionarioDto);
    }

    @PostMapping("/login")
    public ResponseEntity<FuncionarioTokenDto> login(@RequestBody FuncionarioLoginDto funcionarioLoginDto) {

        final Funcionario funcionario = FuncionarioMapper.of(funcionarioLoginDto);
        FuncionarioTokenDto funcionarioTokenDto = this.service.autenticar(funcionario);

        return ResponseEntity.status(200).body(funcionarioTokenDto);
    }

    @PostMapping("/login/criptografar")
    public ResponseEntity<String> criptografarSenha(@RequestParam String senha) {
        return ResponseEntity.ok(service.criptografar(senha));
    }


    @GetMapping("/{idEmpresa}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar funcionários", description = "Lista todos os funcionários de uma determinada empresa presentes na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários listados com sucesso.",
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
            @ApiResponse(responseCode = "404", description = "Funcionários não encontrados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Não foi encontrado nenhum funcionário nessa empresa."
            }
            """))
            )
    })
    public ResponseEntity<List<FuncionarioResponseDto>> listarPorEmpresa(
            @Parameter(description = "ID da empresa contratante.", required = true)
            @PathVariable Integer idEmpresa) {
        List<Funcionario> buscarFuncionario = service.listarPorEmpresa(idEmpresa);
        return ResponseEntity.status(200).body(FuncionarioMapper.toDtoList(buscarFuncionario));
    }

    @GetMapping("/{id}/{idEmpresa}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar funcionário por ID", description = "Retorna um funcionário de uma determinada empresa presente na base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Funcionário com ID não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "O funcionário não foi encontrado nesse ID."
            }
            """))
            )
    })
    public ResponseEntity<FuncionarioResponseDto> buscarFuncionarioPorId(
            @Parameter(description = "ID do funcionário para busca.", required = true)
            @PathVariable int id,
            @Parameter(description = "ID da empresa contratante.", required = true)
            @PathVariable Integer idEmpresa) {

        Funcionario funcionario = service.buscarFuncionarioPorId(id, idEmpresa);
        FuncionarioResponseDto dto = FuncionarioMapper.toDto(funcionario);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}/{idEmpresa}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Remover funcionário existente em uma empresa específica", description = "Remove um funcionário de determinada empresa da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Funcionário removido com sucesso.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "O funcionário não foi encontrado."
            }
            """)))
    })
    public ResponseEntity<Void> removerPorId(
            @Parameter(description = "ID do funcionário para busca.", required = true)
            @PathVariable int id,
            @Parameter(description = "ID da empresa contratante.", required = true)
            @PathVariable Integer idEmpresa) {
        service.removerPorId(id, idEmpresa);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/{idEmpresa}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar funcionário existente em determinada empresa", description = "Atualiza um funcionário de uma determinada empresa da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso.",
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
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
            }
            """))
            ),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "A empresa não foi encontrada."
            }
            """))
            )
    })
    public ResponseEntity<FuncionarioResponseDto> editarFuncionario(
            @PathVariable int id,
            @Valid @RequestBody FuncionarioRequestDto funcionarioDto,
            @PathVariable Integer idEmpresa) {

        Funcionario funcionarioParaEditar = FuncionarioMapper.toEntity(funcionarioDto);
        Funcionario funcionarioAtualizado = service.editarFuncionario(id, idEmpresa, funcionarioParaEditar);
        FuncionarioResponseDto responseDto = FuncionarioMapper.toDto(funcionarioAtualizado);

        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/primeiro-acesso/{id}/{idEmpresa}")
    public ResponseEntity<Void> redefinirSenha(
            @PathVariable int id,
            @PathVariable Integer idEmpresa,
            @RequestBody RedefinirSenhaDto dto) {

        service.redefinirSenhaPrimeiroAcesso(id, idEmpresa, dto.getSenha(), dto.isPrimeiroAcesso());
        return ResponseEntity.ok().build();
    }


}

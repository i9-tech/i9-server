package school.sptech.controller.empresa;


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
import school.sptech.controller.empresa.dto.EmpresaAtualizarDto;
import school.sptech.controller.empresa.dto.EmpresaListagemDto;
import school.sptech.controller.empresa.dto.EmpresaMapper;
import school.sptech.controller.empresa.dto.EmpresaCadastroDto;
import school.sptech.entity.empresa.Empresa;
import school.sptech.service.empresa.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("/empresas")
@Tag(name = "Empresas", description = "Operações relacionadas as empresas.")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova empresa", description = "Cadastra uma nova empresa na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empresa cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
            }
            """))
            )

    })
    public ResponseEntity<EmpresaListagemDto> empresa(
            @Parameter(description = "Dados da empresa para cadastro", required = true)
            @Valid @RequestBody EmpresaCadastroDto empresaCadastroDto) {
        Empresa novaEmpresa = empresaService.cadastrarEmpresa(EmpresaMapper.transformarEmEntidade(empresaCadastroDto));

        EmpresaListagemDto respostaEmpresaDto = EmpresaMapper.transformarEmRespostaDto(novaEmpresa);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaEmpresaDto);
    }

    @GetMapping
    @Operation(summary = "Listar empresas", description = "Lista todas as empresas presentes na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresas listadas com sucesso.",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(
                            example = """
                [
                  {
                    "id": 1,
                    "nome": "Restaurante Tauá",
                    "cnpj": "12.345.678/0001-95",
                    "endereco": "Rua Haddock Lobo, 595 - Consolação, São Paulo - SP",
                    "dataCadastro": "2025-04-25",
                    "ativo": true
                  },
                  {
                    "id": 2,
                    "nome": "Lanchonete da Vila",
                    "cnpj": "98.765.432/0001-10",
                    "endereco": "Av. Faria Lima, 1234 - Itaim Bibi, São Paulo - SP",
                    "dataCadastro": "2025-04-20",
                    "ativo": true
                  }
                ]""")))),
            @ApiResponse(responseCode = "404", description = "Empresas não encontradas.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Não foi encontrada nenhuma empresa."
            }
            """))
            )
    })
    public ResponseEntity<List<EmpresaListagemDto>> listagem() {

        List<EmpresaListagemDto> respostaListagemIdDto = EmpresaMapper.transformarEmRespostaDtoList(empresaService.listarEmpresa());

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaListagemIdDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empresa por ID", description = "Retorna uma empresa presente na base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Empresa com ID não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "A empresa não foi encontrada nesse ID."
            }
            """))
            )
    })
    public ResponseEntity<EmpresaListagemDto> listagemPorId(
            @Parameter(description = "ID da empresa", example = "1", required = true)
            @PathVariable Integer id) {

        EmpresaListagemDto respostaListagemIdDto = EmpresaMapper.transformarEmRespostaDto(empresaService.listarEmpresaPorId(id));

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaListagemIdDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar empresa existente", description = "Atualiza uma empresa da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "id": 1,
              "nome": "Restaurante Tauá - Lanches & Pastéis",
              "cnpj": "12.345.678/0001-95",
              "endereco": "Rua Pedro Mineiro, 262 - Paulista, São Paulo - SP",
              "dataCadastro": "2025-04-25",
              "ativo": true
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
    public ResponseEntity<EmpresaListagemDto> atualizar(
            @Parameter(description = "ID da empresa", example = "1", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Dados da empresa para atualização.", required = true)
            @Valid @RequestBody EmpresaAtualizarDto empresaParaAtualizar) {
        Empresa entidadeParaAtualizar = empresaService.atualizarEmpresa(id, EmpresaMapper.transformarEmEntidade(empresaParaAtualizar));

        EmpresaListagemDto respostaAtualizadaDto = EmpresaMapper.transformarEmRespostaDto(entidadeParaAtualizar);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaAtualizadaDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover empresa existente", description = "Remove uma empresa da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empresa removida com sucesso.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "A empresa não foi encontrada."
            }
            """)))
    })
    public ResponseEntity<EmpresaListagemDto> remover(
            @Parameter(description = "ID da empresa", example = "1", required = true)
            @PathVariable Integer id) {
        empresaService.removerEmpresa(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

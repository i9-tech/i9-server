package school.sptech.controller.plano;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.plano.dto.AlterarPlanoDto;
import school.sptech.controller.plano.dto.ContratarPlanoDto;
import school.sptech.controller.plano.dto.GerenciamentoPlanoMapper;
import school.sptech.controller.plano.dto.GerenciamentoPlanoResponse;
import school.sptech.entity.plano.GerenciamentoPlano;
import school.sptech.service.plano.GerenciamentoPlanoService;

@RestController
@RequestMapping("/gerenciamento-planos")
@Tag(name = "Gerenciamento de Planos", description = "Operações relacionadas ao gerenciamento de planos de assinatura das empresas.")
public class GerenciamentoPlanoController {

    private final GerenciamentoPlanoService gerenciamentoPlanoService;
    private final GerenciamentoPlanoMapper mapper;

    public GerenciamentoPlanoController(GerenciamentoPlanoService gerenciamentoPlanoService, GerenciamentoPlanoMapper mapper) {
        this.gerenciamentoPlanoService = gerenciamentoPlanoService;
        this.mapper = mapper;
    }

    @PostMapping("/contratar")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Contratar plano", description = "Permite que uma empresa contrate um plano de assinatura.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano contratado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou contrato não permitido")
    })
    public ResponseEntity<GerenciamentoPlanoResponse> contratar(@Valid @RequestBody ContratarPlanoDto contratarPlanoDto) {
        GerenciamentoPlano gerenciamentoPlano = gerenciamentoPlanoService.contratarPlano(contratarPlanoDto);
        return ResponseEntity.ok(mapper.toResponse(gerenciamentoPlano));
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Alterar plano", description = "Permite atualizar informações de um plano contratado por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<GerenciamentoPlanoResponse> alterarPlano(
            @PathVariable("id") Integer id,
            @Valid @RequestBody AlterarPlanoDto dto) {

        GerenciamentoPlano atualizado = gerenciamentoPlanoService.alterarPlano(id, dto);
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @PostMapping("/cancelar/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Cancelar plano", description = "Cancela o plano ativo de uma empresa pelo ID da empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plano cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado")
    })
    public ResponseEntity<Void> cancelar(@PathVariable Integer empresaId) {
        gerenciamentoPlanoService.cancelarPlano(empresaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empresa/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar plano por empresa", description = "Retorna o plano contratado de uma empresa pelo ID da empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado para a empresa")
    })
    public ResponseEntity<GerenciamentoPlanoResponse> buscarPorEmpresa(@PathVariable Integer empresaId) {
        return gerenciamentoPlanoService.buscarPorEmpresa(empresaId)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Renova plano por empresa",
            description = "Renova o plano ativo da empresa toda vez que efetua pagamento. Retorna o novo plano renovado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano renovado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Empresa não encontrada ou plano inválido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping("/renovar/{empresaId}")
    public ResponseEntity<GerenciamentoPlano> renovarPlano(@PathVariable Integer empresaId) {
        GerenciamentoPlano novoPlano = gerenciamentoPlanoService.renovarPlano(empresaId);
        return ResponseEntity.ok(novoPlano);
    }
}

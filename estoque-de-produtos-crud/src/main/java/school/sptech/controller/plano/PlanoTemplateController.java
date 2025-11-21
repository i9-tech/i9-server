package school.sptech.controller.plano;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.plano.dto.PlanoTemplateAtualizarDto;
import school.sptech.controller.plano.dto.PlanoTemplateCriacaoDto;
import school.sptech.controller.plano.dto.PlanoTemplateMapper;
import school.sptech.controller.plano.dto.PlanoTemplateResponse;
import school.sptech.entity.plano.PlanoTemplate;
import school.sptech.service.plano.PlanoTemplateService;

import java.util.List;

@RestController
@RequestMapping("/plano-templates")
@Tag(name = "Templates de Planos", description = "Operações relacionadas aos templates de planos de assinatura.")
public class PlanoTemplateController {

    private final PlanoTemplateService service;

    public PlanoTemplateController(PlanoTemplateService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar templates", description = "Retorna todos os templates de planos disponíveis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Templates listados com sucesso")
    })
    public ResponseEntity<List<PlanoTemplateResponse>> listar() {
        List<PlanoTemplate> entities = service.listarTodos();
        List<PlanoTemplateResponse> resp = PlanoTemplateMapper.toResponse(entities);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar template por ID", description = "Retorna um template de plano pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Template encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Template não encontrado")
    })
    public ResponseEntity<PlanoTemplateResponse> buscar(@PathVariable Integer id) {
        PlanoTemplate p = service.buscarPorId(id);
        return ResponseEntity.ok(PlanoTemplateMapper.toResponse(p));
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Criar template", description = "Cria um novo template de plano de assinatura.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Template criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<PlanoTemplateResponse> criar(@Valid @RequestBody PlanoTemplateCriacaoDto dto) {
        PlanoTemplate created = service.criar(dto);
        return ResponseEntity.status(201).body(PlanoTemplateMapper.toResponse(created));
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar template", description = "Atualiza um template existente de plano pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Template atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Template não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<PlanoTemplateResponse> atualizar(@PathVariable Integer id, @RequestBody PlanoTemplateAtualizarDto dto) {
        PlanoTemplate updated = service.atualizar(id, dto);
        return ResponseEntity.ok(PlanoTemplateMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Excluir template", description = "Remove um template de plano pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Template excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Template não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

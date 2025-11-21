package school.sptech.controller.plano;

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
public class GerenciamentoPlanoController {

    private final GerenciamentoPlanoService gerenciamentoPlanoService;

    private final GerenciamentoPlanoMapper mapper;

    public GerenciamentoPlanoController(GerenciamentoPlanoService gerenciamentoPlanoService, GerenciamentoPlanoMapper mapper) {
        this.gerenciamentoPlanoService = gerenciamentoPlanoService;
        this.mapper = mapper;
    }

    @PostMapping("/contratar")
    public ResponseEntity<GerenciamentoPlanoResponse> contratar(@Valid @RequestBody ContratarPlanoDto contratarPlanoDto) {
        GerenciamentoPlano gerenciamentoPlano = gerenciamentoPlanoService.contratarPlano(contratarPlanoDto);
        return ResponseEntity.ok(mapper.toResponse(gerenciamentoPlano));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GerenciamentoPlanoResponse> alterarPlano(
            @PathVariable("id") Integer id,
            @Valid @RequestBody AlterarPlanoDto dto) {

        GerenciamentoPlano atualizado = gerenciamentoPlanoService.alterarPlano(id, dto);
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @PostMapping("/cancelar/{empresaId}")
    public ResponseEntity<Void> cancelar(@PathVariable Integer empresaId) {
        gerenciamentoPlanoService.cancelarPlano(empresaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<GerenciamentoPlanoResponse> buscarPorEmpresa(@PathVariable Integer empresaId) {
        return gerenciamentoPlanoService.buscarPorEmpresa(empresaId)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}

package school.sptech.controller.plano;

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
public class PlanoTemplateController {

    private final PlanoTemplateService service;

    public PlanoTemplateController(PlanoTemplateService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PlanoTemplateResponse>> listar() {
        List<PlanoTemplate> entities = service.listarTodos();
        List<PlanoTemplateResponse> resp = PlanoTemplateMapper.toResponse(entities);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoTemplateResponse> buscar(@PathVariable Integer id) {
        PlanoTemplate p = service.buscarPorId(id);
        return ResponseEntity.ok(PlanoTemplateMapper.toResponse(p));
    }

    @PostMapping
    public ResponseEntity<PlanoTemplateResponse> criar(
            @Valid @RequestBody PlanoTemplateCriacaoDto dto) {

        PlanoTemplate created = service.criar(dto);
        return ResponseEntity.status(201).body(PlanoTemplateMapper.toResponse(created));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlanoTemplateResponse> atualizar(
            @PathVariable Integer id,
            @RequestBody PlanoTemplateAtualizarDto dto) {

        PlanoTemplate updated = service.atualizar(id, dto);
        return ResponseEntity.ok(PlanoTemplateMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

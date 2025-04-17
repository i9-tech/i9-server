package school.sptech.controller.prato;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.prato.dto.AtualizarPratoDto;
import school.sptech.controller.prato.dto.CadastroPratoDto;
import school.sptech.controller.prato.dto.PratoMapper;
import school.sptech.controller.prato.dto.RespostaPratoDto;
import school.sptech.service.prato.PratoService;

import java.util.List;

@RestController
@RequestMapping("/pratos")
public class PratoController {

    private final PratoService pratoService;

    public PratoController(PratoService pratoService) {
        this.pratoService = pratoService;
    }

    @GetMapping
    public ResponseEntity<List<RespostaPratoDto>> listarPratos() {
        return ResponseEntity
                .ok(PratoMapper
                        .toResponseDtoList(pratoService.listarPratos()));
    }

    @PostMapping
    public ResponseEntity<RespostaPratoDto> cadastrarPrato(
            @Valid @RequestBody CadastroPratoDto request
    ) {
        return ResponseEntity
                .ok(PratoMapper.toResponseDto
                        (pratoService.cadastrarPrato(
                                PratoMapper.toEntity(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaPratoDto> atualizarPrato(
            @Valid @RequestBody AtualizarPratoDto request,
            @PathVariable Integer id
    ) {
        return ResponseEntity
                .ok(PratoMapper.toResponseDto
                        (pratoService.atualizarPrato(
                                PratoMapper.toEntity(request, id))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPrato(
            @PathVariable Integer id
    ) {
        pratoService.deletarPrato(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<RespostaPratoDto>> buscarPratos(
            @RequestParam String nome
    ) {
        return ResponseEntity
                .ok(PratoMapper
                        .toResponseDtoList(pratoService.buscarPratosPorNome(nome)));
    }

}

package school.sptech.controller.prato;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.prato.dto.AtualizarPratoDto;
import school.sptech.controller.prato.dto.CadastroPratoDto;
import school.sptech.controller.prato.dto.PratoMapper;
import school.sptech.controller.prato.dto.RespostaPratoDto;
import school.sptech.controller.produto.dto.ProdutoListagemDto;
import school.sptech.service.prato.PratoService;

import java.util.List;

@RestController
@RequestMapping("/pratos")
@Tag(name = "Pratos", description = "Operações relacionadas aos pratos.")
public class PratoController {

    private final PratoService pratoService;

    public PratoController(PratoService pratoService) {
        this.pratoService = pratoService;
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<List<RespostaPratoDto>> listarPratos(
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(PratoMapper
                        .toResponseDtoList(pratoService.listarPratos(idFuncionario)));
    }

    @PostMapping("/{idFuncionario}")
    public ResponseEntity<RespostaPratoDto> cadastrarPrato(
            @Valid @RequestBody CadastroPratoDto request,
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(PratoMapper.toResponseDto
                        (pratoService.cadastrarPrato(
                                PratoMapper.toEntity(request), idFuncionario)));
    }

    @PutMapping("/{id}/{idFuncionario}")
    public ResponseEntity<RespostaPratoDto> atualizarPrato(
            @Valid @RequestBody AtualizarPratoDto request,
            @PathVariable Integer id,
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(PratoMapper.toResponseDto
                        (pratoService.atualizarPrato(
                                PratoMapper.toEntity(request),  id, idFuncionario)));
    }

    @DeleteMapping("/{id}/{idFuncionario}")
    public ResponseEntity<Void> deletarPrato(
            @PathVariable Integer id,
            @PathVariable Integer idFuncionario
    ) {
        pratoService.deletarPrato(id, idFuncionario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar/{idFuncionario}")
    public ResponseEntity<List<RespostaPratoDto>> buscarPratos(
            @RequestParam String nome,
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(PratoMapper
                        .toResponseDtoList(pratoService.buscarPratosPorNome(nome, idFuncionario)));
    }

    @GetMapping("/categoria/{idFuncionario}")
    public ResponseEntity<List<RespostaPratoDto>> buscarPratosPorCategoria(
            @RequestParam Integer categoriaId,
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(PratoMapper
                        .toResponseDtoList(pratoService.buscarPratosPorCategoria(categoriaId, idFuncionario)));
    }

    @GetMapping("/setor/{idFuncionario}")
    public ResponseEntity<List<RespostaPratoDto>> buscarPratosPorSetor(
            @RequestParam Integer setorId,
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(PratoMapper
                        .toResponseDtoList(pratoService.buscarPratosPorSetor(setorId, idFuncionario)));
    }

}

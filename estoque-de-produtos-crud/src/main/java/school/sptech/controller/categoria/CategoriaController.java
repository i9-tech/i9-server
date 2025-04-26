package school.sptech.controller.categoria;

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
public class CategoriaController {


    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping({"/{idFuncionario}"})
    public ResponseEntity<List<CategoriaListagemDto>> listagem(@PathVariable Integer idFuncionario) {
        List<Categoria> buscarCategoria = categoriaService.listarTodasCategorias(idFuncionario);
        if (buscarCategoria.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(CategoriaMapper.transformarEmRespostaListaDto(buscarCategoria));
    }

    @GetMapping("/{id}/{idFuncionario}")
    public ResponseEntity<CategoriaListagemDto> listagemId(@PathVariable Integer idCategoria, @PathVariable Integer idFuncionario) {
        Optional<Categoria> categoriaEncontrado = categoriaService.buscarCategoriaPorId(idCategoria, idFuncionario);

        if (categoriaEncontrado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        CategoriaListagemDto respostaListagemIdDto = CategoriaMapper.transformarEmRespostaDto(categoriaEncontrado.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaListagemIdDto);
    }

    @PostMapping("/{idFuncionario}")
    public ResponseEntity<CategoriaListagemDto> cadastrar(@Valid @RequestBody CategoriaCadastroDto categoriaParaCadastro, @PathVariable Integer idFuncionario) {
        Categoria novaCategoria = categoriaService.cadastrarCategoria(CategoriaMapper.transformarEmEntidade(categoriaParaCadastro), idFuncionario);

        CategoriaListagemDto respostaCategoriaDto = CategoriaMapper.transformarEmRespostaDto(novaCategoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaCategoriaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaListagemDto> atualizar(@PathVariable Integer id, @Valid @RequestBody CategoriaAtualizarDto categoriaParaAtualizar) {
        Categoria entidadeParaAtualizar = categoriaService.atualizarCategoria(id, CategoriaMapper.transformarEmEntidade(categoriaParaAtualizar));

        CategoriaListagemDto respostaAtualizadaDto = CategoriaMapper.transformarEmRespostaDto(entidadeParaAtualizar);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaAtualizadaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoriaListagemDto> remover(@PathVariable Integer id) {
        categoriaService.removerCategoria(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
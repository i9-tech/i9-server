package school.sptech.controller.produto;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.produto.dto.ProdutoRequestDto;
import school.sptech.controller.produto.dto.ProdutoResponseDto;
import school.sptech.service.produto.ProdutoService;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping("/{idFuncionario}")
    public ResponseEntity<ProdutoResponseDto> cadastrar(@Valid @RequestBody ProdutoRequestDto produtoParaCadastrar, @PathVariable Integer idFuncionario) {
        ProdutoResponseDto produtoCadastrado = service.cadastrarProduto(produtoParaCadastrar, idFuncionario);
        return ResponseEntity.status(201).body(produtoCadastrado);
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<List<ProdutoResponseDto>> listarProduto(@PathVariable Integer idFuncionario) {
        List<ProdutoResponseDto> responseDto = service.listarProdutoPorEmpresa(idFuncionario);
        return ResponseEntity.status(200).body(responseDto);
    }

    @PatchMapping("/{id}/{idFuncionario}")
    public ResponseEntity<ProdutoResponseDto> editarProduto(@PathVariable Integer id, @Valid @RequestBody ProdutoRequestDto produtoParaEditar, @PathVariable Integer idFuncionario) {
        ProdutoResponseDto responseDto = service.editarProduto(id, idFuncionario, produtoParaEditar);
        return ResponseEntity.status(200).body(responseDto);
    }

    @DeleteMapping("/{id}/{idFuncionario}")
    public ResponseEntity<Void> removerProduto(@PathVariable Integer id, @PathVariable Integer idFuncionario) {
        service.removerPorId(id, idFuncionario);
        return ResponseEntity.noContent().build();
    }

}
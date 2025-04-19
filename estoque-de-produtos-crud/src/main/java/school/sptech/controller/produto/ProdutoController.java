package school.sptech.controller.produto;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.produto.dto.ProdutoCadastroDto;
import school.sptech.controller.produto.dto.ProdutoEdicaoDto;
import school.sptech.controller.produto.dto.ProdutoListagemDto;
import school.sptech.service.produto.ProdutoService;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping("/{idFuncionario}")
    public ResponseEntity<ProdutoListagemDto> cadastrar(@Valid @RequestBody ProdutoCadastroDto produtoParaCadastrar, @PathVariable Integer idFuncionario) {
        ProdutoListagemDto produtoCadastrado = service.cadastrarProduto(produtoParaCadastrar, idFuncionario);
        return ResponseEntity.status(201).body(produtoCadastrado);
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<List<ProdutoListagemDto>> listarProduto(@PathVariable Integer idFuncionario) {
        List<ProdutoListagemDto> responseDto = service.listarProdutoPorEmpresa(idFuncionario);
        return ResponseEntity.status(200).body(responseDto);
    }

    @PatchMapping("/{id}/{idFuncionario}")
    public ResponseEntity<ProdutoListagemDto> editarProduto(@PathVariable Integer id, @Valid @RequestBody ProdutoEdicaoDto produtoParaEditar, @PathVariable Integer idFuncionario) {
        ProdutoListagemDto responseDto = service.editarProduto(id, idFuncionario, produtoParaEditar);
        return ResponseEntity.status(200).body(responseDto);
    }

    @DeleteMapping("/{id}/{idFuncionario}")
    public ResponseEntity<Void> removerProduto(@PathVariable Integer id, @PathVariable Integer idFuncionario) {
        service.removerPorId(id, idFuncionario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/valor-total-estoque/{idFuncionario}")
    public ResponseEntity<Double> valorTotalEstoque(@PathVariable Integer idFuncionario) {
        Double valorTotal = service.valorTotalEstoqueProduto(idFuncionario);
        return ResponseEntity.status(200).body(valorTotal);
    }

    @GetMapping("/lucro-previsto-estoque/{idFuncionario}")
    public ResponseEntity<Double> lucroPrevistoEstoque(@PathVariable Integer idFuncionario) {
        Double valorTotal = service.lucroPrevistoEstoqueProduto(idFuncionario);
        return ResponseEntity.status(200).body(valorTotal);
    }

    @GetMapping("/quantidade-estoque/{idFuncionario}")
    public ResponseEntity<Integer> quantidadeProdutosEstoque(@PathVariable Integer idFuncionario) {
        Integer valorTotal = service.quantidadeProdutoEstoque(idFuncionario);
        return ResponseEntity.status(200).body(valorTotal);
    }

    @GetMapping("/quantidade-estoque-baixo/{idFuncionario}")
    public ResponseEntity<Integer> quantidadeProdutosEstoqueBaixo(@PathVariable Integer idFuncionario) {
        Integer quantidadeProdutos = service.quantidadeProdutoEstoqueBaixo(idFuncionario);
        return ResponseEntity.status(200).body(quantidadeProdutos);
    }

    @GetMapping("/quantidade-estoque-alto/{idFuncionario}")
    public ResponseEntity<Integer> quantidadeProdutosEstoqueAlto(@PathVariable Integer idFuncionario) {
        Integer quantidadeProdutos = service.quantidadeProdutoEstoqueAlto(idFuncionario);
        return ResponseEntity.status(200).body(quantidadeProdutos);
    }

    @GetMapping("/categoria/{categoria}/{idFuncionario}")
    public ResponseEntity<List<ProdutoListagemDto>> listarProdutosPorCategoria(@PathVariable String categoria, @PathVariable Integer idFuncionario) {
        List<ProdutoListagemDto> responseDto = service.listarProdutoPorCategoriaEmpresa(categoria, idFuncionario);
        return ResponseEntity.status(200).body(responseDto);
    }

    @GetMapping("/setor/{setor}/{idFuncionario}")
    public ResponseEntity<List<ProdutoListagemDto>> listarProdutosPorSetor(@PathVariable String setor, @PathVariable Integer idFuncionario) {
        List<ProdutoListagemDto> responseDto = service.listarProdutoPorSetorEmpresa(setor, idFuncionario);
        return ResponseEntity.status(200).body(responseDto);
    }

    @GetMapping("/nome/{nome}/{idFuncionario}")
    public ResponseEntity<List<ProdutoListagemDto>> listarProdutosPorNome(@PathVariable String nome, @PathVariable Integer idFuncionario) {
        List<ProdutoListagemDto> responseDto = service.listarProdutoPorNomeEmpresa(nome, idFuncionario);
        return ResponseEntity.status(200).body(responseDto);
    }

}
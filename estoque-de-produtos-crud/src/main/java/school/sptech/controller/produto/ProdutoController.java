package school.sptech.controller.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.entity.produto.Produto;
import school.sptech.service.produto.ProdutoService;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produtoParaCadastrar, @PathVariable int fkEmpresa) {
        Produto produtoCadastrado = service.cadastrarProduto(produtoParaCadastrar, fkEmpresa);
        return ResponseEntity.status(201).body(produtoCadastrado);
    }

    @GetMapping("/{fkEmpresa}")
    public ResponseEntity<List<Produto>> listarPorEmpresa(@PathVariable int fkEmpresa) {
        List<Produto> todosProdutos = service.listarPorEmpresa(fkEmpresa);
        return ResponseEntity.status(200).body(todosProdutos);
    }

    @PatchMapping("/{id}/{fkEmpresa}")
    private ResponseEntity<Produto> editarProduto(@PathVariable int id, @RequestBody Produto produtoParaEditar, @PathVariable int fkEmpresa) {
        Produto produtoEditado = service.editarProduto(id, produtoParaEditar, fkEmpresa);
        return ResponseEntity.status(200).body(produtoEditado);
    }

    @DeleteMapping("/{id}/{fkEmpresa}")
    public ResponseEntity<Void> removerPorId(@PathVariable int id, @PathVariable int fkEmpresa) {
        service.removerPorId(id, fkEmpresa);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/nome/{fkEmpresa}")
    public ResponseEntity<List<Produto>> listarPorNome(@RequestParam String nomeProduto, @PathVariable int fkEmpresa) {
        List<Produto> produtosPorNome = service.listarPorNome(nomeProduto, fkEmpresa);
        return ResponseEntity.status(200).body(produtosPorNome);
    }

    @GetMapping("/categoria/{fkEmpresa}")
    public ResponseEntity<List<Produto>> listarPorCategoria(@RequestParam String categoriaProduto,  @PathVariable int fkEmpresa) {
        List<Produto> produtosCategoria = service.listarPorCategoria(categoriaProduto, fkEmpresa);
        return ResponseEntity.status(200).body(produtosCategoria);
    }

    @GetMapping("/setor/{fkEmpresa}")
    public ResponseEntity<List<Produto>> listarPorSetor(@RequestParam String setorAlimenticio,  @PathVariable int fkEmpresa) {
        List<Produto> produtosSetor = service.listarPorSetor(setorAlimenticio, fkEmpresa);
        return ResponseEntity.status(200).body(produtosSetor);
    }

    @GetMapping("/estoque-baixo/{fkEmpresa}")
    public ResponseEntity<List<Produto>> listarEstoqueBaixo(@PathVariable int fkEmpresa) {
        List<Produto> produtosEstoqueBaixo = service.listarEstoqueBaixo(fkEmpresa);
        return ResponseEntity.status(200).body(produtosEstoqueBaixo);
    }

    @GetMapping("/estoque-alto/{fkEmpresa}")
    public ResponseEntity<List<Produto>> listarEstoqueAlto(@PathVariable int fkEmpresa) {
        List<Produto> produtosEstoqueAlto = service.listarEstoqueAlto(fkEmpresa);
        return ResponseEntity.status(200).body(produtosEstoqueAlto);
    }

}
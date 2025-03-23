package school.sptech.controller.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.entity.produto.Produto;
import school.sptech.repository.produto.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @PostMapping
    private ResponseEntity<Produto> cadastrar(@RequestBody Produto produto, @RequestParam int fkEmpresa) {
        if (produto.getFkEmpresa() != fkEmpresa) {
            return ResponseEntity.status(403).build();
        }

        Produto produtoRegistrado = this.repository.save(produto);
        return ResponseEntity.status(201).body(produtoRegistrado);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar(@RequestParam int fkEmpresa) {
        List<Produto> todosProduto = repository.findByFkEmpresa(fkEmpresa);

        if (todosProduto.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(todosProduto);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<Produto> editar(@PathVariable int id, @RequestBody Produto produtoParaEditar, @RequestParam int fkEmpresa) {
        Optional<Produto> produtoExistente = repository.findById(id);

        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();

            if (produto.getFkEmpresa() != fkEmpresa) {
                return ResponseEntity.status(403).build();
            }

            produtoParaEditar.setId(id);
            produtoParaEditar.setFkEmpresa(fkEmpresa);

            Produto produtoEditado = repository.save(produtoParaEditar);
            return ResponseEntity.status(200).body(produtoEditado);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletar(@PathVariable int id, @RequestParam int fkEmpresa) {
        Optional<Produto> produtoExistente = repository.findById(id);

        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();

            if (produto.getFkEmpresa() != fkEmpresa) {
                return ResponseEntity.status(403).build();
            }

            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam String nomeProduto, @RequestParam int fkEmpresa) {
        List<Produto> produtosNome = repository.findByNomeProdutoContainingIgnoreCaseAndFkEmpresa(nomeProduto, fkEmpresa);

        if (produtosNome.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtosNome);
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<Produto>> buscarPorCategoria(@RequestParam String categoria,  @RequestParam int fkEmpresa) {
        List<Produto> produtosCategoria = repository.findByCategoriaContainingIgnoreCaseAndFkEmpresa(categoria, fkEmpresa);

        if (produtosCategoria.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtosCategoria);
    }

    @GetMapping("/setor")
    public ResponseEntity<List<Produto>> buscarPorSetor(@RequestParam String setorAlimenticio,  @RequestParam int fkEmpresa) {

        List<Produto> produtosSetor = repository.findBySetorAlimenticioContainingIgnoreCaseAndFkEmpresa(setorAlimenticio, fkEmpresa);

        if (produtosSetor.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtosSetor);
    }

    @GetMapping("/estoque-baixo")
    public ResponseEntity<List<Produto>> buscarEstoqueBaixo(@RequestParam int fkEmpresa) {
        List<Produto> produtos = repository.findByFkEmpresa(fkEmpresa);
        List<Produto> produtosEstoqueBaixo = new ArrayList<>();

        for (int i = 0; i < produtos.size(); i++) {
            Produto produtodaVez = produtos.get(i);
            if (produtodaVez.getQuantidade() < produtodaVez.getQuantidadeMin()) {
                produtosEstoqueBaixo.add(produtodaVez);
            }
        }

        if (produtosEstoqueBaixo.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtosEstoqueBaixo);
    }

    @GetMapping("/estoque-alto")
    public ResponseEntity<List<Produto>> buscarEstoqueAlto(@RequestParam int fkEmpresa) {
        List<Produto> produtos = repository.findByFkEmpresa(fkEmpresa);
        List<Produto> produtosEstoqueAlto = new ArrayList<>();

        for (int i = 0; i < produtos.size(); i++) {
            Produto produtodaVez = produtos.get(i);
            if (produtodaVez.getQuantidade() > produtodaVez.getQuantidadeMax()) {
                produtosEstoqueAlto.add(produtodaVez);
            }
        }

        if (produtosEstoqueAlto.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtosEstoqueAlto);
    }

}
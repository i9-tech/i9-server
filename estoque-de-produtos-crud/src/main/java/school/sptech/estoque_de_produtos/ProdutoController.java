package school.sptech.estoque_de_produtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @PostMapping
    private ResponseEntity<Produto> cadastrar(@RequestBody Produto produto) {
        Produto produtoRegistrado = this.repository.save(produto);
        return ResponseEntity.status(201).body(produtoRegistrado);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        List<Produto> todosProdutos = repository.findAll();

        if (todosProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(todosProdutos);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Produto> editar(@PathVariable int id, @RequestBody Produto produtoParaEditar) {
        if (repository.existsById(id)) {
            produtoParaEditar.setId(id);
            Produto produtoEditado = repository.save(produtoParaEditar);
            return ResponseEntity.status(200).body(produtoEditado);
        }
        return ResponseEntity.status(404).build();
    }


    @DeleteMapping("/{id}")
    private ResponseEntity<Produto> deletar(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam String nomeProduto) {

        List<Produto> produtosNome = repository.findByNomeProdutoContainingIgnoreCase(nomeProduto);

        if (produtosNome.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtosNome);
    }


    @GetMapping("/categoria")
    public ResponseEntity<List<Produto>> buscarPorCategoria(@RequestParam String categoria) {

        List<Produto> produtosCategoria = repository.findByCategoriaContainingIgnoreCase(categoria);

        if (produtosCategoria.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtosCategoria);
    }

    @GetMapping("/setor")
    public ResponseEntity<List<Produto>> buscarPorSetor(@RequestParam String setorAlimenticio) {

        List<Produto> produtosSetor = repository.findBySetorAlimenticioContainingIgnoreCase(setorAlimenticio);

        if (produtosSetor.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtosSetor);
    }

    @GetMapping("/estoque-baixo")
    public ResponseEntity<List<Produto>> buscarEstoqueBaixo() {
        List<Produto> produtos = repository.findAll();
        List<Produto> produtosEstoqueBaixo = new ArrayList<>();

        for (int i = 0; i < produtos.size() ; i++) {
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

    @GetMapping("/data-validade/data-recente")
    public ResponseEntity<Produto> produtoProximoValidade(@RequestParam LocalDate dataValidade, LocalDate dataRecente) {
        Optional<Produto> maisRecente = repository.findTop3ByOrderByDesc();
        return ResponseEntity.of(maisRecente);
    }
}
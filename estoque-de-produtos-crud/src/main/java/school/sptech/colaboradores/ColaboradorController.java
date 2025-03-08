package school.sptech.colaboradores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.estoque_de_produtos.Produto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {

    @Autowired
    private ColaboradorRepository repository;

    @PostMapping
    private ResponseEntity<Colaborador> cadastrar(@RequestBody Colaborador colaborador) {
        if(repository.existsById(colaborador.getId())) {
            return ResponseEntity.status(409).build();
        }
        colaborador.gerarSenha();
        Colaborador colaboradorRegistrado = this.repository.save(colaborador);
        return ResponseEntity.status(201).body(colaboradorRegistrado);
    }

    @GetMapping()
    public ResponseEntity<List<Colaborador>> listarPorEmpresa(@RequestParam int fkEmpresa) {
        List<Colaborador> todosColaboradoresEmpresa = repository.findByFkEmpresa(fkEmpresa);

        if (todosColaboradoresEmpresa.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(todosColaboradoresEmpresa);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<Colaborador> editar(@PathVariable int id, @RequestBody Colaborador colaborarParaEditar) {
        if (repository.existsById(id)) {
            colaborarParaEditar.gerarSenha();
            colaborarParaEditar.setId(id);
            Colaborador colaboradorEditado = repository.save(colaborarParaEditar);
            return ResponseEntity.status(200).body(colaboradorEditado);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Colaborador> deletar(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/colaborador/{id}")
    public ResponseEntity<Colaborador> buscarPorIdColaborador(@PathVariable int id) {
        Optional<Colaborador> colaborador = repository.findById(id);

        if (colaborador.isPresent()) {
            return ResponseEntity.status(200).body(colaborador.get());
        }
        return ResponseEntity.status(404).build();
    }

}

package school.sptech.controller.colaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.repository.colaborador.ColaboradorRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {

    @Autowired
    private ColaboradorRepository repository;

    @PostMapping
    private ResponseEntity<Funcionario> cadastrar(@RequestBody Funcionario funcionario) {
        if(repository.existsById(funcionario.getId())) {
            return ResponseEntity.status(409).build();
        }
        funcionario.gerarSenha();
        Funcionario funcionarioRegistrado = this.repository.save(funcionario);
        return ResponseEntity.status(201).body(funcionarioRegistrado);
    }

    @GetMapping()
    public ResponseEntity<List<Funcionario>> listarPorEmpresa(@RequestParam int fkEmpresa) {
        List<Funcionario> todosColaboradoresEmpresa = repository.findByFkEmpresa(fkEmpresa);

        if (todosColaboradoresEmpresa.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(todosColaboradoresEmpresa);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<Funcionario> editar(@PathVariable int id, @RequestBody Funcionario colaborarParaEditar) {
        if (repository.existsById(id)) {
            colaborarParaEditar.gerarSenha();
            colaborarParaEditar.setId(id);
            Funcionario funcionarioEditado = repository.save(colaborarParaEditar);
            return ResponseEntity.status(200).body(funcionarioEditado);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Funcionario> deletar(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/colaborador/{id}")
    public ResponseEntity<Funcionario> buscarPorIdColaborador(@PathVariable int id) {
        Optional<Funcionario> colaborador = repository.findById(id);

        if (colaborador.isPresent()) {
            return ResponseEntity.status(200).body(colaborador.get());
        }
        return ResponseEntity.status(404).build();
    }

}

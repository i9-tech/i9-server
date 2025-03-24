package school.sptech.controller.colaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.service.funcionario.FuncionarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {

    @Autowired
    private FuncionarioService service;

//    @PostMapping
//    private ResponseEntity<Funcionario> cadastrar(@RequestBody Funcionario funcionario) {
//        if(repository.existsById(funcionario.getId())) {
//            return ResponseEntity.status(409).build();
//        }
//        funcionario.gerarSenha();
//        Funcionario funcionarioRegistrado = this.repository.save(funcionario);
//        return ResponseEntity.status(201).body(funcionarioRegistrado);
//    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarPorEmpresa(@RequestParam int fkEmpresa) {

        List<Funcionario> todosColaboradoresEmpresa = service.listarPorEmpresa(fkEmpresa);
        return ResponseEntity.status(200).body(todosColaboradoresEmpresa);

    }

    @GetMapping("/colaborador/{id}")
    public ResponseEntity<Funcionario> buscarFuncionarioPorId(@PathVariable int id) {

        return ResponseEntity.status(200).body(service.buscarFuncionarioPorId(id));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Funcionario> removerPorId(@PathVariable int id) {

        service.removerPorId(id);
        return ResponseEntity.status(204).build();
    }


//    @PatchMapping("/{id}")
//    private ResponseEntity<Funcionario> editar(@PathVariable int id, @RequestBody Funcionario colaborarParaEditar) {
//        if (repository.existsById(id)) {
//            colaborarParaEditar.gerarSenha();
//            colaborarParaEditar.setId(id);
//            Funcionario funcionarioEditado = repository.save(colaborarParaEditar);
//            return ResponseEntity.status(200).body(funcionarioEditado);
//        }
//        return ResponseEntity.status(404).build();
//    }
//



}

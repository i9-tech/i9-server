package school.sptech.controller.colaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.produto.Produto;
import school.sptech.service.funcionario.FuncionarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/colaboradores")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @PostMapping("/{fkEmpresa}")
    public ResponseEntity<Funcionario> cadastrar(@RequestBody Funcionario funcionarioParaCadastro, @PathVariable int fkEmpresa) {
        funcionarioParaCadastro.gerarSenha();
        Funcionario funcionarioCadastrado = service.cadastrarFuncionario(funcionarioParaCadastro, fkEmpresa);
        return ResponseEntity.status(201).body(funcionarioCadastrado);
    }

    @GetMapping("/{fkEmpresa}")
    public ResponseEntity<List<Funcionario>> listarPorEmpresa(@PathVariable int fkEmpresa) {
        List<Funcionario> todosColaboradoresEmpresa = service.listarPorEmpresa(fkEmpresa);
        return ResponseEntity.status(200).body(todosColaboradoresEmpresa);
    }

    @GetMapping("/colaborador/{id}/{fkEmpresa}")
    public ResponseEntity<Optional<Funcionario>> buscarFuncionarioPorId(@PathVariable int id, @PathVariable int fkEmpresa) {
        Optional<Funcionario> funcionarioPorId  = service.buscarFuncionarioPorId(id, fkEmpresa);
        return ResponseEntity.status(200).body(funcionarioPorId);
    }

    @DeleteMapping("/{id}/{fkEmpresa}")
    public ResponseEntity<Funcionario> removerPorId(@PathVariable int id, @PathVariable int fkEmpresa) {
        service.removerPorId(id, fkEmpresa);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{id}/{fkEmpresa}")
    private ResponseEntity<Funcionario> editarFuncionario(@PathVariable int id, @RequestBody Funcionario funcionarioParaEditar, @PathVariable int fkEmpresa) {
        Funcionario funcionarioEditado = service.editarFuncionario(id, fkEmpresa, funcionarioParaEditar);
        return ResponseEntity.status(200).body(funcionarioEditado);
    }

}

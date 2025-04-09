package school.sptech.controller.funcionario;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.funcionario.dto.FuncionarioRequestDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.service.funcionario.FuncionarioService;

import java.util.List;

@RestController
@RequestMapping("/colaboradores")
@Tag(name = "Funcionários", description = "Operações relacionadas aos funcionários")
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @PostMapping("/{fkEmpresa}")
    public ResponseEntity<FuncionarioResponseDto> cadastrar(@RequestBody FuncionarioRequestDto requestDto, @PathVariable int fkEmpresa) {
        FuncionarioResponseDto responseDto = service.cadastrarFuncionario(requestDto, fkEmpresa);
        return ResponseEntity.status(201).body(responseDto);
    }

    @GetMapping("/{fkEmpresa}")
    public ResponseEntity<List<FuncionarioResponseDto>> listarPorEmpresa(@PathVariable int fkEmpresa) {
        List<FuncionarioResponseDto> responseDto = service.listarPorEmpresa(fkEmpresa);
        return ResponseEntity.status(200).body(responseDto);
    }

    @GetMapping("/{id}/{fkEmpresa}")
    public ResponseEntity<FuncionarioResponseDto> buscarFuncionarioPorId(@PathVariable int id, @PathVariable int fkEmpresa) {
        FuncionarioResponseDto responseDto = service.buscarFuncionarioPorId(id, fkEmpresa);
        return ResponseEntity.status(200).body(responseDto);
    }

    @DeleteMapping("/{id}/{fkEmpresa}")
    public ResponseEntity<Void> removerPorId(@PathVariable int id, @PathVariable int fkEmpresa) {
        service.removerPorId(id, fkEmpresa);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/{fkEmpresa}")
    public ResponseEntity<FuncionarioResponseDto> editarFuncionario(@PathVariable int id, @RequestBody FuncionarioRequestDto funcionarioParaEditar, @PathVariable int fkEmpresa) {
        FuncionarioResponseDto responseDto = service.editarFuncionario(id, fkEmpresa, funcionarioParaEditar);
        return ResponseEntity.status(200).body(responseDto);
    }




}

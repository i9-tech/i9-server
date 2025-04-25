package school.sptech.controller.funcionario;


import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.funcionario.dto.FuncionarioRequestDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;

import school.sptech.service.funcionario.FuncionarioService;

import java.util.List;

//import java.util.List;

@RestController
@RequestMapping("/colaboradores")
@Tag(name = "Funcionários", description = "Operações relacionadas aos funcionários")
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @PostMapping("/{idEmpresa}")
    public ResponseEntity<FuncionarioResponseDto> cadastrar(@Valid @RequestBody FuncionarioRequestDto requestDto, @PathVariable Integer idEmpresa) {
        FuncionarioResponseDto responseDto = service.cadastrarFuncionario(requestDto, idEmpresa);
        return ResponseEntity.status(201).body(responseDto);
    }

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<List<FuncionarioResponseDto>> listarPorEmpresa(@PathVariable Integer idEmpresa) {
        List<FuncionarioResponseDto> responseDto = service.listarPorEmpresa(idEmpresa);
        return ResponseEntity.status(200).body(responseDto);
    }

    @GetMapping("/{id}/{idEmpresa}")
    public ResponseEntity<FuncionarioResponseDto> buscarFuncionarioPorId(@PathVariable int id, @PathVariable Integer idEmpresa) {
        FuncionarioResponseDto responseDto = service.buscarFuncionarioPorId(id, idEmpresa);
        return ResponseEntity.status(200).body(responseDto);
    }

    @DeleteMapping("/{id}/{idEmpresa}")
    public ResponseEntity<Void> removerPorId(@PathVariable int id, @PathVariable Integer idEmpresa) {
        service.removerPorId(id, idEmpresa);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/{idEmpresa}")
    public ResponseEntity<FuncionarioResponseDto> editarFuncionario(@PathVariable int id, @Valid @RequestBody FuncionarioRequestDto funcionarioParaEditar, @PathVariable Integer idEmpresa) {
        FuncionarioResponseDto responseDto = service.editarFuncionario(id, idEmpresa, funcionarioParaEditar);
        return ResponseEntity.status(200).body(responseDto);
    }
}

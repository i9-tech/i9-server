package school.sptech.controller.funcionario;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.funcionario.dto.FuncionarioRequestDto;
import school.sptech.controller.funcionario.dto.FuncionarioResponseDto;
import school.sptech.entity.empresa.Empresa;
import school.sptech.service.funcionario.FuncionarioService;

import java.util.List;

@RestController
@RequestMapping("/empresas/{fkEmpresa}/colaboradores")
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponseDto> cadastrar(@Valid @RequestBody FuncionarioRequestDto requestDto, @PathVariable  Empresa empresa) {
        FuncionarioResponseDto responseDto = service.cadastrarFuncionario(requestDto, empresa);
        return ResponseEntity.status(201).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDto>> listarPorEmpresa(@PathVariable  Empresa empresa) {
        List<FuncionarioResponseDto> responseDto = service.listarPorEmpresa(empresa);
        return ResponseEntity.status(200).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDto> buscarFuncionarioPorId(@PathVariable int id, @PathVariable Empresa empresa) {
        FuncionarioResponseDto responseDto = service.buscarFuncionarioPorId(id, empresa);
        return ResponseEntity.status(200).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable int id, @PathVariable Empresa empresa) {
        service.removerPorId(id, empresa);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDto> editarFuncionario(@PathVariable int id, @Valid @RequestBody FuncionarioRequestDto funcionarioParaEditar, @PathVariable Empresa empresa) {
        FuncionarioResponseDto responseDto = service.editarFuncionario(id, empresa, funcionarioParaEditar);
        return ResponseEntity.status(200).body(responseDto);
    }

}

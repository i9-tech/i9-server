package school.sptech.controller.empresa;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.empresa.dto.EmpresaAtualizarDto;
import school.sptech.controller.empresa.dto.EmpresaListagemDto;
import school.sptech.controller.empresa.dto.EmpresaMapper;
import school.sptech.controller.empresa.dto.EmpresaCadastroDto;
import school.sptech.entity.empresa.Empresa;
import school.sptech.service.empresa.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity<EmpresaListagemDto> empresa(@Valid @RequestBody EmpresaCadastroDto empresaCadastroDto) {
        Empresa novaEmpresa = empresaService.cadastrarEmpresa(EmpresaMapper.transformarEmEntidade(empresaCadastroDto));

        EmpresaListagemDto respostaEmpresaDto = EmpresaMapper.transformarEmRespostaDto(novaEmpresa);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaEmpresaDto);
    }

    @GetMapping
    public ResponseEntity<List<EmpresaListagemDto>> listagem() {

        List<EmpresaListagemDto> respostaListagemIdDto = EmpresaMapper.transformarEmRespostaDtoList(empresaService.listarEmpresa());

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaListagemIdDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaListagemDto> listagemPorId(@PathVariable Integer id) {

        EmpresaListagemDto respostaListagemIdDto = EmpresaMapper.transformarEmRespostaDto(empresaService.listarEmpresaPorId(id));

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaListagemIdDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaListagemDto> atualizar(@PathVariable Integer id, @Valid @RequestBody EmpresaAtualizarDto empresaParaAtualizar) {
        Empresa entidadeParaAtualizar = empresaService.atualizarEmpresa(id, EmpresaMapper.transformarEmEntidade(empresaParaAtualizar));

        EmpresaListagemDto respostaAtualizadaDto = EmpresaMapper.transformarEmRespostaDto(entidadeParaAtualizar);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaAtualizadaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmpresaListagemDto> remover(@PathVariable Integer id) {
        empresaService.removerEmpresa(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

package school.sptech.controller.setor;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.setor.dto.SetorAtualizarDto;
import school.sptech.controller.setor.dto.SetorCadastroDto;
import school.sptech.controller.setor.dto.SetorListagemDto;
import school.sptech.controller.setor.dto.SetorMapper;
import school.sptech.entity.setor.Setor;
import school.sptech.service.setor.SetorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/setores")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<List<SetorListagemDto>> listagem(@PathVariable Integer idFuncionario) {
        List<Setor> buscarSetor = setorService.listarTodosSetores(idFuncionario);
        if (buscarSetor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(SetorMapper.transformarEmListaRespostaDto(buscarSetor));
    }

    @GetMapping("/{idSetor}/{idFuncionario}")
    public ResponseEntity<SetorListagemDto> listagemId(@PathVariable Integer idSetor, @PathVariable Integer idFuncionario) {
        Optional<Setor> setorEncontrado = setorService.buscarSetorPorId(idSetor, idFuncionario);

        if (setorEncontrado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        SetorListagemDto respostaListagemIdDto = SetorMapper.transformarEmRespostaDto(setorEncontrado.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaListagemIdDto);
    }

    @PostMapping("/{idFuncionario}")
    public ResponseEntity<SetorListagemDto> cadastrar(@PathVariable Integer idFuncionario, @Valid @RequestBody SetorCadastroDto setorParaCadastro) {
        Setor novoSetor = setorService.cadastrarSetor(SetorMapper.transformarEmEntidade(setorParaCadastro), idFuncionario);

        SetorListagemDto respostaSetorDto = SetorMapper.transformarEmRespostaDto(novoSetor);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaSetorDto);
    }

    @PutMapping("/{idSetor}")
    public ResponseEntity<SetorListagemDto> atualizar(@PathVariable Integer idSetor, @Valid @RequestBody SetorAtualizarDto setorParaAtualizar) {
        Setor entidadeParaAtualizar = setorService.atualizarSetor(idSetor, SetorMapper.transformarEmEntidade(setorParaAtualizar));

        SetorListagemDto repsostaAtualizadaDto = SetorMapper.transformarEmRespostaDto(entidadeParaAtualizar);

        return ResponseEntity.status(HttpStatus.CREATED).body(repsostaAtualizadaDto);
    }

    @DeleteMapping("/{idSetor}")
    public ResponseEntity<SetorListagemDto> remover(@PathVariable Integer idSetor) {
        setorService.removerSetor(idSetor);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

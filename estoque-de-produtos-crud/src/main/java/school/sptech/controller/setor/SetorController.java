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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/setores")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @GetMapping()
    public ResponseEntity<SetorListagemDto> listagem() {
        List<Setor> buscarSetor = setorService.listarTodosSetores();
        if (buscarSetor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<SetorListagemDto> respostaSetorDto = new ArrayList<>();

        for (int i = 0; i < buscarSetor.size(); i++) {
            respostaSetorDto.add(SetorMapper.transformarEmRespostaDto(buscarSetor.get(i)));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body((SetorListagemDto) respostaSetorDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SetorListagemDto> listagemId(@PathVariable Integer id) {
        Optional<Setor> setorEncontrado = setorService.buscarSetorPorId(id);

        if (setorEncontrado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        SetorListagemDto respostaListagemIdDto = SetorMapper.transformarEmRespostaDto(setorEncontrado.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaListagemIdDto);
    }

    @PostMapping
    public ResponseEntity<SetorListagemDto> cadastrar(@Valid @RequestBody SetorCadastroDto setorParaCadastro) {
        Setor novoSetor = setorService.cadastrarSetor(SetorMapper.transformarEmRespostaDto(setorParaCadastro));

        SetorListagemDto respostaSetorDto = SetorMapper.transformarEmRespostaDto(novoSetor);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaSetorDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorListagemDto> atualizar(@PathVariable Integer id, @Valid @RequestBody SetorAtualizarDto setorParaAtualizar) {
        Setor entidadeParaAtualizar = setorService.atualizarSetor(id, SetorMapper.transformarEmEntidade(setorParaAtualizar));

        SetorListagemDto repsostaAtualizadaDto = SetorMapper.transformarEmRespostaDto(entidadeParaAtualizar);

        return ResponseEntity.status(HttpStatus.CREATED).body(repsostaAtualizadaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SetorListagemDto> remover(@PathVariable Integer id) {
        setorService.removerSetor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

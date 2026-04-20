package school.sptech.controller.mensagemIA;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.mensagemIA.dto.MensagemIAEnvioDto;
import school.sptech.controller.mensagemIA.dto.MensagemMapper;
import school.sptech.service.mensagemIA.MensagemIAService;

import java.util.List;

@RestController
@RequestMapping("/mensagens-ia")
public class MensagemIAController {

    private final MensagemIAService service;

    public MensagemIAController(MensagemIAService service) {
        this.service = service;
    }

    @PostMapping("/{idFuncionario}/{idChat}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<MensagemIAEnvioDto> enviarMensagem(
            @PathVariable Integer idFuncionario,
            @PathVariable Integer idChat,
            @RequestBody MensagemIAEnvioDto request
    ) {
        return ResponseEntity.ok
                (MensagemMapper.toResponseDto
                        (service.enviarMensagem
                                (MensagemMapper.toEntity(request), idChat, idFuncionario)));
    }

    @GetMapping("/{idFuncionario}/{idChat}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<MensagemIAEnvioDto>> listarMensagensChat(
            @PathVariable Integer idFuncionario,
            @PathVariable Integer idChat
    ) {
        return ResponseEntity.ok
                (MensagemMapper.toRespondeDtoList
                        (service.listarMensagens(idChat, idFuncionario)));
    }

    @DeleteMapping("/{idFuncionario}/{idChat}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> apagarMensagens(
            @PathVariable Integer idFuncionario,
            @PathVariable Integer idChat
    ) {
        service.apagarMensagens(idChat, idFuncionario);
        return ResponseEntity.ok().build();

    }
    @DeleteMapping("/apagar-mensagem-usuario/{idMensagem}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> apagarMensagemUsuario(
            @PathVariable Integer idMensagem
    ) {

        service.apagarMensagemUsuario(idMensagem);
        return ResponseEntity.ok().build();
    }

}

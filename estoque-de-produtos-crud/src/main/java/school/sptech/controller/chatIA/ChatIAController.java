package school.sptech.controller.chatIA;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.chatIA.dto.ChatAtualizarDto;
import school.sptech.controller.chatIA.dto.ChatDto;
import school.sptech.controller.chatIA.dto.ChatMapper;
import school.sptech.service.chatIA.ChatIAService;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatIAController {

    private final ChatIAService service;

    public ChatIAController(ChatIAService service) {
        this.service = service;
    }

    @PostMapping("/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ChatDto> criarNovoChat(
            @PathVariable Integer idFuncionario) {
        return ResponseEntity.ok(
                ChatMapper.toResponseDto(
                        service.criarNovoChat(idFuncionario)));

    }

    @GetMapping("/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ChatDto>> listarChats(
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity.ok(
                ChatMapper.toRespondeDtoList(
                        service.listarChats(idFuncionario)));
    }

    @PatchMapping("/atualizar-nome/{idChat}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ChatAtualizarDto> atualizarNomeChat(
            @PathVariable Integer idChat,
            @PathVariable Integer idFuncionario,
            @RequestBody ChatAtualizarDto request
    ) {

        return ResponseEntity.ok
                (ChatMapper.toResponseDtoAtualizar
                        (service.atualizarNomeChat(idChat, idFuncionario,
                                ChatMapper.toEntityAtualizar(request))));
    }

    @PatchMapping("/fixar-chat/{idChat}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ChatDto> fixarChat(
            @PathVariable Integer idChat,
            @PathVariable Integer idFuncionario
    ) {

        return ResponseEntity
                .ok(ChatMapper
                        .toResponseDto(service
                                .fixarChat(idChat, idFuncionario)));
    }

    @PatchMapping("/desafixar-chat/{idChat}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ChatDto> desafixarChat(
            @PathVariable Integer idChat,
            @PathVariable Integer idFuncionario
    ) {

        return ResponseEntity
                .ok(ChatMapper
                        .toResponseDto(service
                                .desafixarChat(idChat, idFuncionario)));
    }

    @DeleteMapping("/{idChat}/{idFuncionario}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> deletarChat(
            @PathVariable Integer idChat,
            @PathVariable Integer idFuncionario) {
        service.deletarChat(idChat, idFuncionario);
        return ResponseEntity.ok().build();
    }
}

package school.sptech.controller.recuperacaoSenha;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.recuperacaoSenha.dto.RecuperacaoSenhaDto;
import school.sptech.controller.recuperacaoSenha.dto.RedefinirSenhaComTokenDto;
import school.sptech.service.recuperacaoSenha.RecuperacaoSenhaTokenService;

@RestController
@RequestMapping("/recuperacoes")
public class RecuperacaoSenhaTokenController {

    private final RecuperacaoSenhaTokenService senhaTokenService;

    public RecuperacaoSenhaTokenController(RecuperacaoSenhaTokenService senhaTokenService) {
        this.senhaTokenService = senhaTokenService;
    }

    @PostMapping("/esqueceu-senha")
    public ResponseEntity<String> solicitarRecuperacao
            (@RequestBody RecuperacaoSenhaDto request) {
        try {
            senhaTokenService.iniciarRecuperacaoSenha(request.getCpf());
            return ResponseEntity.ok("Link de recuperação de senha enviado para o email da empresa.");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(
            @RequestBody RedefinirSenhaComTokenDto request) {
        try {
            senhaTokenService.redefinirSenha(request.getToken(), request.getNovaSenha());
            return ResponseEntity.ok("Senha redefinida com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{token}")
    public ResponseEntity<String> buscarCpfUsuarioToken(
            @PathVariable String token
    ) {
        try {
            return ResponseEntity.ok(senhaTokenService.buscarCpfUsuarioToken(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

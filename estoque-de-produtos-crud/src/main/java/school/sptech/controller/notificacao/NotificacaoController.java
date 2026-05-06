package school.sptech.controller.notificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import school.sptech.entity.notificacao.Notificacao;
import school.sptech.repository.notificacao.NotificacaoRepository;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoRepository repository;

    @GetMapping
    public List<Notificacao> listar() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "dataCriacao"));
    }

    @PatchMapping("/{id}/lida")
    public void marcarComoLido(@PathVariable Integer id) {
        repository.findById(id).ifPresent(n -> {
            n.setLida(true);
            repository.save(n);
        });
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
package school.sptech.service.prato;

import org.springframework.stereotype.Service;
import school.sptech.entity.prato.Prato;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.prato.PratoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PratoService {

    private final PratoRepository pratoRepository;

    public PratoService(PratoRepository pratoRepository) {
        this.pratoRepository = pratoRepository;
    }

    public List<Prato> listarPratos() {
        if (pratoRepository.findAll().isEmpty()) {
            throw new EntidadeNaoEncontradaException("Pratos não encontrados!");
        }
        return pratoRepository.findAll();
    }

    public Prato cadastrarPrato(Prato prato) {
        prato.setId(prato.getId());
        return pratoRepository.save(prato);
    }

    public Prato atualizarPrato(Prato prato) {
        Optional<Prato> entidadeAtualizar = pratoRepository.findById(prato.getId());

        if (entidadeAtualizar.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Prato não encontrado!");
        }

        prato.setId(prato.getId());
        return pratoRepository.save(prato);
    }

    public void deletarPrato(Integer id) {
        if (!pratoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Prato não encontrado!");
        }

        pratoRepository.deleteById(id);
    }

    public List<Prato> buscarPratosPorNome(String nome) {
        List<Prato> pratos = pratoRepository.findByNomeContainingIgnoreCase(nome);

        if (pratos.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Não existem pratos com %s".formatted(pratos));
        }

        return pratos;
    }
}


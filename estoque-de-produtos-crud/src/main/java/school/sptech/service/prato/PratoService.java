package school.sptech.service.prato;

import org.springframework.stereotype.Service;
import school.sptech.entity.prato.Prato;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.prato.PratoRepository;

import java.util.List;

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
}

package school.sptech.service.prato;

import org.springframework.stereotype.Service;
<<<<<<< HEAD
import school.sptech.entity.prato.Prato;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.prato.PratoRepository;

import java.util.List;
=======
>>>>>>> 68f745151a904fb362fb0fc9affa540a7a4bd68a

@Service
public class PratoService {

<<<<<<< HEAD
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
=======
    private final PratoService pratoService;

    public PratoService(PratoService pratoService) {
        this.pratoService = pratoService;
>>>>>>> 68f745151a904fb362fb0fc9affa540a7a4bd68a
    }
}

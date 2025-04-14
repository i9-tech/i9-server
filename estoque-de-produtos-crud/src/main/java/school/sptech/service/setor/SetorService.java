package school.sptech.service.setor;

import org.springframework.stereotype.Service;
import school.sptech.entity.setor.Setor;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.setor.SetorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SetorService {

    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public Setor cadastrarSetor(Setor setorParaCadastrar) {
        setorParaCadastrar.setId(setorParaCadastrar.getId());
        return setorRepository.save(setorParaCadastrar);
    }

    public List<Setor> listarTodosSetores() {
        return setorRepository.findAll();
    }

    public Optional<Setor> buscarSetorPorId(Integer id) {
        Optional<Setor> setorEncontrado = setorRepository.findById(id);

        if (setorEncontrado.isEmpty()) {
            throw new EntidadeNaoEncontradaException();
        }
        return setorEncontrado;
    }

    public Setor atualizarSetor(Integer id, Setor setorParaAtualizar) {
        Optional<Setor> setorEncontrado = setorRepository.findById(id);

        if (setorEncontrado.isEmpty()) {
            throw new EntidadeNaoEncontradaException("O setor não foi encontrado");
        }
        return setorEncontrado.get();
    }

    public void removerSetor(Integer id) {
        Optional<Setor> setorEncontrado = setorRepository.findById(id);

        if (setorEncontrado.isEmpty()) {
            throw new EntidadeNaoEncontradaException();
        }
        setorRepository.deleteById(id);
    }
}

package school.sptech.service.categoria;

import org.springframework.stereotype.Service;
import school.sptech.entity.categoria.Categoria;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.categoria.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria cadastrarCategoria(Categoria categoriaParaCadastrar) {
        categoriaParaCadastrar.setId(categoriaParaCadastrar.getId());
        return categoriaRepository.save(categoriaParaCadastrar);
    }

    public List<Categoria> listarTodasCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria atualizarCategoria(Integer id, Categoria categoriaParaAtualizar) {
        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if (categoriaEncontrada.isEmpty()) {
            throw new EntidadeNaoEncontradaException("A categoria não foi encontrada");
        }
        return categoriaEncontrada.get();
    }

    public void removerCategoria(Integer id) {
        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if (categoriaEncontrada.isEmpty()) {
            throw new EntidadeNaoEncontradaException();
        }

        categoriaRepository.deleteById(id);
    }
}

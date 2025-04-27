package school.sptech.service.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.entity.categoria.Categoria;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.categoria.CategoriaRepository;
import school.sptech.repository.funcionario.FuncionarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final FuncionarioRepository funcionarioRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, FuncionarioRepository funcionarioRepository) {
        this.categoriaRepository = categoriaRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public Categoria cadastrarCategoria(Categoria categoriaParaCadastrar, Integer idFuncionario) {

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        categoriaParaCadastrar.setFuncionario(funcionario);
        categoriaParaCadastrar.setId(categoriaParaCadastrar.getId());
        return categoriaRepository.save(categoriaParaCadastrar);
    }

    public List<Categoria> listarTodasCategorias(Integer idFuncionario) {
    return categoriaRepository.buscarCategoriasDaEmpresaDoFuncionario(idFuncionario);
    }

    public Optional<Categoria> buscarCategoriaPorId(Integer idCategoria, Integer idFuncionario) {
        Optional<Categoria> categoriaEncontrada = categoriaRepository.buscarCategoriaPorIdDoFuncionarioDaEmpresa(idCategoria, idFuncionario);

        if (categoriaEncontrada.isEmpty()) {
            throw new EntidadeNaoEncontradaException();
        }
        return categoriaEncontrada;
    }

    public Categoria atualizarCategoria(Integer id, Categoria categoriaParaAtualizar) {
        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if (categoriaEncontrada.isEmpty()) {
            throw new EntidadeNaoEncontradaException("A categoria não foi encontrada");
        }

        categoriaParaAtualizar.setId(id);
        return categoriaRepository.save(categoriaParaAtualizar);
    }

    public void removerCategoria(Integer id) {
        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if (categoriaEncontrada.isEmpty()) {
            throw new EntidadeNaoEncontradaException();
        }

        categoriaRepository.deleteById(id);
    }
}

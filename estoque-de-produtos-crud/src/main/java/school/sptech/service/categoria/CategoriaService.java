package school.sptech.service.categoria;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.entity.categoria.Categoria;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.exception.EntidadeInativaException;
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

        if (!categoriaRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        categoriaParaCadastrar.setFuncionario(funcionario);
        categoriaParaCadastrar.setId(categoriaParaCadastrar.getId());
        return categoriaRepository.save(categoriaParaCadastrar);
    }

    public List<Categoria> listarTodasCategorias(Integer idFuncionario) {
        if (!categoriaRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }
    return categoriaRepository.buscarCategoriasDaEmpresaDoFuncionario(idFuncionario);
    }

    public Optional<Categoria> buscarCategoriaPorId(Integer idCategoria, Integer idFuncionario) {

        if (!categoriaRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Optional<Categoria> categoriaEncontrada = categoriaRepository.buscarCategoriaPorIdDoFuncionarioDaEmpresa(idCategoria, idFuncionario);

        if (categoriaEncontrada.isEmpty()) {
            throw new EntidadeNaoEncontradaException();
        }
        return categoriaEncontrada;
    }

    public Categoria atualizarCategoria(Integer id, Categoria categoriaParaAtualizar, Integer idFuncionario) {

        if (!categoriaRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if (categoriaEncontrada.isEmpty()) {
            throw new EntidadeNaoEncontradaException("A categoria não foi encontrada");
        }

        categoriaParaAtualizar.setId(id);
        categoriaParaAtualizar.setFuncionario(funcionario);
        return categoriaRepository.save(categoriaParaAtualizar);
    }

    @Transactional
    public void removerCategoria(Integer id, Integer idFuncionario) {

        if (!categoriaRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if (categoriaEncontrada.isEmpty()) {
            throw new EntidadeNaoEncontradaException();
        }

        categoriaRepository.desvincularPratosDaCategoria(id);
        categoriaRepository.desvincularProdutosDaCategoria(id);

        categoriaRepository.deleteCategoriaById(id);
    }
}

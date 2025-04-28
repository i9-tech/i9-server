package school.sptech.service.setor;

import org.springframework.stereotype.Service;
import school.sptech.controller.setor.dto.SetorMapper;
import school.sptech.entity.categoria.Categoria;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.setor.Setor;
import school.sptech.exception.EntidadeInativaException;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.setor.SetorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SetorService {

    private final SetorRepository setorRepository;
    private final FuncionarioRepository funcionarioRepository;

    public SetorService(SetorRepository setorRepository, FuncionarioRepository funcionarioRepository) {
        this.setorRepository = setorRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public Setor cadastrarSetor(Setor setorParaCadastrar, Integer idFuncionario) {

        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        setorParaCadastrar.setFuncionario(funcionario);

        setorParaCadastrar.setId(setorParaCadastrar.getId());
        return setorRepository.save(setorParaCadastrar);
    }

    public List<Setor> listarTodosSetores(Integer idFuncionario) {

        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        return setorRepository.buscarSetorsDaEmpresaDoFuncionario(idFuncionario);
    }

    public Optional<Setor> buscarSetorPorId(Integer id, Integer idFuncionario) {

        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Optional<Setor> setorEncontrado = setorRepository.buscarSetorPorIdDoFuncionarioDaEmpresa(id, idFuncionario);

        if (setorEncontrado.isEmpty()) {
            throw new EntidadeNaoEncontradaException();
        }
        return setorEncontrado;
    }

    public Setor atualizarSetor(Integer id, Setor setorParaAtualizar, Integer idFuncionario) {

        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        Optional<Setor> setorEncontrado = setorRepository.findById(id);

        if (setorEncontrado.isEmpty()) {
            throw new EntidadeNaoEncontradaException("O setor não foi encontrado");
        }

        setorParaAtualizar.setId(id);
        setorParaAtualizar.setFuncionario(funcionario);
        return setorRepository.save(setorParaAtualizar);
    }

    public void removerSetor(Integer id, Integer idFuncionario) {

        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Optional<Setor> setorEncontrado = setorRepository.findById(id);

        if (setorEncontrado.isEmpty()) {
            throw new EntidadeNaoEncontradaException();
        }

        setorRepository.desvincularPratosDaSetor(id);
        setorRepository.desvincularProdutosDaSetor(id);

        setorRepository.deleteSetorById(id);
    }
}

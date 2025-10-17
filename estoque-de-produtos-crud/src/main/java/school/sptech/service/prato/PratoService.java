package school.sptech.service.prato;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.prato.Prato;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.prato.PratoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PratoService {

    private final PratoRepository pratoRepository;

    private final FuncionarioRepository funcionarioRepository;

    public PratoService(PratoRepository pratoRepository, FuncionarioRepository funcionarioRepository) {
        this.pratoRepository = pratoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Prato> listarTodosPratos(Integer idFuncionario) {
        if (pratoRepository.buscarTodosPratosDaEmpresaDoFuncionario(idFuncionario).isEmpty()) {
            throw new EntidadeNaoEncontradaException("Pratos não encontrados!");
        }
        return pratoRepository.buscarTodosPratosDaEmpresaDoFuncionario(idFuncionario);
    }

    public Page<Prato> listarPratosPaginado(Integer idFuncionario, int pagina, int quantidadePorPagina, String ordem, String termoBusca, Boolean disponivel, Integer setorSelecionado, Integer categoriaSelecionada, Integer areaSelecionada) {
        Sort ordenacao = Sort.by("nome");
        ordenacao = ordem.equalsIgnoreCase("desc") ? ordenacao.descending() : ordenacao.ascending();
        Pageable pageable = PageRequest.of(pagina, quantidadePorPagina, ordenacao);

        if ((termoBusca == null || termoBusca.isEmpty()) && disponivel == null && setorSelecionado== null && categoriaSelecionada == null && areaSelecionada == null) {
            return pratoRepository.buscarPratosDaEmpresaDoFuncionario(idFuncionario, pageable);
        }

        return pratoRepository.buscarPratosComFiltros(idFuncionario, termoBusca, disponivel, setorSelecionado, categoriaSelecionada, areaSelecionada, pageable);
    }


    public Prato buscarPratoPorId(Integer id, Integer idFuncionario) {
        if (pratoRepository.buscarPratoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(id, idFuncionario).isEmpty()) {
            throw new EntidadeNaoEncontradaException("Pratos não encontrados!");
        }

        return pratoRepository.buscarPratoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(id, idFuncionario).get();
    }

    public Prato cadastrarPrato(Prato prato, Integer idFuncionario) {

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        prato.setId(prato.getId());
        prato.setFuncionario(funcionario);
        return pratoRepository.save(prato);
    }

    public Prato atualizarPrato(Prato prato, Integer idPrato, Integer idFuncionario) {
        Optional<Prato> entidadeAtualizar = pratoRepository.buscarPratoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(idPrato, idFuncionario);

        if (entidadeAtualizar.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Prato não encontrado!");
        }

        prato.setId(idPrato);

        return pratoRepository.save(prato);
    }

    @Transactional
    public void deletarPrato(Integer id, Integer idFuncionario) {
        if (pratoRepository.buscarPratoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(id, idFuncionario).isEmpty()) {
            throw new EntidadeNaoEncontradaException("Prato não encontrado!");
        }

        pratoRepository.desvincularPratoDosItens(id);
        pratoRepository.deleteById(id);
    }

    public List<Prato> buscarPratosPorNome(String nome, Integer idFuncionario) {
        List<Prato> pratos = pratoRepository.listarPratoPorNomeLikeEmpresa(nome, idFuncionario);

        if (pratos.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Não existem pratos com %s".formatted(pratos));
        }

        return pratos;
    }

    public List<Prato> buscarPratosPorCategoria(Integer categoriaId, Integer idFuncionario) {
        List<Prato> pratos = pratoRepository.listarPratoPorCategoriaEmpresa(categoriaId, idFuncionario);

        if (pratos.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Não existem pratos na categoria %s".formatted(categoriaId));
        }

        return pratos;
    }

    public List<Prato> buscarPratosPorSetor(Integer setorId, Integer idFuncionario) {
        List<Prato> pratos = pratoRepository.listarPratoPorSetorEmpresa(setorId, idFuncionario);

        if (pratos.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Não existem pratos no setor %s".formatted(setorId));
        }

        return pratos;
    }

    public Double valorTotalEstoquePratos(Integer idFuncionario) {
        return pratoRepository.valorTotalPratosEstoqueEmpresa(idFuncionario);
    }

    public Double valorTotalEstoquePratosInativosEAtivos(Integer idFuncionario) {
        return pratoRepository.valorTotalPratosEmpresaInativosAtivos(idFuncionario);
    }

    public Integer totalPratosEstoque(Integer idFuncionario) {
        return pratoRepository.quantidadeTotalPratosPorEmpresa(idFuncionario);
    }

    public Integer totalPratosAtivos(Integer idFuncionario) {
        return pratoRepository.quantidadePratosAtivosPorEmpresa(idFuncionario);
    }

    public Integer totalPratosInativos(Integer idFuncionario) {
        return pratoRepository.quantidadePratosInativosPorEmpresa(idFuncionario);
    }

    public Integer totalSetoresPratosPorEmpresa(Integer idFuncionario) {
        return pratoRepository.quantidadeSetoresPratoPorEmpresa(idFuncionario);
    }

    public Integer totalCategoriasPratosPorEmpresa(Integer idFuncionario) {
        return pratoRepository.quantidadeCategoriasPratoPorEmpresa(idFuncionario);
    }

}


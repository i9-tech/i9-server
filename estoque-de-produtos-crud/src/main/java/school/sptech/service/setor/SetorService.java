package school.sptech.service.setor;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import school.sptech.controller.setor.dto.SetorAtualizarDto;
import school.sptech.controller.setor.dto.SetorCadastroDto;
import school.sptech.controller.setor.dto.SetorListagemDto;
import school.sptech.controller.setor.dto.SetorMapper;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.setor.Setor;
import school.sptech.exception.EntidadeInativaException;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.setor.SetorRepository;

import java.util.List;

@Service
public class SetorService {

    private final SetorRepository setorRepository;
    private final FuncionarioRepository funcionarioRepository;

    public SetorService(SetorRepository setorRepository, FuncionarioRepository funcionarioRepository) {
        this.setorRepository = setorRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public SetorListagemDto cadastrarSetor(SetorCadastroDto setorDto, Integer idFuncionario) {
        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        Setor setor = SetorMapper.transformarEmEntidade(setorDto);
        setor.setFuncionario(funcionario);

        Setor salvo = setorRepository.save(setor);
        return SetorMapper.transformarEmRespostaDto(salvo);
    }

    public List<SetorListagemDto> listarTodosSetores(Integer idFuncionario) {
        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        List<Setor> setores = setorRepository.buscarSetorsDaEmpresaDoFuncionario(idFuncionario);
        return SetorMapper.transformarEmListaRespostaDto(setores);
    }

    public SetorListagemDto buscarSetorPorId(Integer id, Integer idFuncionario) {
        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Setor setor = setorRepository.buscarSetorPorIdDoFuncionarioDaEmpresa(id, idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Setor não encontrado"));

        return SetorMapper.transformarEmRespostaDto(setor);
    }

    public SetorListagemDto atualizarSetor(Integer id, SetorAtualizarDto setorDto, Integer idFuncionario) {
        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        setorRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("O setor não foi encontrado"));

        Setor setor = SetorMapper.transformarEmEntidade(setorDto);
        setor.setId(id);
        setor.setFuncionario(funcionario);

        Setor atualizado = setorRepository.save(setor);
        return SetorMapper.transformarEmRespostaDto(atualizado);
    }

    @Transactional
    public void removerSetor(Integer setorId, Integer idFuncionario) {
        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        setorRepository.findById(setorId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException());

        setorRepository.desvincularPratosDoSetor(setorId);
        setorRepository.desvincularProdutosDoSetor(setorId);
        setorRepository.deleteSetorById(setorId);
    }
}

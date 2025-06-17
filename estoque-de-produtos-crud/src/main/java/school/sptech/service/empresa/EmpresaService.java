package school.sptech.service.empresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.entity.empresa.Empresa;
import school.sptech.exception.EntidadeInativaException;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.empresa.EmpresaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public Empresa cadastrarEmpresa(Empresa empresaParaCadastrar) {
        empresaParaCadastrar.setId(empresaParaCadastrar.getId());
        return empresaRepository.save(empresaParaCadastrar);
    }

    public List<Empresa> listarEmpresa() {
        if (empresaRepository.findAll().isEmpty()) {
            throw new EntidadeNaoEncontradaException("Empresas não encontradas");
        }
        return empresaRepository.findAll();
    }

    public Empresa listarEmpresaPorId(Integer id) {
        if (!empresaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Empresa não encontrada");
        }
        return empresaRepository.findById(id).get();
    }

    public Empresa atualizarEmpresa(Integer idEmpresa, Empresa empresaParaAtualizar) {

        if (!empresaRepository.verificarEmpresaAtivaPorId(idEmpresa)) {
            throw new EntidadeInativaException();
        }

        if (!empresaRepository.existsById(idEmpresa)) {
            throw new EntidadeNaoEncontradaException("A empresa não foi encontrada");
        }

        Empresa empresaEncontrada = empresaRepository.findById(idEmpresa).get();

        empresaParaAtualizar.setId(idEmpresa);
        empresaParaAtualizar.setCnpj(empresaEncontrada.getCnpj());
        empresaParaAtualizar.setDataCadastro(empresaEncontrada.getDataCadastro());
        empresaParaAtualizar.setAtivo(empresaEncontrada.isAtivo());
        return empresaRepository.save(empresaParaAtualizar);
    }

    public void removerEmpresa(Integer id) {

        if (!empresaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("A empresa não foi encontrada");
        }

        Empresa empresaDesativar = empresaRepository.findById(id).get();

        empresaDesativar.setAtivo(false);

        // empresaRepository.deleteById(id);
    }
}

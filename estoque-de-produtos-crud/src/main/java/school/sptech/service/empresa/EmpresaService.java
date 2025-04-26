package school.sptech.service.empresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.entity.empresa.Empresa;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.empresa.EmpresaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
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

    public Empresa atualizarEmpresa(Integer id, Empresa empresaParaAtualizar) {
        Optional<Empresa> empresaEncontrada = empresaRepository.findById(id);

        if (empresaEncontrada.isEmpty()) {
            throw new EntidadeNaoEncontradaException("A empresa não foi encontrada");
        }
        empresaParaAtualizar.setId(id);
        return empresaRepository.save(empresaParaAtualizar);
    }

    public void removerEmpresa(Integer id) {

        if (!empresaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("A empresa não foi encontrada");
        }
        empresaRepository.deleteById(id);
    }
}

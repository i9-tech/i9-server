package school.sptech.service.empresa;

import org.springframework.stereotype.Service;
import school.sptech.entity.empresa.Empresa;
import school.sptech.exception.EntidadeInativaException;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.service.empresa.port.EmpresaPort;

import java.util.List;


@Service
public class EmpresaService {

    // aplicando inversão de dependência
    private final EmpresaPort empresaPort;

    public EmpresaService(EmpresaPort empresaPort) {
        this.empresaPort = empresaPort;
    }

    public Empresa cadastrarEmpresa(Empresa empresaParaCadastrar) {
        empresaParaCadastrar.setId(empresaParaCadastrar.getId());
        return empresaPort.save(empresaParaCadastrar);
    }

    public List<Empresa> listarEmpresa() {
        if (empresaPort.findAll().isEmpty()) {
            throw new EntidadeNaoEncontradaException("Empresas não encontradas");
        }
        return empresaPort.findAll();
    }

    public Empresa listarEmpresaPorId(Integer id) {
        if (!empresaPort.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Empresa não encontrada");
        }
        return empresaPort.findById(id).get();
    }

    public Empresa atualizarEmpresa(Integer idEmpresa, Empresa empresaParaAtualizar) {

        //buscando o estado atual da empresa
        Empresa empresaEncontrada = empresaPort.findById(idEmpresa)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("A empresa não foi encontrada"));

        // Se a empresa estiver inativa, a Entidade lança a exceção e o Service propaga
        // Chamando a regra de domínio
        try {
            empresaEncontrada.validarSePodeSerAtualizada();
        } catch (IllegalStateException e) {
            // Mapeia a exceção do domínio
            throw new EntidadeInativaException();
        }

        empresaEncontrada.setNome(empresaParaAtualizar.getNome());
        empresaEncontrada.setEndereco(empresaParaAtualizar.getEndereco());
        empresaEncontrada.setWhatsapp(empresaParaAtualizar.getWhatsapp());

        return empresaPort.save(empresaEncontrada);
    }

    public void removerEmpresa(Integer id) {

        Empresa empresaDesativar = empresaPort.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("A empresa não foi encontrada"));

        // chamando SRP (metododo da entidade)
        empresaDesativar.desativar();

        // persistencia da mudança
        empresaPort.save(empresaDesativar);

    }
}

/*

Agora, o service não depende de uma camada externa (Repository)
e sim da abstração Empresa port.

 */
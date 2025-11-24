package school.sptech.service.plano;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.controller.plano.dto.PlanoTemplateAtualizarDto;
import school.sptech.controller.plano.dto.PlanoTemplateCriacaoDto;
import school.sptech.entity.plano.PlanoTemplate;
import school.sptech.repository.plano.PlanoTemplateRepository;

import java.util.List;

@Service
public class PlanoTemplateService {

    private final PlanoTemplateRepository repository;

    public PlanoTemplateService(PlanoTemplateRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PlanoTemplate> listarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public PlanoTemplate buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PlanoTemplate não encontrado: " + id));
    }

    @Transactional
    public PlanoTemplate criar(PlanoTemplateCriacaoDto dto) {
        PlanoTemplate entity = new PlanoTemplate();
        entity.setTipo(dto.getTipo());
        entity.setDescricao(dto.getDescricao());
        entity.setPrecoMensal(dto.getPrecoMensal());
        entity.setPrecoMensalComDescontoAnual(dto.getPrecoMensalComDescontoAnual());
        entity.setPrecoAnual(dto.getPrecoAnual());
        entity.setQtdUsuarios(dto.getQtdUsuarios());
        entity.setQtdSuperUsuarios(dto.getQtdSuperUsuarios());
        entity.setAcessoRelatorioWhatsApp(Boolean.TRUE.equals(dto.getAcessoRelatorioWhatsApp()));
        entity.setAcessoDashboard(Boolean.TRUE.equals(dto.getAcessoDashboard()));
        return repository.save(entity);
    }

    @Transactional
    public PlanoTemplate atualizar(Integer id, PlanoTemplateAtualizarDto dto) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("PlanoTemplate não encontrado: " + id);
        }

        PlanoTemplate plano = repository.getReferenceById(id);

        if (dto.getTipo() != null) plano.setTipo(dto.getTipo());
        if (dto.getDescricao() != null) plano.setDescricao(dto.getDescricao());
        if (dto.getPrecoMensal() != null) plano.setPrecoMensal(dto.getPrecoMensal());
        if (dto.getPrecoMensalComDescontoAnual() != null)
            plano.setPrecoMensalComDescontoAnual(dto.getPrecoMensalComDescontoAnual());
        if (dto.getPrecoAnual() != null) plano.setPrecoAnual(dto.getPrecoAnual());
        if (dto.getQtdUsuarios() != null) plano.setQtdUsuarios(dto.getQtdUsuarios());
        if (dto.getQtdSuperUsuarios() != null) plano.setQtdSuperUsuarios(dto.getQtdSuperUsuarios());
        if (dto.getAcessoRelatorioWhatsApp() != null)
            plano.setAcessoRelatorioWhatsApp(dto.getAcessoRelatorioWhatsApp());
        if (dto.getAcessoDashboard() != null)
            plano.setAcessoDashboard(dto.getAcessoDashboard());

        return repository.save(plano);
    }

    @Transactional
    public void excluir(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("PlanoTemplate não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}

package school.sptech.service.empresa;

import org.springframework.stereotype.Service;
import school.sptech.repository.empresa.EmpresaRepository;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }
}

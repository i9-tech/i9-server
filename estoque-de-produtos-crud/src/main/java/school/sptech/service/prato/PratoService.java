package school.sptech.service.prato;

import org.springframework.stereotype.Service;

@Service
public class PratoService {

    private final PratoService pratoService;

    public PratoService(PratoService pratoService) {
        this.pratoService = pratoService;
    }
}

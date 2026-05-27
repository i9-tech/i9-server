package school.sptech.controller.leitorNotaFiscal;
import org.springframework.web.bind.annotation.*;
import school.sptech.service.leitorNotaFiscal.NFCeService;

@RestController
@RequestMapping("/nfce")
public class NFCeController {

    private final NFCeService service;

    public NFCeController(NFCeService service) {
        this.service = service;
    }

    @PostMapping("/processar-html")
    public NFCeResponseDTO processarHtml(
            @RequestBody NFCeHTMLRequestDTO request
    ) {

        return service.processarHtml(
                request.getHtml(),
                request.getChNFe()
        );
    }
}
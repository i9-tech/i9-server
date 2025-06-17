package school.sptech.controller.azureBlobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.service.azureBlobs.AzureBlobService;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/azure")
public class AzureBlobController {

    @Autowired
    private AzureBlobService azureBlobService;

    @PostMapping("/enviar-imagem")
    public ResponseEntity<Map<String, String>> enviarImagem(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String url = azureBlobService.subirImagemParaBlobStorage(file);
            return ResponseEntity.ok(Collections.singletonMap("imageUrl", url));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

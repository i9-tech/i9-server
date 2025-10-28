package school.sptech.controller.pixabay;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.service.pixabay.PixabayService;

@RestController
@RequestMapping("/api/pixabay")
public class PixabayController {
    private final PixabayService pixabayService;

    public PixabayController(PixabayService pixabayService) {
        this.pixabayService = pixabayService;
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchImages(@RequestParam String query) {
        String jsonResponse = pixabayService.searchImages(query);
        return ResponseEntity.ok(jsonResponse);
    }
}

package school.sptech.service.pixabay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PixabayService {
    @Value("${pixabay.api.key}")
    private String apiKey;

    @Value("${pixabay.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String searchImages(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("key", apiKey)
                .queryParam("q", query)
                .queryParam("image_type", "photo")
                .queryParam("lang", "pt")
                .queryParam("per_page", 30)
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }
}

package school.sptech.service.azureBlobs;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.azure.storage.blob.models.BlobHttpHeaders;
import java.io.IOException;
import java.util.UUID;

@Service
public class AzureBlobService {

    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Value("${azure.storage.sas-token}")
    private String sasToken;



    public String subirImagemParaBlobStorage(MultipartFile arquivoAzure) throws IOException {
        String endpointAzure = String.format("https://%s.blob.core.windows.net", accountName);
        String nomeArquivo = UUID.randomUUID() + "-" + arquivoAzure.getOriginalFilename();

        BlobClient blobClient = new BlobClientBuilder()
                .endpoint(endpointAzure)
                .sasToken(sasToken)
                .containerName(containerName)
                .blobName(nomeArquivo)
                .buildClient();

        blobClient.upload(arquivoAzure.getInputStream(), arquivoAzure.getSize(), true);

        BlobHttpHeaders headers = new BlobHttpHeaders()
                .setContentType(arquivoAzure.getContentType());
        blobClient.setHttpHeaders(headers);

        return blobClient.getBlobUrl(); // retorna a URL pública
    }

}

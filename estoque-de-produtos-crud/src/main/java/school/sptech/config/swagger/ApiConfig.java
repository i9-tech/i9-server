package school.sptech.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "i9 Server",
                description = "Documentação de API Rest configurada pela equipe i9",
                contact = @Contact(
                        name = "i9Tech",
                        url = "https://github.com/i9-tech/",
                        email = "i9.tech@gmail.com"
                ),
                license = @License(name = "MIT License", url = "https://opensource.org/licenses/MIT"),
                version = "1.0 - beta"
        )
)

public class ApiConfig {
}

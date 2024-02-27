package com.XAUS.Configs.OpenApiConfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Murilo Barbosa",
                        email = "murilobarbosa358@gmail.com",
                        url = "https://portfolio-murilo358.vercel.app/"
                ),
                description = """
                        An application based on admin panel that provides you a simple interface and interactions.\s
                        \s
                         First of all use the /auth/login route to get the JWT token and then use it to authenticate on the api docs""",
                title = "XAUS-system",
                version = "1.0",
                license = @License(
                        name = "Xaus-systems"
                )
        ),
        servers = {
                @Server(
                        description = "Xaus-system local server",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Xaus-system production server",
                        url = "To implement a production server"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "Bearer authentication"
                )
        }
)
@SecurityScheme(
        name = "Bearer authentication",
        description = "JWT authentication",
        scheme =  "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApi30Config {

}
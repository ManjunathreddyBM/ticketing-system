package com.tms.ticketing_system.swagger;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SecurityScheme(
        name = "bearer auth",
        description = "Jwt Auth Description",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
 )

public class OpenApiConfig {
	public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("API Documentation")
            .version("1.0")
            .description("API Documentation for the Ticketing System"));
    }
}

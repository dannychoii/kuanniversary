package kr.ac.korea.anniversary.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SecurityScheme(
    type = SecuritySchemeType.APIKEY, `in` = SecuritySchemeIn.HEADER,
    name = "Authorization", description = "Basic {username:password} 형태로 입력 base64 encode username:password concatenation",
)
@Configuration
class SwaggerConfig {
    val authorization = "Authorization"

    @Bean
    fun adminApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("admin")
            .addOpenApiCustomizer {
                it
                    .info(
                        Info()
                            .title("API")
                            .description("API Document")
                            .version("1.0")
                    )
                    // enable to send header - https://github.com/swagger-api/swagger-ui/issues/5715#issuecomment-624866240
                    .security(
                        listOf(
                            SecurityRequirement().addList(authorization),
                        )
                    )
            }
            .pathsToMatch("/admin/v1/**")
            .build()
    }


    @Bean
    fun api(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("api")
            .addOpenApiCustomizer {
                it
                    .info(
                        Info()
                            .title("API")
                            .description("API Document")
                            .version("1.0")
                    )
            }
            .pathsToMatch("/api/v1/**")
            .build()
    }
}
package io.abner.docsystem.recepcao.web

import io.abner.docsystem.recepcao.adapter.kafka.ConsumidorSubmissaoDocumento
import io.quarkus.runtime.StartupEvent
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType
import org.eclipse.microprofile.openapi.annotations.info.Contact
import org.eclipse.microprofile.openapi.annotations.info.Info
import org.eclipse.microprofile.openapi.annotations.info.License
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes
import javax.enterprise.event.Observes
import javax.ws.rs.core.Application


@OpenAPIDefinition(/*tags = [Tag(name = "documento", description = "Operações em documentos."), Tag(name = "status-submissao",
    description = "OPerações relacioandas ao status da submissão")],*/
    info = Info(title = "API Submissão de Documentos",
        version = "1.0.0",
        contact = Contact(name = "Ábner Oliveira",
            url = "http://abner.io",
            email = "abner.oliveira@serpro.gov.br"),
        license = License(name = "MIT", url = "https://www.apache.org/licenses/LICENSE-2.0.html")))

@SecuritySchemes(value = [
    SecurityScheme(
        securitySchemeName = "httpBasic",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
    ), SecurityScheme(
        type = SecuritySchemeType.APIKEY,
        bearerFormat = "Bearer ",
        securitySchemeName = "tokenJwt",
        `in` = SecuritySchemeIn.HEADER,
        description = """Um header com um token jwt assinado com o jwt secret key
            |específico da aplicação, cadastrado junto ao serviço:
        """
    )
])
class DocumentoApiApplication(

) : Application() {



}

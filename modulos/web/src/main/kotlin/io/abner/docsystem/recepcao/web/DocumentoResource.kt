package io.abner.docsystem.recepcao.web

import io.abner.docsystem.recepcao.domain.port.input.RecepcionarEventoDocumentoUseCase
import io.quarkus.security.Authenticated
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn
import org.eclipse.microprofile.openapi.annotations.enums.ParameterStyle
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirements
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.jboss.logging.Logger
import java.net.URI
import java.util.*
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("/api/v1/documento")
@Tag(name = "Documento")
class DocumentoResource {

    companion object {
        val LOGGER: Logger = Logger.getLogger(DocumentoResource::class.java.simpleName)
    }

    @Inject
    @field:Default
    private lateinit var recepcionarEventoDocumentoUseCase: RecepcionarEventoDocumentoUseCase


    @GET
    @Path("ready/auth")
    @Produces(MediaType.TEXT_PLAIN)
    @Authenticated
    @SecurityRequirements(value = [SecurityRequirement(
        name = "httpBasic",
        scopes = []
    ) ])
    fun readyAuthenticated(@Context ctx: SecurityContext): String {
        return "OK - Oi usuario autenticado: ${ctx.userPrincipal.name}"
    }

    @POST
    @Authenticated
    @SecurityRequirements(value = [SecurityRequirement(
        name = "bearerAuth",
        scopes = []
    ), SecurityRequirement(
        name = "httpBasic",
        scopes = []
    ) ])
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "201",
                description = "Submissão de alteração aceita",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SubmissaoDocumentoResponseBody::class)
                    ),
                ]
            )
        ]
    )
    @Operation(
        summary = "Submete um documento para o docsystem",
        description = "Retorna o Location para onde escutar o resultado e o id da solicitacao",

        )
    suspend fun submterDocumento(
        @Context ctx: SecurityContext,
        @Parameter(
            description = "corpo da requisição",
            required = true,
            `in` = ParameterIn.DEFAULT,
            style = ParameterStyle.DEEPOBJECT,
            schema = Schema(implementation = SubmissaoDocumentoRequestBody::class)
        ) submissaoDocumento: SubmissaoDocumentoRequestBody,
    ): Response {
        val command = submissaoDocumento.toCommand(
            commandId = UUID.randomUUID(),
            idUsuario = ctx.userPrincipal.name
        )
        recepcionarEventoDocumentoUseCase.recepcionar(
            command
        )
        return Response.created(URI.create("/api/v1/submissao-documento/${command.id}")).entity(
            SubmissaoDocumentoResponseBody(
                idSubmissao = command.id
            )
        ).build()
    }
    /**
     *  Exemplo REQUEST:
    {
    "id": "be3abe68-1f79-49fa-9d9c-dd87ceb3f4af",
    "dados": {"tipo": "ESCRITURACAO", "orgao": "ORGAO AB"}
    }
     */
}
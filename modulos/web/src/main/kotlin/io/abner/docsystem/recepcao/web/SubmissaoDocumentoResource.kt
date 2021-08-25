package io.abner.docsystem.recepcao.web

import io.abner.docsystem.recepcao.domain.evento.EventoResultadoSubmissaoDocumento
import io.abner.docsystem.recepcao.domain.port.output.FonteEventoResultadoSubmissaoDocumentoPort
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import org.jboss.resteasy.reactive.RestSseElementType
import java.time.Duration
import java.util.*
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/api/v1/submissao-documento")
class SubmissaoDocumentoResource {

    @Inject
    lateinit var fonteEventoResultadoSubmissaoDocumentoPort: FonteEventoResultadoSubmissaoDocumentoPort

    @GET
    @Path("{idDocumento}")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.APPLICATION_JSON)
    fun obterAndamentoSubmissao(@PathParam("idDocumento") idDocumento: UUID): Multi<EventoResultadoSubmissaoDocumento> {
//        return Multi.createFrom()
//            .items(1,2, 3,4,5,6,7,8,9)
//            .onItem()
//            .transform { i ->
//                mapOf("id" to i) as Map<String, Any>
//            }.onItem()
//            .call { map ->
//                Uni.createFrom().item(map).onItem().delayIt().by(Duration.ofSeconds(2))
//            }

        return fonteEventoResultadoSubmissaoDocumentoPort
            .obterStreamResultado()
//            .onItem()
//            .invoke { evt -> println("EVENT_______ $evt")}
//            .filter { evt ->
//                evt.getPayload().idDocumento == idDocumento
//            }
    }

}
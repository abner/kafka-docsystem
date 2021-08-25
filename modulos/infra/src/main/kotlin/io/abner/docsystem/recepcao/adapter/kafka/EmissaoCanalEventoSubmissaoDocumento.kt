package io.abner.docsystem.recepcao.adapter.kafka

import arrow.core.Either
import arrow.core.None
import io.abner.docsystem.recepcao.domain.evento.EventoSubmissaoDocumento
import io.abner.docsystem.recepcao.domain.model.SubmissaoDocumento
import io.abner.docsystem.recepcao.domain.port.output.EmissorEventoSubmissaoPort
import io.opentelemetry.context.Context
import io.smallrye.reactive.messaging.TracingMetadata
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.eclipse.microprofile.reactive.messaging.Message
import org.eclipse.microprofile.reactive.messaging.Metadata
import org.jboss.logging.Logger
import java.util.concurrent.CompletableFuture
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class EmissaoCanalEventoSubmissaoDocumento : EmissorEventoSubmissaoPort {

    @Channel("submissao-documento")
    lateinit var emitter: Emitter<SubmissaoDocumento>

    override suspend fun enviar(evento: EventoSubmissaoDocumento): Either<Throwable, None> {
        val messageMetadata: Metadata = Metadata.of(TracingMetadata.withCurrent(Context.current()))
        // cria a mensagem utilizando o payload e o metadado de tracing
        val message: Message<SubmissaoDocumento> = Message.of(evento.getPayload(), messageMetadata
        ) {
            CompletableFuture.completedFuture(null)
        }
        emitter.send(message)
        return Either.Right(None)
    }


    companion object {
        private val LOGGER: Logger = Logger.getLogger(EmissaoCanalEventoSubmissaoDocumento::class.java.simpleName)
    }

}
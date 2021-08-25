package io.abner.docsystem.recepcao.adapter.kafka

import arrow.core.Either
import arrow.core.None
import com.fasterxml.jackson.databind.ObjectMapper
import io.abner.docsystem.recepcao.domain.evento.EventoResultadoSubmissaoDocumento
import io.abner.docsystem.recepcao.domain.model.ResultadoSubmissaoDocumento
import io.abner.docsystem.recepcao.domain.port.output.EmissorEventoSubmissaoProcessadaPort
import io.abner.docsystem.recepcao.domain.port.output.FonteEventoResultadoSubmissaoDocumentoPort
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor
import kotlinx.coroutines.future.await
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class CanalEventoNotificacaoSubmissaoProcessada : FonteEventoResultadoSubmissaoDocumentoPort,
    EmissorEventoSubmissaoProcessadaPort {

    @Inject
    lateinit var objectMapper: ObjectMapper

    @Channel("notificacao-resultado-submissao")
    lateinit var emitter: Emitter<ResultadoSubmissaoDocumento>

    private val broadcastEventoNotificaoResultado: BroadcastProcessor<EventoResultadoSubmissaoDocumento> =
        BroadcastProcessor.create<EventoResultadoSubmissaoDocumento>()

    override suspend fun enviar(eventoResultadoSubmissao: EventoResultadoSubmissaoDocumento): Either<Throwable, None> {
        emitter.send(eventoResultadoSubmissao.getPayload()).await()
        return Either.Right(None)
    }

    override fun obterStreamResultado(): Multi<EventoResultadoSubmissaoDocumento> {
        return broadcastEventoNotificaoResultado.toHotStream()

    }

    @Incoming("recepcao-notificacao-resultado-submissao")
    fun recepcionarEventoResultadoSubmissao(resultadoSubmissaoDocumento: ResultadoSubmissaoDocumento) {
        val evento = EventoResultadoSubmissaoDocumento(
            resultadoSubmissaoDocumento
        )
        broadcastEventoNotificaoResultado.onNext(evento)
    }


}
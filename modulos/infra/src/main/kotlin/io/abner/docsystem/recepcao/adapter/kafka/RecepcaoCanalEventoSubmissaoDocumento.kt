package io.abner.docsystem.recepcao.adapter.kafka

import io.abner.docsystem.recepcao.domain.evento.EventoSubmissaoDocumento
import io.abner.docsystem.recepcao.domain.model.SubmissaoDocumento
import io.abner.docsystem.recepcao.domain.port.input.ProcessarDocumentoRecebidoUseCase
import io.abner.docsystem.recepcao.domain.port.output.FonteEventoSubmissaoDocumentoPort
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.jboss.logging.Logger
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class RecepcaoCanalEventoSubmissaoDocumento :
    FonteEventoSubmissaoDocumentoPort {
    @Inject
    lateinit var processarDocumentoRecebidoUseCase: ProcessarDocumentoRecebidoUseCase
    private val broadcastProcessor = BroadcastProcessor.create<EventoSubmissaoDocumento>()
    override fun obterStreamResultado(): Multi<EventoSubmissaoDocumento> = broadcastProcessor

    @Incoming("recepcao-submissao-documento")
    suspend fun recepcionarEventoSubmissao(payload: SubmissaoDocumento): CompletionStage<Void> {
        val evento = EventoSubmissaoDocumento(
            payload
        )
        broadcastProcessor.onNext(evento)
        processarDocumentoRecebidoUseCase.executar(evento)
        return CompletableFuture.completedFuture(null)
    }

    companion object {
        private val LOGGER: Logger = Logger.getLogger(RecepcaoCanalEventoSubmissaoDocumento::class.java)
    }

}
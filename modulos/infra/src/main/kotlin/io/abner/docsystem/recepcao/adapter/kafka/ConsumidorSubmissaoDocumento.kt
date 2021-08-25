package io.abner.docsystem.recepcao.adapter.kafka

import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import io.smallrye.mutiny.subscription.Cancellable
import org.jboss.logging.Logger
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.inject.Inject

@ApplicationScoped
class ConsumidorSubmissaoDocumento() {

    fun start(@Observes startupEvent: StartupEvent) {
        iniciarConsumoEvento()
    }

    fun start(@Observes stopEvent: ShutdownEvent) {
        subscription.cancel()
    }

    @Inject
    lateinit var recepcaoCanalEventoSubmissaoDocumento: RecepcaoCanalEventoSubmissaoDocumento

    lateinit var subscription: Cancellable
    companion object {
        val LOGGER: Logger = Logger.getLogger(ConsumidorSubmissaoDocumento::class.java.simpleName)
    }

    fun iniciarConsumoEvento() {
        val stream = recepcaoCanalEventoSubmissaoDocumento.obterStreamResultado()
        subscription = stream
            .onItem()
            .invoke { evento ->
                LOGGER.info("EVENTO RECEBIDO: ${evento.getPayload()}")
            }
            .onFailure()
            .invoke { e ->
                LOGGER.error(e.message, e)
            }
            .subscribe()
            .with { _ ->

            }

    }
}
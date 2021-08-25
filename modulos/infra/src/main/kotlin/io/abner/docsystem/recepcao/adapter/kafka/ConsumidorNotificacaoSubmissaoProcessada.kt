package io.abner.docsystem.recepcao.adapter.kafka

import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import io.smallrye.mutiny.subscription.Cancellable
import org.jboss.logging.Logger
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.inject.Inject

@ApplicationScoped
class ConsumidorNotificacaoSubmissaoProcessada {
    fun start(@Observes startupEvent: StartupEvent) {
        iniciarConsumoEvento()
    }

    fun start(@Observes stopEvent: ShutdownEvent) {
        subscricao.cancel()
    }
    @Inject
    lateinit var canalEventoNotificacaoSubmissaoProcessada: CanalEventoNotificacaoSubmissaoProcessada

    lateinit var subscricao: Cancellable
    companion object {
        val LOGGER: Logger = Logger.getLogger(ConsumidorNotificacaoSubmissaoProcessada::class.java.simpleName)
    }

    fun iniciarConsumoEvento() {
        val stream = canalEventoNotificacaoSubmissaoProcessada.obterStreamResultado()
        subscricao = stream
            .onItem()
            .invoke { evento ->
                LOGGER.info("EVENTO NOTIFICACAO PROCESSAMENTO RECEBIDO: ${evento.getPayload()}")
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
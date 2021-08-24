package io.abner.docsystem.recepcao.domain.port.output

import arrow.core.Either
import arrow.core.None
import io.abner.docsystem.recepcao.domain.evento.EventoResultadoSubmissaoDocumento

interface EmissorNotificacaoPort {
    fun enviar(eventoResultadoSubmissao: EventoResultadoSubmissaoDocumento): Either<Throwable, None>

}
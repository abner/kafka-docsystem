package io.abner.docsystem.recepcao.domain.port.output

import arrow.core.Either
import arrow.core.None
import io.abner.docsystem.recepcao.domain.evento.EventoSubmissaoDocumento

interface EmissorEventoSubmissaoPort {
    suspend fun enviar(evento: EventoSubmissaoDocumento): Either<Throwable, None>
}
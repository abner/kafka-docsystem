package io.abner.docsystem.recepcao.domain.port.input

import arrow.core.Either
import arrow.core.None
import io.abner.docsystem.recepcao.domain.evento.EventoSubmissaoDocumento
// import io.vavr.control.Either

interface ProcessarDocumentoRecebidoUseCase {
    suspend fun executar(evento: EventoSubmissaoDocumento): Either<Throwable, None>
}
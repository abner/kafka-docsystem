package io.abner.docsystem.recepcao.domain.port.input

import arrow.core.Either
import arrow.core.None
import io.abner.docsystem.recepcao.domain.evento.EventoSubmissaoDocumento

interface ProcessarDocumentoRecebidoUseCase {
    suspend fun executar(evento: EventoSubmissaoDocumento): Either<Throwable, None>
}
package io.abner.docproc.recepcao.domain.port.input

import io.abner.docproc.recepcao.domain.evento.base.EventoSubmissaoDocumento
import io.vavr.control.Either

interface ProcessarDocumentoRecebidoUseCase {
    suspend fun executar(evento: EventoSubmissaoDocumento): Either<Throwable, Void>
}
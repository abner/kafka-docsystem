package io.abner.docsystem.recepcao.domain.port.input

import arrow.core.Either
import arrow.core.None
import io.abner.docsystem.recepcao.domain.model.CommandSubmissaoDocumento

interface RecepcionarEventoDocumentoUseCase {
    suspend fun recepcionar(command: CommandSubmissaoDocumento): Either<Throwable, None>
}
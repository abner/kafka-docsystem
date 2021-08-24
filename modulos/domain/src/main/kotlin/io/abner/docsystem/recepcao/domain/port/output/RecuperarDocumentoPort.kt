package io.abner.docsystem.recepcao.domain.port.output

import arrow.core.Either
import io.abner.docsystem.recepcao.domain.model.Documento
// import io.vavr.control.Either
import java.util.*

interface RecuperarDocumentoPort {
    suspend fun recuperar(id: UUID): Either<Throwable, Documento>
}
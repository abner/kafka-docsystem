package io.abner.docproc.recepcao.domain.port.output

import io.abner.docproc.recepcao.domain.model.Documento
import io.vavr.control.Either
import java.util.*

interface RecuperarDocumentoPort {
    suspend fun recuperar(id: UUID): Either<Throwable, Documento>
}
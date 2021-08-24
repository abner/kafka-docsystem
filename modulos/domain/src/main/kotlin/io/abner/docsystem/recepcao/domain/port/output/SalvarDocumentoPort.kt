package io.abner.docsystem.recepcao.domain.port.output

import arrow.core.Either
import arrow.core.None
import io.abner.docsystem.recepcao.domain.model.Documento
// import io.vavr.control.Either

interface SalvarDocumentoPort {
    suspend fun salvar(documento: Documento): Either<Throwable, None>
}
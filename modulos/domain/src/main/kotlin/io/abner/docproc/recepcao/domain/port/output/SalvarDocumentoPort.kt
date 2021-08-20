package io.abner.docproc.recepcao.domain.port.output

import io.abner.docproc.recepcao.domain.model.Documento
import io.vavr.control.Either

interface SalvarDocumentoPort {
    suspend fun salvar(documento: Documento): Either<Throwable, Void>
}
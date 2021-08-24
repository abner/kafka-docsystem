package io.abner.docsystem.recepcao.domain.port.output

import arrow.core.Either
import arrow.core.None
import io.abner.docsystem.recepcao.domain.model.Documento
import io.abner.docsystem.recepcao.domain.model.DocumentoRegistrado


interface SalvarDocumentoPort {
    suspend fun salvar(documento: DocumentoRegistrado): Either<Throwable, None>
}
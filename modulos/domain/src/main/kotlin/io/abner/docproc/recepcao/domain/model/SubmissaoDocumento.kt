package io.abner.docproc.recepcao.domain.model

import java.util.*

data class SubmissaoDocumento(
    val id: UUID,
    val idUsuario: String,
    val conteudoDocumento: String
)
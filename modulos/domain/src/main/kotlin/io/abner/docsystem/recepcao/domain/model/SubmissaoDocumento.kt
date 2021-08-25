package io.abner.docsystem.recepcao.domain.model

import java.util.*

data class SubmissaoDocumento(
    val id: UUID,
    val idDocumento: UUID,
    val idUsuario: String,
    val conteudo: String
)
package io.abner.docsystem.recepcao.domain.model

import java.util.*

data class CommandSubmissaoDocumento(
    val id: UUID,
    val idDocumento: UUID,
    val conteudo: String,
    val idUsuario: String
)
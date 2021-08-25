package io.abner.docsystem.recepcao.web

import java.util.*

data class SubmissaoDocumentoResponseBody(
    val ok: Boolean = true,
    val idSubmissao: UUID
)
package io.abner.docsystem.recepcao.domain.model

import java.util.*

data class ResultadoSubmissaoDocumento(
    val idDocumento: UUID,
    val idSolicitacaoAtualizacaoDocumento: UUID,
    val sucesso: Boolean,
    val detalhe: String? = null
)
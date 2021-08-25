package io.abner.docsystem.recepcao.domain.model

import java.util.*

data class ResultadoSubmissaoDocumento(
    val idSolicitacaoAtualizacaoDocumento: UUID,
    val sucesso: Boolean,
    val detalhe: String? = null
)
package io.abner.docproc.recepcao.domain.model

import java.util.*

data class ResultadoSubmissaoDocumento(
    val idSolicitacaoAtualizacaoDocumento: UUID,
    val sucesso: Boolean,
    val detalhe: String
)
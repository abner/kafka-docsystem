package io.abner.docproc.recepcao.domain.model

import java.util.*

class Documento(
    val arquivado: Boolean,
    val id: UUID,
    var descricao: String,
    var conteudo: String
) {
    fun podeSerSalvo(): Boolean {
        return !this.arquivado
    }
}
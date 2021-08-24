package io.abner.docsystem.recepcao.domain.model

import java.util.*

data class DocumentoNaoEncontrado(
    override val id: UUID,
    override val encontrado: Boolean = false
): Documento {
}
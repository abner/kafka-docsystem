package io.abner.docsystem.recepcao.domain.model

import java.util.*

interface Documento {
    val id: UUID
    val encontrado: Boolean
    fun podeSerSalvo(): Boolean = false
}
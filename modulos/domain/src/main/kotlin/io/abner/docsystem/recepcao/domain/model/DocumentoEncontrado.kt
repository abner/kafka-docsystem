package io.abner.docsystem.recepcao.domain.model

import java.util.*

data class DocumentoEncontrado(
    override val id: UUID,
    val idPessoa: String = "------",
    val arquivado: Boolean,
    val descricao: String,
    val dados: String,
    override val encontrado: Boolean = true
) : Documento {
    override fun podeSerSalvo(): Boolean {
        return !this.arquivado
    }

    fun alterar(dados: String): DocumentoEncontrado {
        return if (podeSerSalvo()) {
            DocumentoEncontrado(
                id = this.id,
                dados = dados,
                descricao = this.descricao,
                arquivado = this.arquivado,
                encontrado = this.encontrado
            )
        } else {
            this
        }
    }

}
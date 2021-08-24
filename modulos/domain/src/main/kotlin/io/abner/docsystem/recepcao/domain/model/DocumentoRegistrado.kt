package io.abner.docsystem.recepcao.domain.model

import java.util.*

class DocumentoRegistrado(
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

    fun alterar(dados: String): DocumentoRegistrado {
        return if (podeSerSalvo()) {
            DocumentoRegistrado(
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
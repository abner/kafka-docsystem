package io.abner.docsystem.recepcao.domain.evento

import io.abner.docsystem.recepcao.domain.model.SubmissaoDocumento

class EventoSubmissaoDocumento(private val payload: SubmissaoDocumento) :
    Evento<SubmissaoDocumento> {

    override fun getIdentificador(): String {
        return "submissao-documento"
    }

    override fun getPayload(): SubmissaoDocumento = payload
}
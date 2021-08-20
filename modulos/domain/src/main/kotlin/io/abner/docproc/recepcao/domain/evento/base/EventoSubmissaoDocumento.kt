package io.abner.docproc.recepcao.domain.evento.base

import io.abner.docproc.recepcao.domain.model.SubmissaoDocumento

class EventoSubmissaoDocumento(private val value: SubmissaoDocumento) :
    Evento<SubmissaoDocumento> {

    override fun getIdentificador(): String {
        return "submissao-documento"
    }

    override fun getPayload(): SubmissaoDocumento = value
}
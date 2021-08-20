package io.abner.docproc.recepcao.domain.evento.base

import io.abner.docproc.recepcao.domain.model.ResultadoSubmissaoDocumento

class EventoResultadoSubmissaoDocumento(private val value: ResultadoSubmissaoDocumento): Evento<ResultadoSubmissaoDocumento> {
    override fun getPayload(): ResultadoSubmissaoDocumento = value
    override fun getIdentificador(): String = "resultado-submissao-documento"
}
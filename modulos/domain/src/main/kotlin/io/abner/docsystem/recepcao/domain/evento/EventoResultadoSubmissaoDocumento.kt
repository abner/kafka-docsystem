package io.abner.docsystem.recepcao.domain.evento

import io.abner.docsystem.recepcao.domain.model.ResultadoSubmissaoDocumento

class EventoResultadoSubmissaoDocumento(private val value: ResultadoSubmissaoDocumento):
    Evento<ResultadoSubmissaoDocumento> {
    override fun getPayload(): ResultadoSubmissaoDocumento = value
    override fun getIdentificador(): String = "resultado-submissao-documento"
}
package io.abner.docproc.recepcao.domain.evento.base

interface Evento<T> {
    fun getPayload(): T
    fun getIdentificador(): String
    fun getTopicoAlvo(): String {
        return "docproc.recepcao." + getIdentificador()
    }
}
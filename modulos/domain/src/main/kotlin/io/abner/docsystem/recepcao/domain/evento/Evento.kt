package io.abner.docsystem.recepcao.domain.evento

interface Evento<T> {
    fun getPayload(): T
    fun getIdentificador(): String
    fun getTopicoAlvo(): String {
        return "docsystem.recepcao." + getIdentificador()
    }
}
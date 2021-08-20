package io.abner.docproc.recepcao.domain.port.input

import io.abner.docproc.recepcao.domain.evento.base.EventoResultadoSubmissaoDocumento
import io.smallrye.mutiny.Multi
import io.vavr.control.Either

interface FonteEventoResultadoSubmissaoDocumentoPort {
    fun obterStreamResultado(): Multi<EventoResultadoSubmissaoDocumento>
}
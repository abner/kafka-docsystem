package io.abner.docsystem.recepcao.domain.port.output

import io.abner.docsystem.recepcao.domain.evento.EventoResultadoSubmissaoDocumento
import io.smallrye.mutiny.Multi
import io.vavr.control.Either

interface FonteEventoResultadoSubmissaoDocumentoPort {
    fun obterStreamResultado(): Multi<EventoResultadoSubmissaoDocumento>
}
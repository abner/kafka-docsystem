package io.abner.docsystem.recepcao.domain.port.output

import io.abner.docsystem.recepcao.domain.evento.EventoSubmissaoDocumento
import io.smallrye.mutiny.Multi

interface FonteEventoSubmissaoDocumentoPort {
    fun obterStreamResultado(): Multi<EventoSubmissaoDocumento>
}
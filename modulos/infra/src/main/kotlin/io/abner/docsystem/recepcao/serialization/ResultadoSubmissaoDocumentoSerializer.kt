package io.abner.docsystem.recepcao.serialization

import io.abner.docsystem.recepcao.domain.evento.EventoSubmissaoDocumento
import io.abner.docsystem.recepcao.domain.model.ResultadoSubmissaoDocumento
import io.quarkus.kafka.client.serialization.ObjectMapperSerializer

class ResultadoSubmissaoDocumentoSerializer: ObjectMapperSerializer<ResultadoSubmissaoDocumento>()
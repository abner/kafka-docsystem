package io.abner.docsystem.recepcao.serialization

import io.abner.docsystem.recepcao.domain.model.ResultadoSubmissaoDocumento
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer

class ResultadoSubmissaoDocumentoDesserializer :
    ObjectMapperDeserializer<ResultadoSubmissaoDocumento>(ResultadoSubmissaoDocumento::class.java)
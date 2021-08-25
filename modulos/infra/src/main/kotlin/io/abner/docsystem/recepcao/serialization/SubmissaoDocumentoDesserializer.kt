package io.abner.docsystem.recepcao.serialization

import io.abner.docsystem.recepcao.domain.model.SubmissaoDocumento
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer

class SubmissaoDocumentoDesserializer :
    ObjectMapperDeserializer<SubmissaoDocumento>(SubmissaoDocumento::class.java)
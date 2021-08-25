package io.abner.docsystem.recepcao.serialization

import io.abner.docsystem.recepcao.domain.model.SubmissaoDocumento
import io.quarkus.kafka.client.serialization.ObjectMapperSerializer

class SubmissaoDocumentoSerializer : ObjectMapperSerializer<SubmissaoDocumento>()
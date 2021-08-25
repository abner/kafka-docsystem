package io.abner.docsystem.recepcao.web

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonProperty
import io.abner.docsystem.recepcao.domain.model.CommandSubmissaoDocumento
import io.vertx.core.json.Json
import java.util.*

data class SubmissaoDocumentoRequestBody(
    @JsonProperty
    val id: UUID,
    @JsonAnySetter
    @get:JsonAnyGetter
    val dados: Map<String, Any>? = mapOf()
) {
    fun toCommand(commandId: UUID, idUsuario: String): CommandSubmissaoDocumento {
        return CommandSubmissaoDocumento(
            id = commandId,
            idUsuario = idUsuario,
            idDocumento = id,
            conteudo = Json.encode(dados)
        )
    }
}

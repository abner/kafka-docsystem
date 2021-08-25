package io.abner.docsystem.recepcao.domain.service

import arrow.core.Either
import arrow.core.None
import io.abner.docsystem.recepcao.domain.evento.EventoSubmissaoDocumento
import io.abner.docsystem.recepcao.domain.model.CommandSubmissaoDocumento
import io.abner.docsystem.recepcao.domain.model.SubmissaoDocumento
import io.abner.docsystem.recepcao.domain.port.input.RecepcionarEventoDocumentoUseCase
import io.abner.docsystem.recepcao.domain.port.output.EmissorEventoSubmissaoPort
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecepcionarDocumentoService(
    @Inject @field:Default var emissorEventoSubmissaoPort: EmissorEventoSubmissaoPort
): RecepcionarEventoDocumentoUseCase {

    override suspend fun recepcionar(command: CommandSubmissaoDocumento): Either<Throwable, None> {
        return emissorEventoSubmissaoPort.enviar(
            evento = EventoSubmissaoDocumento(
                payload = SubmissaoDocumento(
                    id = command.id,
                    idDocumento = command.idDocumento,
                    idUsuario = command.idUsuario,
                    conteudo = command.conteudo
                )
            )
        )
    }
}
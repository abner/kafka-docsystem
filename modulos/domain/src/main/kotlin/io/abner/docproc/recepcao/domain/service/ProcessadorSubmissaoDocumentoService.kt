package io.abner.docproc.recepcao.domain.service

import io.abner.docproc.recepcao.domain.evento.base.EventoSubmissaoDocumento
import io.abner.docproc.recepcao.domain.port.input.ProcessarDocumentoRecebidoUseCase
import io.abner.docproc.recepcao.domain.port.output.RecuperarDocumentoPort
import io.abner.docproc.recepcao.domain.port.output.SalvarDocumentoPort
import io.vavr.control.Either
import java.util.*

class ProcessadorSubmissaoDocumentoService(
    val recuperarDocumentoPort: RecuperarDocumentoPort,
    val salvarDocumentoPort: SalvarDocumentoPort
) : ProcessarDocumentoRecebidoUseCase {

    override suspend fun executar(evento: EventoSubmissaoDocumento): Either<Throwable, Void> {
        val uuid = UUID.fromString(evento.getIdentificador())
        val eitherDocRecuperado = recuperarDocumentoPort.recuperar(uuid)


        if (eitherDocRecuperado.isRight) {
            if (!eitherDocRecuperado.get().podeSerSalvo()) {
                return Either.left(Exception("documento.nao-pode-ser-salvo"))
            }
            val doc = eitherDocRecuperado.get()
            doc.conteudo = evento.getPayload().conteudoDocumento

            return salvarDocumentoPort.salvar(doc)

        } else {
            return eitherDocRecuperado.map { _ -> null }
        }

    }
}
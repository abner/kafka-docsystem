package io.abner.docsystem.recepcao.domain.service

// import io.vavr.control.Either
import arrow.core.Either
import arrow.core.None
import arrow.core.flatMap
import io.abner.docsystem.recepcao.domain.evento.EventoSubmissaoDocumento
import io.abner.docsystem.recepcao.domain.model.DocumentoRegistrado
import io.abner.docsystem.recepcao.domain.port.input.ProcessarDocumentoRecebidoUseCase
import io.abner.docsystem.recepcao.domain.port.output.RecuperarDocumentoPort
import io.abner.docsystem.recepcao.domain.port.output.SalvarDocumentoPort
import jakarta.inject.Singleton

@Singleton
class ProcessadorSubmissaoDocumentoService(
    private val recuperarDocumentoPort: RecuperarDocumentoPort,
    private val salvarDocumentoPort: SalvarDocumentoPort,
) : ProcessarDocumentoRecebidoUseCase {

    override suspend fun executar(evento: EventoSubmissaoDocumento): Either<Throwable, None> {
        // recupera o documento
        val eitherDocRecuperado = recuperarDocumentoPort.recuperar(evento.getPayload().id)

        return eitherDocRecuperado.flatMap { documento ->
            // uma vez que foi recuperado sem fallha, checa se ele foi arquivado
            if (documento.encontrado && (documento as DocumentoRegistrado).arquivado) {
                return@flatMap Either.Left(Exception("documento.nao-pode-ser-salvo"))
            } else {
                val doc = (documento as DocumentoRegistrado)
                    .alterar(evento.getPayload().conteudoDocumento)

                return salvarDocumentoPort.salvar(doc)
            }
        }
    }
}
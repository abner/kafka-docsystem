package io.abner.docsystem.recepcao.domain.service

// import io.vavr.control.Either
import arrow.core.Either
import arrow.core.None
import arrow.core.flatMap
import io.abner.docsystem.recepcao.domain.evento.EventoResultadoSubmissaoDocumento
import io.abner.docsystem.recepcao.domain.evento.EventoSubmissaoDocumento
import io.abner.docsystem.recepcao.domain.model.DocumentoRegistrado
import io.abner.docsystem.recepcao.domain.model.ResultadoSubmissaoDocumento
import io.abner.docsystem.recepcao.domain.port.input.ProcessarDocumentoRecebidoUseCase
import io.abner.docsystem.recepcao.domain.port.output.EmissorEventoSubmissaoProcessadaPort
import io.abner.docsystem.recepcao.domain.port.output.RecuperarDocumentoPort
import io.abner.docsystem.recepcao.domain.port.output.SalvarDocumentoPort
import javax.inject.Singleton

@Singleton
class ProcessadorSubmissaoDocumentoService(
    private val recuperarDocumentoPort: RecuperarDocumentoPort,
    private val salvarDocumentoPort: SalvarDocumentoPort,
    private val emissorEventoSubmissaoProcessadaPort: EmissorEventoSubmissaoProcessadaPort

) : ProcessarDocumentoRecebidoUseCase {

    override suspend fun executar(evento: EventoSubmissaoDocumento): Either<Throwable, None> {
        // recupera o documento
        val eitherDocRecuperado = recuperarDocumentoPort.recuperar(
            evento.getPayload().idDocumento
        )
        return eitherDocRecuperado.flatMap { documento ->
            // uma vez que foi recuperado sem fallha, checa se ele foi arquivado
            if (documento.encontrado && documento.podeSerSalvo()) {
                val doc = (documento as DocumentoRegistrado)
                    .alterar(evento.getPayload().conteudo)

                val resultado = salvarDocumentoPort.salvar(doc)
                emissorEventoSubmissaoProcessadaPort.enviar(
                    EventoResultadoSubmissaoDocumento(
                        ResultadoSubmissaoDocumento(
                            idSolicitacaoAtualizacaoDocumento = evento.getPayload().id,
                            sucesso = true,
                            idDocumento = evento.getPayload().idDocumento
                        )
                    )
                )
                resultado
            } else {
                emissorEventoSubmissaoProcessadaPort.enviar(
                    EventoResultadoSubmissaoDocumento(
                        ResultadoSubmissaoDocumento(
                            idSolicitacaoAtualizacaoDocumento = evento.getPayload().id,
                            sucesso = false,
                            idDocumento = evento.getPayload().idDocumento,
                            detalhe = "documento.nao-pode-ser-salvo"
                        )
                    )
                )
                return@flatMap Either.Left(Exception("documento.nao-pode-ser-salvo"))
            }
        }
    }
}
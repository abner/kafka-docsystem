package io.abner.docsystem.recepcao.domain.service

import arrow.core.Either
import arrow.core.None
import io.abner.docsystem.recepcao.domain.evento.EventoSubmissaoDocumento
import io.abner.docsystem.recepcao.domain.model.DocumentoRegistrado
import io.abner.docsystem.recepcao.domain.model.SubmissaoDocumento
import io.abner.docsystem.recepcao.domain.port.output.EmissorEventoSubmissaoPort
import io.abner.docsystem.recepcao.domain.port.output.EmissorEventoSubmissaoProcessadaPort
import io.abner.docsystem.recepcao.domain.port.output.RecuperarDocumentoPort
import io.abner.docsystem.recepcao.domain.port.output.SalvarDocumentoPort
import io.abner.docsystem.recepcao.domain.service.ProcessadorSubmissaoDocumentoService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*


class ProcessadorSubmissaoDocumentoServiceTest {

    private lateinit var servico: ProcessadorSubmissaoDocumentoService
    private val idDocumento: UUID = UUID.fromString("be3abe68-1f79-49fa-9d9c-dd87ceb3f4af")
    private val idSubmissao: UUID = UUID.randomUUID()
    private val documentoPersistido = DocumentoRegistrado(
        id =idDocumento,
        arquivado = false,
        descricao = "Documento da pessoa 123",
        idPessoa = "000123",
        dados = """{}""".trimIndent()
    )
    private lateinit var recuperarDocumentoPort: RecuperarDocumentoPort
    private lateinit var salvarDocumentoPort: SalvarDocumentoPort
    private lateinit var emissorEventoSubmissaoProcessadaPort: EmissorEventoSubmissaoProcessadaPort
    @BeforeEach
    fun setup() {
        // preparação mock port RecuperarDocumento
        recuperarDocumentoPort = mockk<RecuperarDocumentoPort>()
        coEvery {
            recuperarDocumentoPort.recuperar(idDocumento)
        } returns Either.Right(documentoPersistido)

        val emptyResult: Either<Throwable, None> = Either.Right(None)

        // preparação do mock do "port" SalvarDocumento
        salvarDocumentoPort = mockk<SalvarDocumentoPort>()
        coEvery {
            salvarDocumentoPort.salvar(any())
        } returns emptyResult

        emissorEventoSubmissaoProcessadaPort = mockk<EmissorEventoSubmissaoProcessadaPort>()
        coEvery {
            emissorEventoSubmissaoProcessadaPort.enviar(any())
        } returns emptyResult
        // criando a instancia do ProcessadorSubmissaoDocumentoService
        // passando os mocks via construtor
        servico = ProcessadorSubmissaoDocumentoService(
            recuperarDocumentoPort,
            salvarDocumentoPort,
            emissorEventoSubmissaoProcessadaPort
        )
    }

    @Test
    fun testarProcessarSubmissao() = runBlockingTest {
        servico.executar(evento = EventoSubmissaoDocumento(
            payload = SubmissaoDocumento(
                id = idSubmissao,
                idDocumento = idDocumento,
                idUsuario = "000332",
                conteudo = """{
                    |"tipo": "ESCRITURACAO",
                    |"uf": "BA"
                    |}""".trimMargin()
            )
        ))

        // verificações
        coVerify(exactly = 1) {
            recuperarDocumentoPort
                .recuperar(eq(idDocumento))
        }

        // verificações
        coVerify(exactly = 1) {
            salvarDocumentoPort.salvar(any())
        }

    }
}
package io.abner.docsystem.recepcao.web

import arrow.core.Either
import arrow.core.None
import io.abner.docsystem.recepcao.adapter.kafka.EmissaoCanalEventoSubmissaoDocumento
import io.abner.docsystem.recepcao.adapter.kafka.RecepcaoCanalEventoSubmissaoDocumento
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.common.http.TestHTTPResource
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import io.quarkus.test.security.TestSecurity
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.filter.log.LogDetail
import kotlinx.coroutines.test.runBlockingTest
import org.apache.http.HttpStatus
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.net.URL
import java.util.*
import javax.ws.rs.core.MediaType
import kotlin.test.assertEquals


@QuarkusTest
@TestHTTPEndpoint(DocumentoResource::class)
class DocumentoResourceTest {

    @TestHTTPEndpoint(DocumentoResource::class)
    @TestHTTPResource
    lateinit var url: URL

    @InjectMock
    lateinit var emissaoCanalEventoSubmissaoDocumento: EmissaoCanalEventoSubmissaoDocumento


    @Test
    @TestSecurity(user = "joao.gerente", roles = ["admin", "user"])
    fun `deve retornar 204_CREATED ao submeter com usuario logado`() = runBlockingTest {
        Mockito.`when`(
            emissaoCanalEventoSubmissaoDocumento.enviar(any())
        ).thenReturn(Either.Right(None))
        val idDcoumento = UUID.randomUUID()

        val httpResponse = given()
            .`when`()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(SubmissaoDocumentoRequestBody(
                id = idDcoumento,
                mapOf("descricao" to "Documento XPTO")
            ))
            .post()
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .response()

        val body: SubmissaoDocumentoResponseBody = httpResponse.body.`as`<SubmissaoDocumentoResponseBody>(SubmissaoDocumentoResponseBody::class.java) as SubmissaoDocumentoResponseBody
        val locationHeader = httpResponse.header("Location")
        assertEquals("/api/v1/submissao-documento/${body.idSubmissao}", locationHeader)
    }

    @Test
    fun `verificar o path`() {
        assertEquals("/api/v1/documento", url.path)
    }

    @Test
    @TestSecurity(user = "joao.gerente", roles = ["admin", "user"])
    fun testarObterReadyAutenticado() {
        given()
            //.pathParam("name", uuid)
            .`when`().get("/ready/auth")
            .then()
            .statusCode(200)
            .body(`is`("OK - Oi usuario autenticado: joao.gerente"))
    }

    @Test
    fun `retorna status code 401 se não tiver autenticado`() {

        given()
            .`when`().get("/ready/auth")
            .then()
            .statusCode(401)
    }

    @Test
    fun `deve retornar 401 se submeter documento sem ser usuario logado`() = runBlockingTest {
        val idDcoumento = UUID.randomUUID()
        given()
            .`when`()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(SubmissaoDocumentoRequestBody(
                id = idDcoumento,
                mapOf("descricao" to "Documento XPTO")
            ))
            .post()
            .then()
            .statusCode(401)
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun setupAll() {
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
        }
    }
}

/**
 * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
 * null is returned.
 */
fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

@Suppress("UNCHECKED_CAST")
fun <T> uninitialized(): T = null as T

fun <T> notNull(): T {
    Mockito.notNull<T>()
    return uninitialized()
}

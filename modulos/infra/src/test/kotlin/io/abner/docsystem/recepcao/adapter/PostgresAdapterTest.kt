package io.abner.docsystem.recepcao.adapter

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import java.util.*
import javax.inject.Inject
import javax.transaction.Transactional

@QuarkusTest
class PostgresAdapterTest {

    @Inject
    lateinit var postgresAdapter: PostgresAdapter

    @Test
    fun recuperar() = runBlocking {

        // withContext(Dispatchers.IO + RequestContextElement(requestContextController)) {
        postgresAdapter shouldNotBe null

        val idDoc = UUID.fromString("be3abe68-1f79-49fa-9d9c-dd87ceb3f4af")
        val eitherDocumentoRecuperado = postgresAdapter
            .recuperar(idDoc)

        eitherDocumentoRecuperado.map { documento ->
            documento.encontrado shouldBe true
            documento.podeSerSalvo() shouldBe true
            documento.id shouldBe idDoc
        }

        print("IS LEFT...")
        print(eitherDocumentoRecuperado.isLeft())
        //}

    }

    @Test
    fun salvar() = runBlockingTest {
    }
}

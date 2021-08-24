package io.abner.docsystem.recepcao.adapter

import arrow.core.Either
import arrow.core.None
import io.abner.docsystem.recepcao.domain.model.Documento
import io.abner.docsystem.recepcao.domain.model.DocumentoNaoEncontrado
import io.abner.docsystem.recepcao.domain.model.DocumentoRegistrado
import io.abner.docsystem.recepcao.domain.model.exception.FalhaLeituraDocumento
import io.abner.docsystem.recepcao.domain.model.exception.NenhumDocumentoAtualizado
import io.abner.docsystem.recepcao.domain.port.output.RecuperarDocumentoPort
import io.abner.docsystem.recepcao.domain.port.output.SalvarDocumentoPort
import io.quarkus.runtime.StartupEvent
import io.smallrye.mutiny.coroutines.awaitSuspending
import io.vertx.mutiny.pgclient.PgPool
import io.vertx.mutiny.sqlclient.Row
import io.vertx.mutiny.sqlclient.SqlConnection
import io.vertx.mutiny.sqlclient.Tuple
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.enterprise.inject.Default
import javax.inject.Inject

@ApplicationScoped
class PostgresAdapter : RecuperarDocumentoPort, SalvarDocumentoPort {

    @Inject @field:Default
    lateinit var pgPool: PgPool

    @ConfigProperty(name = "quarkus.profile")
    lateinit var currentProfile: Optional<String>

    fun inicializar(@Observes startupEvent: StartupEvent) {
        if (currentProfile.orElse("prod") == "dev") {
            //val connection = pgPool.connection.await().indefinitely()
            pgPool.query(PostgresDB.createDatabaseSQL).execute()
                .flatMap { _ -> pgPool.query(PostgresDB.registrosDocumentosSQL).execute() }
                .await().indefinitely()
        }
    }


    override suspend fun recuperar(id: UUID): Either<Throwable, Documento> {
        var connection: SqlConnection? = null

        try {
            connection = pgPool.connection.awaitSuspending()
            val rsResult = connection.preparedQuery("""
                select id, idpessoa, ano, descricao, dados,arquivado
                from 
                    documento
                where
                    id = $1
            """.trimIndent())
                .execute(Tuple.of(id))
                .awaitSuspending()

            if (rsResult.size() == 0) {
                return Either.Right(DocumentoNaoEncontrado(id))
            }
            // converte o row (linha do banco de dados) em uma
            // instância de "Documento"
            return rsResult.first().let(this::lerRegistroDocumento)

        } catch (e: Throwable) {
            return Either.Left(e)
        } finally {
            connection?.close()
        }
    }

    override suspend fun salvar(documento: DocumentoRegistrado): Either<Throwable, None> {
        var connection: SqlConnection? = null

        try {
            connection = pgPool.connection.awaitSuspending()
            val rsResult = connection.preparedQuery("""
                UPDATE documento
                   set dados = $2
                where
                    id = $1
            """.trimIndent())
                .execute(Tuple.of(documento.id, documento.dados))
                .awaitSuspending()

            if (rsResult.rowCount() == 0) {
                return Either.Left(NenhumDocumentoAtualizado())
            }

            return Either.Right(None)

        } catch (e: Exception) {
            return Either.Left(e)
        } finally {
            connection?.close()
        }
    }

    private fun lerRegistroDocumento(row: Row): Either<Throwable, Documento> {
        return try {
            val documento = DocumentoRegistrado(
                id = row.getUUID("id"),
                descricao = row.getString("descricao"),
                dados = row.getJsonObject("dados").encode(),
                arquivado = row.getBoolean("arquivado")
            )
            Either.Right(documento)
        } catch (e: Throwable) {
            Either.Left(FalhaLeituraDocumento(e))
        }
    }
}


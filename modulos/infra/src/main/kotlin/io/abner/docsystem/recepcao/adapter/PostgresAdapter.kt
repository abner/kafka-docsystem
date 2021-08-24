package io.abner.docsystem.recepcao.adapter

import arrow.core.Either
import arrow.core.None
import io.abner.docsystem.recepcao.domain.model.Documento
import io.abner.docsystem.recepcao.domain.model.DocumentoEncontrado
import io.abner.docsystem.recepcao.domain.model.DocumentoNaoEncontrado
import io.abner.docsystem.recepcao.domain.model.exception.FalhaLeituraDocumento
import io.abner.docsystem.recepcao.domain.port.output.RecuperarDocumentoPort
import io.abner.docsystem.recepcao.domain.port.output.SalvarDocumentoPort
import io.smallrye.mutiny.coroutines.awaitSuspending

import io.vertx.mutiny.pgclient.PgPool
import io.vertx.mutiny.sqlclient.Row
import io.vertx.mutiny.sqlclient.RowSet
import io.vertx.mutiny.sqlclient.SqlConnection
import io.vertx.mutiny.sqlclient.Tuple
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class PostgresAdapter : RecuperarDocumentoPort, SalvarDocumentoPort {
    @Inject
    lateinit var pgPool: PgPool
    override suspend fun recuperar(id: UUID): Either<Throwable, Documento> {
        var connection: SqlConnection? = null

        try {
            connection = pgPool.connection.awaitSuspending()
            val rsResult = connection.preparedQuery("""
                select id, idpessoa, ano, descricao, dados
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

        } catch (e: Exception) {
            return Either.Left(e)
        } finally {
            connection?.close()
        }
    }

    override suspend fun salvar(documento: Documento): Either<Throwable, None> {
        TODO("Not yet implemented")
    }

    private fun lerRegistroDocumento(row: Row): Either<Throwable, Documento> {
        return try {
            val documento = DocumentoEncontrado(
                id = row.getUUID("id"),
                descricao = row.getString("descricao"),
                dados = row.getString("dados"),
                arquivado = row.getBoolean("arquivado")
            )
            Either.Right(documento)
        } catch (e: Throwable) {
            Either.Left(FalhaLeituraDocumento(e))
        }
    }
}

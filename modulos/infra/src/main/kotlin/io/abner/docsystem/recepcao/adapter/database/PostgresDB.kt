package io.abner.docsystem.recepcao.adapter.database

object PostgresDB {
    val createDatabaseSQL ="""
        DROP TABLE IF EXISTS documento;
        CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
        CREATE TABLE public.documento (
            id uuid NOT NULL DEFAULT uuid_generate_v4(),
            idpessoa varchar(6) NOT NULL,
            ano int NOT NULL,
            descricao varchar(255) NULL,
            dados jsonb NULL,
            arquivado bool NOT NULL DEFAULT false,
            data_hora_ultima_alteracao timestamptz,
            CONSTRAINT documento_pkey PRIMARY KEY (id)
        );
        CREATE INDEX documento_idpessoa_ano_idx ON public.documento USING btree (idpessoa, ano);
        CREATE INDEX dadosgin ON documento USING gin (dados);
        CREATE OR REPLACE FUNCTION public.atualizar_data_hora_ultima_alteracao()
         RETURNS trigger
         LANGUAGE plpgsql
        AS ${"\$function\$"}
        BEGIN
            NEW.data_hora_ultima_alteracao = now();
            RETURN NEW;
        END;
        ${"\$function\$"}
    """.trimIndent()

    val registrosDocumentosSQL = """
        INSERT INTO documento(idpessoa, ano, descricao, dados) VALUES (
        '000121', 2018, 'Certidao 000121', '{"tipo":"CERTIDAO", "clausula": "valida", "permanencia": "vitalicia"}'
        );

        INSERT INTO documento(id, idpessoa, ano, descricao, dados)
        VALUES ('be3abe68-1f79-49fa-9d9c-dd87ceb3f4af','000332', 2019, 'Autorização 000332','{"tipo":"AUTORIZACAO", "clausula": "invalida", "permanencia": "temporaria"}');
    """.trimIndent()
}
package io.abner.docsystem.recepcao.domain.model.exception

class PayloadInvalidoException : Exception() {
    private val erros: MutableList<PayloadAtributoException> = ArrayList()
    fun erro(nomeEvento: String, atributo: String, mensagem: String): PayloadInvalidoException {
        erros.add(PayloadAtributoException(nomeEvento, atributo, mensagem))
        return this
    }

    fun adicionarFalha(erro: PayloadAtributoException) {
        erros.add(erro)
    }

    fun possuiErros(): Boolean {
        return erros.size > 0
    }

    override fun toString(): String {
        return "PayloadInvalidoException{" +
                "erros=" + erros +
                '}'
    }

    data class PayloadAtributoException(
        val nomeEvento: String, val atributo: String, val mensagem: String,
    ) {

        override fun toString(): String {
            return "PayloadInvalidoException{" +
                    "nomeEvento='" + nomeEvento + '\'' +
                    ", atributo='" + atributo + '\'' +
                    ", message='" + mensagem + '\'' +
                    '}'
        }
    }
}
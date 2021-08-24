package io.abner.docsystem.recepcao.domain.model.exception

class FalhaLeituraDocumento(private val e: Throwable) : ApplicationException(e)
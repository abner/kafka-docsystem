package io.abner.docsystem.recepcao.domain.model.exception

open class ApplicationException(private val e: Throwable? = null) : Throwable(e)
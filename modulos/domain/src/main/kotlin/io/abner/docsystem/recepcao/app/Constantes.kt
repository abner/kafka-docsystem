package io.abner.docsystem.recepcao.app

import java.time.ZoneId
import java.time.ZoneOffset

object Constantes {
    //public static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("America/Sao_Paulo");
    @JvmStatic
    val ZONE_OFFSET: ZoneOffset = ZoneOffset.UTC
    @JvmStatic
    val ZONE_ID: ZoneId = ZoneOffset.of(ZONE_OFFSET.id)
}
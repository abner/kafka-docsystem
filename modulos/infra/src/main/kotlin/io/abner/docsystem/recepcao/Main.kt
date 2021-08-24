package io.abner.docsystem.recepcao

import io.abner.docsystem.recepcao.adapter.PostgresAdapter
import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


fun main(args: Array<String>) {
    Quarkus.run(QuarkusApp::class.java, *args)
}

@QuarkusMain
class QuarkusApp: QuarkusApplication {
    @Inject
    lateinit var pgPostgresAdapter: PostgresAdapter
    @OptIn(DelicateCoroutinesApi::class)
    override fun run(vararg args: String?): Int {

        GlobalScope.launch {
            val idDoc = UUID.fromString("be3abe68-1f79-49fa-9d9c-dd87ceb3f4af")

            val resDoc = pgPostgresAdapter.recuperar(idDoc)
            println("RIGHT: ${resDoc.isRight()}")

            resDoc.map {
                println(it.toString())
            }
        }

        Quarkus.waitForExit();



        return 0
    }

}
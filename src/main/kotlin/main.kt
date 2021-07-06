import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    exampleOf("filter operator")

    forceUsers.asFlow()
        .filter { it is Jedi }
        .collect { forceUser -> log(forceUser.name) }

    exampleOf("map operator")

    suspend fun bestowSithTitle(forceUser: ForceUser): String {
        delay(DELAY)
        return "Darth ${forceUser.name}"
    }

    val sith = forceUsers.asFlow()
        .filter { it is Sith }
        .map { bestowSithTitle(it) }

    sith.collect { log(it) }

    exampleOf("transform operator")

    forceUsers.asFlow()
        .transform { forceUser ->
            if (forceUser is Sith) {
                emit("Turning ${forceUser.name} to the Dark Side")
                emit(bestowSithTitle(forceUser))
            }
        }
        .collect { log(it) }

    exampleOf("size-limiting operators")

    sith.take(2).collect { log(it) }

    exampleOf("terminal operators")

    val jediLineage = forceUsers.asFlow()
        .filter { it is Jedi }
        .map { it.name }
        .reduce { a,b -> "$a trained by $b" }

    log(jediLineage)
}




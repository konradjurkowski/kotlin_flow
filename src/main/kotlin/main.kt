import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun jediTrainess(): Flow<ForceUser> = forceUsers.asFlow()
    .transform {
        if (it is Padawan) {
            delay(DELAY)
            emit(it)
        }
    }

fun main() = runBlocking {

    var time = measureTimeMillis {
        jediTrainess()
            .collect {
               delay(3 * DELAY)
                log("Jedi ${it.name} is now a Jedi Master")
            }
    }

    log("Total time: $time ms")

    exampleOf("buffer")

    time = measureTimeMillis {
        jediTrainess()
            .buffer()
            .collect {
                delay(3 * DELAY)
                log("Jedi ${it.name} is now a Jedi Master")
            }
    }

    log("Total time: $time ms")

    exampleOf("conflate")

    time = measureTimeMillis {
        jediTrainess()
            .conflate()
            .collect {
                delay(3 * DELAY)
                log("Jedi ${it.name} is now a Jedi Master")
            }
    }

    log("Total time: $time ms")

    exampleOf("collectLatest")

    time = measureTimeMillis {
        jediTrainess()
            .collectLatest  {
                log("Jedi Master training for ${it.name}")
                delay(3 * DELAY)
                log("Jedi ${it.name} is now a Jedi Master")
            }
    }

    log("Total time: $time ms")
}




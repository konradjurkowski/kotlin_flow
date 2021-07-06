import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    exampleOf("Sequence (blocks main thread)")

    fun prequels(): Sequence<String> = sequence {
        for (movie in listOf(episodeI, episodeII, episodeIII)) {
            Thread.sleep(DELAY)
            yield(movie)
        }
    }

    prequels().forEach { movie -> log(movie) }

    log("Something else to be done here.")

    exampleOf("Suspending function (asynchronous)")

    suspend fun originals(): List<String> {
        delay(DELAY)
        return listOf(episodeIV, episodeV, episodeVI)
    }

    launch {
        originals().forEach { movie -> log(movie) }
    }

    log("Something else to be done here.")

    delay(2 * DELAY)
    exampleOf("Simple flow")

    fun sequels(): Flow<String> = flow {
        for (movie in listOf(episodeVII, episodeVIII, episodeIX)) {
            delay(DELAY)
            emit(movie)
        }
    }

    launch {
        sequels().collect { movie -> log(movie) }
    }

    launch {
        for (i in 1..3) {
            log("Not blocked $i")
            delay(DELAY)
        }
    }

    log("Something else to be done here.")

    delay(4 * DELAY)
    exampleOf("Cold stream")

    val sequelFilms = sequels()

    sequelFilms.collect { movie -> log(movie) }

    delay(3 * DELAY)
    exampleOf("Collecting again")

    sequelFilms.collect { movie -> log(movie) }
}



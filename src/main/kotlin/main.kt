import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main() = runBlocking {

    fun starsWarsSounds() = flow {
        for (sound in listOf(chewieSound, r2d2Sound, blasterSound, saberSound)) {
            delay(DELAY)
            log("Emitting $sound")
            emit(sound)
        }
    }

    withTimeoutOrNull((3.1 * DELAY).toLong()) {
        starsWarsSounds().collect {  sound -> log(sound) }
    }

    log("Done emitting sounds!")
}



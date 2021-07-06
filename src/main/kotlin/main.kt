import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    fun duelOfTheFates(): Flow<ForceUser> = flow {
        for (forceUser in forceUsers) {
            delay(DELAY)
            log("Battling ${forceUser.name}")
            emit(forceUser)
        }
    }.transform { forceUser ->
        if (forceUser is Sith) {
            forceUser.name = "Darth ${forceUser.name}"
        }
        emit(forceUser)
    }.flowOn(Dispatchers.Default)

    duelOfTheFates().collect {
        log("Battled ${it.name}")
    }
}




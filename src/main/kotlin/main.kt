import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun midichlorianTest(): Flow<Int> = flow {
    for (key in midichlorianCounts.keys) {
        log("Testing $key")
        delay(DELAY)
        emit(midichlorianCounts[key] ?: 0)
    }
}

fun midichlorianTestString(): Flow<String> =
    flow<Int> {
        for (key in midichlorianCounts.keys) {
            log("Testing $key")
            delay(DELAY)
            emit(midichlorianCounts[key] ?: 0)
        }
    }.map { testResult ->
        check(testResult <= CHOSEN_ONE_THRESHOLD) { "Test Result: $testResult" }
        "$testResult"
    }

fun main() = runBlocking {
    exampleOf("Catching exceptional condition")

    try {
        midichlorianTest().collect {
            log("$it")
            check(it <= CHOSEN_ONE_THRESHOLD) { "Test Result: $it" }
        }
    } catch (error: Throwable) {
        log("Could be the chosen one! ::: $error")
    }

    exampleOf("Catching from intermediate operator")

    try {
        midichlorianTestString().collect { log("$it") }
    } catch(error: Throwable) {
        log("Could be the chosen one! ::: $error")
    }

    exampleOf("Exception transparency")

    midichlorianTest()
        .catch { e -> log("Exception caught: $e") }
        .collect {
            check(it <= CHOSEN_ONE_THRESHOLD) { "Test Result: $it" }
            log("$it")
        }

    exampleOf("Catching declaratively")

    midichlorianTest()
        .onEach {
            check(it <= CHOSEN_ONE_THRESHOLD) { "Test Result: $it" }
            log("$it")
        }
        .catch { e -> log("Exception caught: $e") }
        .collect()
}




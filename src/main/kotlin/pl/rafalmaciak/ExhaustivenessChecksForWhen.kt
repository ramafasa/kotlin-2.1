package pl.rafalmaciak

object ExhaustivenessChecksForWhen {

    sealed class HttpRequestResult {
        data class Success(val code: Int, val body: String) : HttpRequestResult()
        data class Error(val code: Int, val errorMessage: String) : HttpRequestResult()
        object Timeout : HttpRequestResult()
        class ConnectionError() : HttpRequestResult()
    }


    fun <T: HttpRequestResult> processHttpRequestResult(response: T): String =
        when (response  ) {
            is HttpRequestResult.Success -> "Success with code ${response.code} and body: ${response.body}"
            is HttpRequestResult.Error -> "Error returned by server ${response.code}: ${response.errorMessage}"
            is HttpRequestResult.Timeout -> "Connection Timeout"
            is HttpRequestResult.ConnectionError -> "Connection error"
            // 'when' expression must be exhaustive. Add an 'else' branch. (before Kotlin 2.1)
        }
    
}

fun main() {
    val responseSuccess: ExhaustivenessChecksForWhen.HttpRequestResult = ExhaustivenessChecksForWhen.HttpRequestResult.Success(200, "Hello, world!")
    val response400: ExhaustivenessChecksForWhen.HttpRequestResult = ExhaustivenessChecksForWhen.HttpRequestResult.Error(400, "Bad request!")
    println(ExhaustivenessChecksForWhen.processHttpRequestResult(responseSuccess))
    println(ExhaustivenessChecksForWhen.processHttpRequestResult(response400))
}
package pl.rafalmaciak


sealed class HttpRequestResult {
    data class Success(val code: Int, val body: String) : HttpRequestResult()
    data class Error(val code: Int, val errorMessage: String) : HttpRequestResult()
    object Timeout : HttpRequestResult()
    class ConnectionError() : HttpRequestResult()
}

fun processHttpRequestResultWithGuard(response: HttpRequestResult): String =
    when (response) {
        is HttpRequestResult.Success -> "Success with code ${response.code} and body: ${response.body}"
        is HttpRequestResult.Error if response.code in (400..499) -> "Client's error ${response.code}: ${response.errorMessage}"
        is HttpRequestResult.Error if response.code in (500..599) -> "Server's error ${response.code}: ${response.errorMessage}"
        is HttpRequestResult.Error -> "Other error returned by server ${response.code}: ${response.errorMessage}"
        is HttpRequestResult.Timeout -> "Connection Timeout"
        is HttpRequestResult.ConnectionError -> "Connection error"
    }

fun processHttpRequestResult(response: HttpRequestResult): String =
    when (response) {
        is HttpRequestResult.Success -> "Success with code ${response.code} and body: ${response.body}"
        is HttpRequestResult.Error -> {
            when (response.code) {
                in 400..499 -> "Client's error ${response.code}: ${response.errorMessage}"
                500 -> "Server's error ${response.code}: ${response.errorMessage}"
                else -> "Other error returned by server ${response.code}: ${response.errorMessage}"
            }
        }
        is HttpRequestResult.Timeout -> "Connection Timeout"
        is HttpRequestResult.ConnectionError -> "Connection error"
    }

fun main() {
    val responseSuccess: HttpRequestResult = HttpRequestResult.Success(200, "Hello, world!")
    val response400: HttpRequestResult = HttpRequestResult.Error(400, "Bad request!")
    println(processHttpRequestResultWithGuard(responseSuccess))
    println(processHttpRequestResultWithGuard(response400))
}
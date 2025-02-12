package pl.rafalmaciak


sealed class HttpResponse {
    data class Success(val code: Int, val body: String) : HttpResponse()
    data class Error(val code: Int, val errorMessage: String) : HttpResponse()
    object Timeout : HttpResponse()
    class ConnectionError() : HttpResponse()
}

fun processResponseWithGuardCondition(response: HttpResponse): String =
    when (response) {
        is HttpResponse.Success -> "Success with code ${response.code} and body: ${response.body}"
        is HttpResponse.Error if response.code in (400..499) -> "Client's error ${response.code}: ${response.errorMessage}"
        is HttpResponse.Error if response.code in (500..599) -> "Server's error ${response.code}: ${response.errorMessage}"
        is HttpResponse.Error -> "Other error returned by server ${response.code}: ${response.errorMessage}"
        is HttpResponse.Timeout -> "Connection Timeout"
        is HttpResponse.ConnectionError -> "Connection error"
    }

fun processResponse(response: HttpResponse): String =
    when (response) {
        is HttpResponse.Success -> "Success with code ${response.code} and body: ${response.body}"
        is HttpResponse.Error -> {
            when (response.code) {
                in 400..499 -> "Client's error ${response.code}: ${response.errorMessage}"
                500 -> "Server's error ${response.code}: ${response.errorMessage}"
                else -> "Other error returned by server ${response.code}: ${response.errorMessage}"
            }
        }
        is HttpResponse.Timeout -> "Connection Timeout"
        is HttpResponse.ConnectionError -> "Connection error"
    }

fun main() {
    val responseSuccess: HttpResponse = HttpResponse.Success(200, "Hello, world!")
    val response400: HttpResponse = HttpResponse.Error(400, "Bad request!")
    println(processResponseWithGuardCondition(responseSuccess))
    println(processResponseWithGuardCondition(response400))
}
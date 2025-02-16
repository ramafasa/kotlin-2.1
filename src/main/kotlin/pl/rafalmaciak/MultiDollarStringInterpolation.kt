package pl.rafalmaciak


const val fileRefType = "string"
val jsonSchema_beforeKotlin2_1_0 = """
    {
        "${'$'}id" : "https://example/schema/json"
        "items" : {
            "${'$'}ref" : "#/${'$'}defs/exampleReference",
        },
        "${'$'}defs" : {
            "fileRef" : {
                "exampleReference" : "$fileRefType",
            }
        }
    }
""".trimIndent()

val jsonSchema_multiDollarStringInterpolation = $$"""
    {
        "$id" : "https://example/schema/json"
        "items" : {
            "$ref" : "#/$defs/exampleReference",
        },
        "$defs" : {
            "fileRef" : {
                "exampleReference" : "$$fileRefType",
            }
        }
    }
""".trimIndent()

fun main() {
    println(jsonSchema_beforeKotlin2_1_0)
    println(jsonSchema_multiDollarStringInterpolation)
}
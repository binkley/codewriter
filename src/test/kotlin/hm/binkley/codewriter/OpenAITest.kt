package hm.binkley.codewriter

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

// Setup Ktor HTTP client
private val client = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

// OpenAI request/response models
@Serializable
data class OpenAIRequest(
    val model: String = "gpt-4",
    val messages: List<Message>,
    val max_tokens: Int = 50
)

@Serializable
data class Message(
    val role: String,
    val content: String
)

@Serializable
data class OpenAIResponse(
    val choices: List<Choice>
)

@Serializable
data class Choice(
    val message: Message
)

class OpenAITest {
    @Disabled("Turn on when API key is available")
    @Test
    fun `should receive a response from OpenAI API`() = runBlocking {
        val apiKey = System.getenv("OPENAI_API_KEY") ?: error("Missing API key")

        val response: HttpResponse =
            client.post("https://api.openai.com/v1/chat/completions") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $apiKey")
                    append(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json
                    )
                }
                setBody(
                    OpenAIRequest(
                        messages = listOf(
                            Message(
                                role = "user",
                                content = "Hello, OpenAI!"
                            )
                        )
                    )
                )
            }

        assertTrue(
            response.status == HttpStatusCode.OK,
            "Expected HTTP 200 OK, got ${response.status}"
        )

        val openAIResponse: OpenAIResponse = response.body()
        val reply = openAIResponse.choices.firstOrNull()?.message?.content ?: ""

        assertTrue(
            reply.isNotBlank(),
            "Expected a non-blank response from OpenAI"
        )

        println("OpenAI Response: $reply")
    }
}

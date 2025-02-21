package hm.binkley.codewriter

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MainTest {
    @Test
    fun `cmd line should do nothing without complaint`() {
        val output = tapSystemOut { main(emptyArray()) }

        // Assert that the program produces no output
        assertEquals("", output)
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun disableLogging() {
            // Disable Spring Boot logging
            System.setProperty("logging.level.root", "OFF")
        }
    }
}

package hm.binkley.codewriter

import com.github.stefanbirkner.systemlambda.SystemLambda.restoreSystemProperties
import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MainTest {
    @Test
    fun `cmd line should do nothing without complaint`() {
        restoreSystemProperties() {
            System.setProperty("logging.level.root", "OFF")
            val output = tapSystemOut { main(emptyArray()) }

            assertEquals("\n", output)
        }
    }

    @Test
    fun `cmd line produce one line for AI`() {
        restoreSystemProperties() {
            System.setProperty("logging.level.root", "OFF")
            val output = tapSystemOut {
                main(
                    arrayOf(
                        "I",
                        "am",
                        " the ",
                        "very ",
                        "model."
                    )
                )
            }

            assertEquals("I am the very model.\n", output)
        }
    }
}

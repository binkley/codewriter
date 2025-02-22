package hm.binkley.codewriter

import hm.binkley.codewriter.CodeWriter.writeMainSource
import hm.binkley.codewriter.CodeWriter.writeTestSource
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Do not check that files are written into the local project git root.
 * This would mess up your local files.
 */
class CodeWriterTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun `write a new main source file`() {
        val content = "Major modern general."
        writeMainSource(
            packageName = "bob.uncle",
            fileName = "Foo",
            content = content,
            gitRoot = tempDir
        )

        val newPath = tempDir.resolve("src/main/kotlin/bob/uncle/Foo.kt")
        assertTrue(Files.exists(newPath))
        assertEquals(content, Files.readString(newPath))
    }

    @Test
    fun `write a new test source file`() {
        val content = "Test the general."
        writeTestSource(
            packageName = "bob.uncle",
            fileName = "FooTest",
            content = content,
            gitRoot = tempDir
        )

        val newPath = tempDir.resolve("src/test/kotlin/bob/uncle/FooTest.kt")
        assertTrue(Files.exists(newPath))
        assertEquals(content, Files.readString(newPath))
    }
}

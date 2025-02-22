package hm.binkley.codewriter

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

/**
 * TODO: This assumes Kotlin code writen into main/test source roots.
 *  It needs to be more general to update build dependencies and so on.
 */
object CodeWriter {
    // TODO: Find the true git project root -- this assumes you are running
    //  from the project root when calling the executable, and support tests
    //  writing to temp files (don't mess up the local repo from tests)
    private val gitRoot: Path = Path.of(".")

    fun writeMainSource(
        packageName: String,
        fileName: String,
        content: String,
        gitRoot: Path = CodeWriter.gitRoot, // Vary for tests
    ) {
        writeFile(
            pathFor(
                root = gitRoot,
                type = SourceType.MAIN,
                packageName = packageName,
                fileName = fileName
            ), content
        )
    }

    fun writeTestSource(
        packageName: String,
        fileName: String,
        content: String,
        gitRoot: Path = CodeWriter.gitRoot, // Vary for tests
    ) {
        writeFile(
            pathFor(
                root = gitRoot,
                type = SourceType.TEST,
                packageName = packageName,
                fileName = fileName
            ), content
        )
    }

    private enum class SourceType { MAIN, TEST }

    private fun pathFor(
        root: Path,
        type: SourceType,
        packageName: String,
        fileName: String
    ): Path = root.resolve(
        "src/${type.name.lowercase()}/kotlin/${
            packageName.replace(
                ".",
                "/"
            )
        }/$fileName.kt"
    )

    /** This OVERWRITES. It writes in UTF-8. */
    private fun writeFile(targetPath: Path, content: String) {
        Files.createDirectories(targetPath.parent)
        Files.writeString(
            targetPath,
            content,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING
        )
    }
}
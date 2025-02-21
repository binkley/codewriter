package hm.binkley.codewriter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CodewriterApplication

fun main(args: Array<String>) {
	runApplication<CodewriterApplication>(*args)
}

package hm.binkley.codewriter

fun main(args: Array<String>) {
    val prompt = args.joinToString(" ") { it.trim() }
    println(prompt)
}

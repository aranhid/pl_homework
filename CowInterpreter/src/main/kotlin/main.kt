import java.io.File
import kotlin.collections.HashMap

fun readCow(path: String): List<String> {
    val file = File(path)
    val lines = file.readLines().joinToString(" ")
    return lines.split(" ")
}

fun getLoopBlocks(source: List<String>): HashMap<Int, Int> {
    val blocks = HashMap<Int, Int>()
    val stack = mutableListOf<Int>()
    for ((i, operator) in source.withIndex()){
        if (operator == "MOO"){
            stack.add(i)
        }
        if (operator == "moo"){
            blocks[i] = stack[stack.lastIndex]
            blocks[stack.removeAt(stack.lastIndex)] = i
        }
    }
    return blocks
}

fun eval(source: List<String>){
    val buffer = Array<Char> (200) { _ -> (0).toChar()}
    var ptr = 0
    var i = 0
    val blocks = getLoopBlocks(source)
    while (i < source.size){
        when (source[i]) {
            "moO" -> ptr += 1
            "mOo" -> ptr -= 1
            "MoO" -> buffer[ptr] = buffer[ptr] + 1
            "MOo" -> buffer[ptr] = buffer[ptr] - 1
            "OOM" -> print(buffer[ptr].toInt())
            "oom" -> {
                print("Enter value: ")
                buffer[ptr] = readLine()?.toCharArray()?.get(0)!!
            }
            "MOO" -> {
                if (buffer[ptr] == (0).toChar()) {
                    i = blocks[i]!!
                }
            }
            "moo" -> {
                if (buffer[ptr] != (0).toChar()) {
                    i = blocks[i]!!
                }
            }
            "Moo" -> {
                if (buffer[ptr] == (0).toChar())
                    buffer[ptr] = readLine()?.toCharArray()?.get(0)!!
                else
                    print(buffer[ptr])
            }
            "OOO" -> buffer[ptr] = (0).toChar()
        }
        i += 1
    }
}

fun main(args: Array<String>){
    val source = readCow("hello.cow")
    eval(source)
    println()
    println("Done")
}
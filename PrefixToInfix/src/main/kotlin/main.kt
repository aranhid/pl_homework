import java.util.*

fun isNumber(num: String): Boolean {
    for (ch in num) {
        if (!ch.isDigit()) {
            return false
        }
    }
    return true
}

fun reader(): List<String> {
    val line = readLine()
    if (line.isNullOrEmpty()){
        throw Throwable("Empty input");
    }
    return line.trim()!!.split(" ")
}

fun translator(parts: List<String>):String {
    var ops = arrayOf("+", "-", "*", "/")
    val stack = Stack<String>()
    for (part in parts.reversed()) {
        if (isNumber(part)) {
            stack.push(part);
        } else {
            if (part in ops) {
                if (stack.size >= 2) {
                    val num1 = stack.pop()
                    val num2 = stack.pop()
                    stack.push("($num1 $part $num2)")
                } else {
                    throw Throwable("Not enough numbers in expression")
                }
            } else {
                throw Throwable("Wrong operation. You can only use +, -, * or /.")
            }
        }
    }
    if (stack.size > 1) {
        throw Throwable("The number of operators does not match the number of elements.")
    }
    return stack.pop()
}

fun main() {
    print("Enter an expression: ")
    try {
        val parts = reader()
        val output = translator(parts)
        print("Answer: ")
        println(output)
    } catch (e: Throwable) {
        print("Error: ")
        println(e.message)
    }
}
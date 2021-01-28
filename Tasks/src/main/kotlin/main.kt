import java.io.File
import kotlin.math.abs
import kotlin.random.Random

fun task1(): Int {
    val expression = readLine()
    if (expression.isNullOrEmpty()) {
        throw Throwable("Empty input")
    }
    val parts = expression.trim().split(" ")
    if (parts.size == 3) {
        return when (parts[2]) {
            "+" -> parts[0].toInt() + parts[1].toInt()
            "-" -> parts[0].toInt() - parts[1].toInt()
            "*" -> parts[0].toInt() * parts[1].toInt()
            "/" -> parts[0].toInt() / parts[1].toInt()
            else -> throw Throwable("Invalid operator")
        }
    } else {
        throw Throwable("Incorrect input")
    }
}

fun task2() {
    var word = readLine()
    if (word.isNullOrEmpty()) {
        throw Throwable("Empty input")
    }
    var vowelsCount = 0
    var consonantCount = 0
    val vowels = arrayListOf('a', 'e', 'i', 'o', 'u', 'y')
    val consonants = arrayListOf('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z')
    for (ch in word.toLowerCase()) {
        if (ch in vowels) {
            vowelsCount++
        }
        if (ch in consonants) {
            consonantCount++
        }
    }
    println("Vowels count: $vowelsCount")
    println("Consonants count: $consonantCount")
}

fun task3(): String {
    var longestWord: String = ""
    var len = 0
    val file = File("words.txt")
    val words = file.readText().split(" ")
    for (word in words) {
        if (word.length > len) {
            len = word.length
            longestWord = word
        }
    }
    return longestWord
}

fun task4() {
    var randomValues = List(10) { Random.nextDouble(-100.0, 100.0) }
    println(randomValues)
    randomValues = randomValues.sortedBy { abs(it) % 1 }
    println(randomValues)
}

fun task5() {
    val text = File("lorem.txt").readText().
    val frequency = HashMap<Char, Int>()
    for (character in text) {
        if (character in frequency) {
            frequency[character] = frequency[character]!!.plus(1)
        } else {
            frequency[character] = 1
        }
    }
    println(frequency)
}

fun main(args: Array<String>) {
    task5()
}
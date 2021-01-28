import java.io.File
import kotlin.math.log2

class PasswordLengthRule(private val minLength: Int, private val maxLength: Int): Rule {
    override val errorMessage: String = "Password should has length more than or equal $minLength characters and more or equal $maxLength characters"

    override fun validate(password: String): Boolean {
        return password.length in minLength..maxLength
    }
}

class CharacterCaseRule(): Rule {
    override val errorMessage: String = "Password must contain upper and lower case characters"

    var hasUpper = false
    var hasLower = false

    override fun validate(password: String): Boolean {
        for (character in password) {
            if (character.isLowerCase()) {
                hasLower = true
            }
            if (character.isUpperCase()) {
                hasUpper = true
            }
        }

        return hasLower && hasUpper
    }
}

class DigitsRule(): Rule {
    override val errorMessage: String = "Password must contain digits"

    override fun validate(password: String): Boolean {
        for (character in password) {
            if (character.isDigit()) {
                return true
            }
        }

        return false
    }
}

class SpecialCharactersRule(): Rule {
    override val errorMessage: String = "Password must contain special characters"

    override fun validate(password: String): Boolean {
        for (character in password) {
            if (!character.isLetterOrDigit()) {
                return true
            }
        }

        return false
    }
}

class NoFrequentWordsRule(): Rule {
    override val errorMessage: String = "Password must not contain frequent words"

    private var words: List<String>

    init {
        words = File("pswd-dict.txt").readLines().joinToString(" ").split(" ")
    }

    override fun validate(password: String): Boolean {
        if (password in words) {
            return false
        }

        return true
    }
}

class HighEntropyRule(private val threshold: Double): Rule {
    override val errorMessage: String = "Password must have high entropy"

    override fun validate(password: String): Boolean {
        val frequency = HashMap<Char, Int>()

        for (character in password) {
            if (character in frequency) {
                frequency[character] = frequency[character]!!.plus(1)
            } else {
                frequency[character] = 1
            }
        }

        var entropy = 0.0

        for ((character, count) in frequency) {
            entropy += (count.toDouble() / frequency.size.toDouble()) * log2(count.toDouble() / frequency.size.toDouble())
        }

        entropy = -entropy
        return entropy >= threshold
    }
}
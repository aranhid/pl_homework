import java.lang.Exception

class InvalidIndexException(message: String): Exception(message)
class InvalidConditionException(message: String): Exception(message)

class SpecialHashMap: HashMap<String, Int>() {

    val iloc = Iloc()
    val ploc = Ploc()

    inner class Iloc {
        operator fun get(index: Int): Int? {
            val sortedKeys = this@SpecialHashMap.keys.toSortedSet().toList()
            if (index >= 0 && index < sortedKeys.size){
                return this@SpecialHashMap[sortedKeys[index]]
            } else {
                throw InvalidIndexException("Wrong index: $index")
            }
        }
    }

    inner class Ploc {
        operator fun get(innerCondition: String): HashMap<String, Int> {
            val conditions = innerCondition.split(",").map { it.replace("\\s".toRegex(), "") }
            val result = HashMap<String, Int>()

            for ((key, value) in this@SpecialHashMap) {
                val indexes = key.replace("[\\s()]".toRegex(), "").split(',')
                if (indexes.size == conditions.size) {
                    var matches = true
                    for ((i, index) in indexes.withIndex()) {
                        if (isNumber(index)) {
                            val number = index.trim().toInt()

                            val parsedCondition = parseCondition(conditions[i])

                            matches = matches && when(parsedCondition.first) {
                                ">=" -> number >= parsedCondition.second
                                "<=" -> number <= parsedCondition.second
                                "<>" -> number != parsedCondition.second
                                ">" -> number > parsedCondition.second
                                "<" -> number < parsedCondition.second
                                "=" -> number == parsedCondition.second
                                else -> throw InvalidConditionException("No such operator: ${parsedCondition.first}")
                            }
                        } else {
                            matches = false
                        }
                    }
                    if (matches) {
                        result[key] = value
                    }
                }
            }
            return result
        }

        private fun parseCondition(condition: String): Pair<String, Int> {
            if (condition.length < 2) {
                throw InvalidConditionException("Short condition: $condition")
            }

            val ops = arrayListOf("<>", "<=", ">=", ">", "<", "=")

            var operator: String? = null
            var value: Int? = null

            if (isNumber(condition[1].toString())) {
                operator = condition[0].toString()
                value = condition.slice(1 until condition.length).toIntOrNull()
            } else {
                operator = condition.slice(0 until 2)
                value = condition.slice(2 until condition.length).toIntOrNull()
            }

            if (operator.isNullOrEmpty() || isNumber(operator)){
                throw InvalidConditionException("No operator in condition: $condition")
            }

            if (value == null){
                throw InvalidConditionException("No value in condition: $condition")
            }

            return Pair(operator, value)
        }

        private fun isNumber(num: String): Boolean {
            for (ch in num) {
                if (!ch.isDigit()) {
                    return false
                }
            }
            return true
        }
    }
}
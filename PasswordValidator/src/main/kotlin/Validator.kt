import java.lang.Exception

class NoRulesException(message: String): Exception(message)
class ExistingRuleException(message: String): Exception(message)

class Validator {
    private val rules = mutableListOf<Rule>()
    private val errors = mutableListOf<String>()

    fun addRule(newRule: Rule) {
        for (rule in rules) {
            if (newRule::class == rule::class) {
                throw ExistingRuleException("Rule is in array")
            }
        }
        rules.add(newRule)
    }

    fun validate(password: String): Boolean {
        errors.clear()

        if (rules.isEmpty()) {
            throw NoRulesException("No rules added.")
        }

        for (rule in rules) {
            if (!rule.validate(password)) {
                errors.add(rule.errorMessage)
            }
        }

        return errors.isEmpty()
    }

    fun  getErrors(): List<String> {
        return  errors
    }
}
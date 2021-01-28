fun main(args: Array<String>) {
    print("Enter your password for validate: ")
    val password = readLine()
    val validator = Validator()
    while (true) {
        println("1. PasswordLengthRule")
        println("2. CharacterCaseRule")
        println("3. DigitsRule")
        println("4. SpecialCharactersRule")
        println("5. NoFrequentWordsRule")
        println("6. HighEntropyRule")
        println("0. Continue")
        print("Enter a number of rule to add it to the validator: ")
        var option = readLine()!!.toInt()
        when (option) {
            1 -> {
                print("Enter minimum length: ")
                val minLength = readLine()!!.toInt()
                print("Enter maximum length: ")
                val maxLength = readLine()!!.toInt()
                validator.addRule(PasswordLengthRule(minLength, maxLength))
            }
            2 -> validator.addRule(CharacterCaseRule())
            3 -> validator.addRule(DigitsRule())
            4 -> validator.addRule(SpecialCharactersRule())
            5 -> validator.addRule(NoFrequentWordsRule())
            6 -> {
                val entropyThreshold = readLine()!!.toDouble()
                validator.addRule(HighEntropyRule(entropyThreshold))
            }
            0 -> break
            else -> {
                println("No such rule!")
            }
        }
    }
    if (validator.validate(password!!)) {
        println("Password is strong")
    } else {
        println(validator.getErrors())
    }

}
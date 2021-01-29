fun main(args: Array<String>) {
    var interpreter: Interpreter
    var text: String
    var result: Double
    var lexer: Lexer

    val test = "BEGIN y = 2; BEGIN a = 3; a = a; b = 10 + a + 10 * y / 4; c = a - b; END x = 11; END."
    val test1 = "BEGIN y = 2;  BEGIN a = 3; b = 10 + 10; END x = 11; END."
    val test2 = "BEGIN END."
    val test3 = "BEGIN y = 2; BEGIN x = 1 + y; END END."

    while (true) {
        print("in > ")
        text = readLine().toString()
        println(": $text")

        if ((text == "exit") or (text.isEmpty())) {
            break
        }
        try {
            val parser = Parser(Lexer(text))
            val interpreter = Interpreter(parser, verbose = true)
            interpreter.interpret()
            print("Answer: ")
            println(interpreter.ID)
        }
        catch (e: InterpreterException){
            System.err.println("out > $e")

        }
    }

    println("Bye, bye!")
}
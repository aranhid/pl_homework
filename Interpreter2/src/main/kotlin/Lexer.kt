class Lexer(val text: String) {
    private var pos: Int = 0
    private var currentChar: Char? = null

    init {
        currentChar = text[pos]
    }

    fun nextToken(): Token {
        var value: String
        var type: TokenType?

        while (currentChar != null)
        {
            if (currentChar!!.isWhitespace()) {
                skip()
                continue
            }
            if (currentChar!!.isDigit()) {
                return Token(TokenType.NUMBER, number())
            }

            if (currentChar!!.isLetter()) {
                val row = row()
                when (row) {
                    "BEGIN" -> return Token(TokenType.BEGIN, row)
                    "END" -> return  Token(TokenType.END, row)
                    else -> return  Token(TokenType.ID, row)
                }
            }

            type = null
            value = "$currentChar"

            when (currentChar)
            {
                '+' -> type = TokenType.PLUS
                '-' -> type = TokenType.MINUS
                '/' -> type = TokenType.DIV
                '*' -> type = TokenType.MUL
                '(' -> type = TokenType.LPAREN
                ')' -> type = TokenType.RPAREN
                '=' -> type = TokenType.ASSIGN
                ';' -> type = TokenType.EOL
                '.' -> type = TokenType.EOF
            }

            type?.let {
                forward()
                return Token(it, value)
            }

            throw InterpreterException("Invalid token")
        }
        return Token(TokenType.EOF, "")
    }

    private fun forward() {
        pos += 1
        if (pos > text.length - 1){
            currentChar = null
        }
        else {
            currentChar = text[pos]
        }
    }

    private fun skip() {
        while ((currentChar != null) && currentChar!!.isWhitespace()){
            forward()
        }
    }

    private fun number(): String {
        var result = arrayListOf<Char>()
        while ((currentChar != null) && ((currentChar!!.isDigit()) || currentChar == '.')) {
            result.add(currentChar!!)
            forward()
        }
        return result.joinToString("")
    }

    private fun row(): String {
        var result = ""
        while ((currentChar != null) && currentChar!!.isLetter()) {
            result += currentChar
            forward()
        }
        return result
    }
}


fun main(args: Array<String>) {
    val lexer = Lexer("2 + 2");
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
}
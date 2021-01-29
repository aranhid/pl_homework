import java.lang.Exception

class ParserException(message: String): Exception(message)

class Parser(private val lexer: Lexer){
    private var currentToken: Token = lexer.nextToken()
    private var currentVariable: Variable? = null

    private fun checkTokenType(type: TokenType){
        if (currentToken.type == type)
        {
            currentToken = lexer.nextToken()
        }
        else {
            throw ParserException("Invalid token order")
        }
    }

    private fun factor(): Node {
        val token = currentToken
        when (token.type) {
            TokenType.PLUS -> {
                checkTokenType(TokenType.PLUS)
                return UnaryOp(token, factor())
            }
            TokenType.MINUS -> {
                checkTokenType(TokenType.MINUS)
                return UnaryOp(token, factor())
            }
            TokenType.NUMBER -> {
                checkTokenType(TokenType.NUMBER)
                return Number(token)
            }
            TokenType.LPAREN -> {
                checkTokenType(TokenType.LPAREN)
                val result = expr()
                checkTokenType(TokenType.RPAREN)
                return result
            }
            TokenType.ID -> {
                checkTokenType(TokenType.ID)
                currentVariable = Variable(token)
                return Variable(token)
            }
        }
        throw ParserException("Invalid factor")
    }

    private fun term(): Node {
        val ops = arrayListOf<TokenType>(TokenType.DIV, TokenType.MUL)
        var result = factor()
        while (ops.contains(currentToken.type)){
            val token = currentToken
            when (token.type){
                TokenType.DIV -> {
                    checkTokenType(TokenType.DIV)
                }
                TokenType.MUL -> {
                    checkTokenType(TokenType.MUL)
                }
            }
            result = BinOp(result, token, factor())
        }
        return result
    }

    fun expr(): Node {
        val ops = arrayListOf<TokenType>(TokenType.PLUS, TokenType.MINUS, TokenType.EOL)
        var result = term()
        while (ops.contains(currentToken.type)){
            val token = currentToken
            when (token.type) {
                TokenType.PLUS -> checkTokenType(TokenType.PLUS)
                TokenType.MINUS -> checkTokenType(TokenType.MINUS)
                TokenType.EOL -> return result
            }
            result = BinOp(result, token, term())
        }
        return result
    }

    private fun assign() : Node? {
        val ops = arrayListOf(TokenType.ASSIGN, TokenType.ID, TokenType.EOL)
        var writer: Node? = null
        while (ops.contains(currentToken.type)) {
            val token = currentToken
            when(token.type) {
                TokenType.ID -> {
                    checkTokenType(TokenType.ID)
                    currentVariable = Variable(token)
//                    println(currentVariable)
//                    println("bk")
                }
                TokenType.ASSIGN -> {
                    checkTokenType(TokenType.ASSIGN)
                    writer = Writer(currentVariable!!, expr())
//                    println("test")
                }
                TokenType.EOL -> {
                    checkTokenType(TokenType.EOL)
//                    println(currentVariable)
                    return writer!!
                }
            }
        }
        return null
    }

    private fun assignment() : Node {
        var body = Body()
        do {
            if (currentToken.type == TokenType.BEGIN) {
                val newBody = complexStatement() as Body
                body.addExpressions(newBody.expression)
            }
            val result = assign()
            if (result != null){
                body.addExpression(result)
            }
        } while (currentToken.type != TokenType.END)
        return body
    }

    private fun complexStatement() : Node {
        var result: Node = Body()
        val ops = arrayListOf(TokenType.BEGIN, TokenType.END)
        while (ops.contains(currentToken.type)) {
            val token = currentToken
            when (token.type) {
                TokenType.BEGIN -> {
                    checkTokenType(TokenType.BEGIN)
                    result = assignment()
//                    checkTokenType(TokenType.END)
                }
                TokenType.END -> {
                    checkTokenType(TokenType.END)
                    return result
                }
            }
        }
        return result
    }

    fun parse() : Node? {
        var result: Node? = null
        result = complexStatement()
        checkTokenType(TokenType.EOF)
        return result
    }
}

fun main(args: Array<String>) {
    val parser = Parser(Lexer("BEGIN c = 4+5; END."))
    val result = parser.parse()
    //print(parser.parse())
}
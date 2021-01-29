enum class TokenType {
    NUMBER,
    PLUS,
    MINUS,
    MUL,
    DIV,
    LPAREN,
    RPAREN,
    BEGIN,
    END,
    ID,
    ASSIGN,
    EOL,
    EOF
}

class Token(val type: TokenType, val value: String) {

    override fun toString(): String {
        return "Token ($type, $value)"
    }
}
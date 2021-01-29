abstract class Node

interface NodeVisitor {
    fun visit(node: Node)
}

class Number(val token: Token): Node() {

    override fun toString(): String {
        return "Number ($token)"
    }
}

class BinOp(val left: Node, val op: Token, val right: Node): Node() {

    override fun toString(): String {
        return "BinOp${op.value} {$left, $right}"
    }
}

class UnaryOp(val op: Token, val expr: Node): Node() {

    override fun toString(): String {
        return "UnaryOp${op.value} ($expr)"
    }
}

class Body : Node() {
    var expression = arrayListOf<Node>()
    var currentExpId = 0

    fun addExpression(node: Node) {
        expression.add(node)
    }

    fun addExpressions(expressions: ArrayList<Node>) {
        expression.addAll(expressions)
    }

    fun next(): Node? {
        if (currentExpId < expression.size) {
            return expression[currentExpId++]
        }
        return null
    }
}

class Variable(private val variable: Token) : Node() {
    override fun toString(): String {
        return variable.value
    }
}

class Writer(var id: Variable, var expr: Node) : Node()

class Eol(private val token: Token) : Node()

class End(private val token: Token) : Node()

class Empty(private val token: Token) : Node()
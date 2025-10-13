package ipca.example.calculator



import kotlin.math.sqrt

class CalculatorBrain {

    enum class Operation(op: String) {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("×"),
        DIVIDE("÷"),
        EQUAL("="),
        SQRT("√"),
        PERCENTAGE("%"),
        CLEAR("C"),
        ALL_CLEAR("A");

        companion object {
            fun parseOperation(op: String): Operation {
                return when (op) {
                    "+" -> ADD
                    "-" -> SUBTRACT
                    "×" -> MULTIPLY
                    "÷" -> DIVIDE
                    "=" -> EQUAL
                    "√" -> SQRT
                    "%" -> PERCENTAGE
                    "C" -> CLEAR
                    "A" -> ALL_CLEAR
                    else -> EQUAL
                }
            }
        }
    }

    var operand = 0.0
    var operation: Operation? = null

    fun doOperation(newOperand: Double, newOperation: Operation) {

        // Operações especiais que não precisam de operação anterior
        when (newOperation) {
            Operation.SQRT -> {
                operand = sqrt(newOperand)
                return
            }
            Operation.PERCENTAGE -> {
                operand = newOperand / 100.0
                return
            }
            else -> {}
        }

        // Se existe uma operação pendente, executa ela primeiro
        if (operation != null) {
            when (operation) {
                Operation.ADD -> operand += newOperand
                Operation.SUBTRACT -> operand -= newOperand
                Operation.MULTIPLY -> operand *= newOperand
                Operation.DIVIDE -> {
                    if (newOperand != 0.0) {
                        operand /= newOperand
                    }
                }
                else -> {}
            }
        } else {
            // Se não há operação pendente, apenas guarda o operando
            operand = newOperand
        }

        // Guarda a nova operação (exceto se for EQUAL)
        operation = if (newOperation == Operation.EQUAL) {
            null
        } else {
            newOperation
        }
    }
}
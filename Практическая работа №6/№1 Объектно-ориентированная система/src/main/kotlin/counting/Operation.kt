package counting

/**
 * Class representing any operation in a mathematical expression which can be entered
 * by hard-coding evaluate() function of the class [App]
 * @param first left argument of an {@link Operation} as an object of [Input] type
 * @param second right argument of an {@link Operation} as an object of [Input] type
 */
abstract class Operation(val first: Input, val second: Input) : Input {
    init {
        try { require(first is Const || first is Variable || first is Operation) }
        catch (e: Exception) { println("Variable 'first' is not a suitable Input!") }

        try { require(second is Const || second is Variable || second is Operation) }
        catch (e: Exception) { println("Variable 'second' is not a suitable Input!") }
    }

     /**
     * Function which logic is responsible for evaluation of an [Operation]
     * @param variable name of a variable in a mathematical expression
     * @return 0 as a marker of successful evaluation
     */
    fun evaluate(variable: Double): Double {
         isNaN(variable)

         val whenSecond = { item: Double ->
             when (second) {
                 is Const -> evaluate(item, second.constant)
                 is Variable -> evaluate(item, variable)
                 is Operation -> evaluate(item, second.evaluate(variable));
                 else -> 0.0
             }
         }
         val answer = when (first) {
             is Const -> whenSecond(first.constant)
             is Variable -> whenSecond(variable)
             is Operation -> whenSecond(first.evaluate(variable))
             else -> 0.0
         }

         isNaN(answer)
         return answer
    }

    /**
     * Function which logic is responsible for calculation of an [Operation]
     * @param lhs left calculated argument of an [Operation]
     * @param rhs right calculated argument of an [Operation]
     * @return calculated evaluation
     */
    protected abstract fun evaluate(lhs: Double, rhs: Double) : Double
}

package game.core.ai.miniMax

class MiniMax(val game: IZeroSumGame,
              val lookAheadLimit: Int) {

    private var initialBeta = Int.MAX_VALUE
    private var initialAlpha = Int.MIN_VALUE

    private var optimalMove = -1

    fun start(): Int {
        miniMax(game, initialAlpha, initialBeta, lookAheadLimit)
        game.move(optimalMove)
        return optimalMove
    }

    private fun miniMax(state: IZeroSumGame, alpha: Int, beta: Int, depth: Int): Int {

        if(state.isOver() || depth == lookAheadLimit)
            return state.getScore()

        val possibleStates = state.getStates()

        return if(state.isMaximizing())
            maximize(possibleStates, alpha, beta, depth)
        else
            minimize(possibleStates, alpha, beta, depth)

    }

    private fun maximize(states: List<IZeroSumGame>, alphaP: Int, betaP: Int, depth: Int): Int {
        var alpha = alphaP
        var beta = betaP
        var bestMoveIndex = -1
        run maximizeLoop@ {
            states.forEachIndexed { index, state ->
                val stateMiniMax = miniMax(state, alpha, beta, depth + 1)
                if(alpha < stateMiniMax) {
                    alpha = stateMiniMax
                    bestMoveIndex = index
                }

                if(alpha >= beta)
                    return@maximizeLoop
            }
        }
        this.optimalMove = bestMoveIndex
        return alpha
    }
    private fun minimize(states: List<IZeroSumGame>, alphaP: Int, betaP: Int, depth: Int): Int {
        var alpha = alphaP
        var beta = betaP
        var bestMoveIndex = -1
        run minimizeLoop@ {
            states.forEachIndexed { index, state ->
                val stateMiniMax = miniMax(state, alpha, beta, depth + 1)
                if(beta > stateMiniMax) {
                    beta = stateMiniMax
                    bestMoveIndex = bestMoveIndex
                }

                if(alpha >= beta)
                    return@minimizeLoop
            }
        }
        this.optimalMove = bestMoveIndex
        return beta
    }

}

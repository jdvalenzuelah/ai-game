package game.core.games.dotsAndBoxes.gamers

import game.core.IGamer
import game.core.games.dotsAndBoxes.gameLogic.IDotsAndBoxes
import game.core.games.dotsAndBoxes.models.MarkType
import game.core.games.dotsAndBoxes.models.PlayerTurn
import game.models.TournamentGame
import kotlin.math.max
import kotlin.math.min

class AIGamer (val gameLogic: IDotsAndBoxes,
               private val maxDepth: Int = 5): IGamer {

    private val initialAlpha = Int.MIN_VALUE + 1
    private val initialBeta = Int.MAX_VALUE - 1
    private var maxPlayer = PlayerTurn.PLAYER_1

    override fun makeMove(gameState: TournamentGame): TournamentGame {
        maxPlayer = getPlayerTurnFromId(gameState.playerTurnId)
        gameLogic.setBoard(gameState.board, maxPlayer)

        //Logger.info("Getting new random move")

        val possibleMoves = gameLogic.getPossibleMoves()

        val nextMove = getOptimalMove(possibleMoves)
            .let { gameLogic.markPosition(it.second, it.first, maxPlayer) }

        return gameState.copy(
            board = gameLogic.getBoardRepresentation(),
            movement = nextMove?.toList()
        )
    }

    private fun getOptimalMove(possibleMoves: List<Pair<MarkType, Int>>): Pair<MarkType, Int> {
        var bestScore = Int.MIN_VALUE
        var bestMove = possibleMoves.first()
        possibleMoves.forEachIndexed { index, move ->
            gameLogic.markPosition(move.second, move.first, maxPlayer)
            val score = miniMax(gameLogic, false, 0, initialAlpha, initialBeta)
            gameLogic.emptyPositions(move.second, move.first)
            if(score > bestScore) {
                bestScore = score
                bestMove = move
            }
        }
        return bestMove
    }

    private fun miniMax(gameState: IDotsAndBoxes, isMaximizing: Boolean, depth: Int, alpha: Int, beta: Int): Int {
        return if(gameState.isGameOver() || depth == maxDepth) {
            gameState.getCurrentScore().let { it.first - it.second }
        } else if(isMaximizing) {
            maximize(gameState, depth, alpha, beta)
        } else {
            minimize(gameState, depth, alpha, beta)
        }
    }


    private fun maximize(gameState: IDotsAndBoxes, depth: Int, alphaP: Int, beta: Int): Int {

        val possibleMoves = gameState.getPossibleMoves()
        var alpha = alphaP

        run maxLoop@ {
            possibleMoves.forEach { move ->
                gameState.markPosition(move.second, move.first, maxPlayer)
                val score = miniMax(gameLogic, false, depth + 1, alpha, beta)
                gameState.emptyPositions(move.second, move.first)
                alpha = max(score, alpha)
                if(alpha >= beta){
                    return@maxLoop
                }
            }
        }
        return alpha
    }

    private fun minimize(gameState: IDotsAndBoxes, depth: Int, alpha: Int, betaP: Int): Int {
        val possibleMoves = gameState.getPossibleMoves()
        var beta = betaP

        run minLoop@ {
            possibleMoves.forEach { move ->
                gameState.markPosition(move.second, move.first, maxPlayer.getOposite())
                val score = miniMax(gameLogic, true, depth + 1, alpha, beta)
                gameState.emptyPositions(move.second, move.first)
                beta = min(beta, score)
                if(alpha >= beta) {
                    return@minLoop
                }
            }
        }
        return alpha
    }


    private fun IDotsAndBoxes.getPossibleMoves() = MarkType.values()
        .map { this.getEmptyPositions(it).map { pos -> it to pos } }
        .flatten()

}

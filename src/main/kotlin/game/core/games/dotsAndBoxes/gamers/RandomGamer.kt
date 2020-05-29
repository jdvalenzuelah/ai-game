package game.core.games.dotsAndBoxes.gamers

import game.core.IGamer
import game.core.games.dotsAndBoxes.gameLogic.IDotsAndBoxes
import game.core.games.dotsAndBoxes.models.MarkType
import game.models.TournamentGame
import org.pmw.tinylog.Logger

class RandomGamer(val gameLogic: IDotsAndBoxes): IGamer {

    override fun makeMove(gameState: TournamentGame): TournamentGame {
        val playerTurn = getPlayerTurnFromId(gameState.playerTurnId)
        gameLogic.setBoard(gameState.board, playerTurn)

        Logger.info("Getting new random move")

        return MarkType.values()
            .map { markType ->
                gameLogic.getEmptyPositions(markType)
                    .map { it to markType }
            }
            .flatten()
            .random()
            .let { gameLogic.markPosition(it.first, it.second, playerTurn) }
            ?.let {
                gameState.copy(
                    board = gameLogic.getBoardRepresentation(),
                    movement = it.toList()
                )
            } ?: gameState
    }

}

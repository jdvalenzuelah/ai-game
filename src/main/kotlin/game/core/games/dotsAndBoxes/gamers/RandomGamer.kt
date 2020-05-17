package game.core.games.dotsAndBoxes.gamers

import game.core.IGamer
import game.core.games.dotsAndBoxes.gameLogic.IDotsAndBoxes
import game.core.games.dotsAndBoxes.models.MarkType
import game.models.TournamentGame
import org.pmw.tinylog.Logger

class RandomGamer(val gameLogic: IDotsAndBoxes): IGamer {

    override fun makeMove(gameState: TournamentGame): TournamentGame {

        gameLogic.setBoard(gameState.board)

        Logger.info("Getting new random move")
        var positionMarked: Pair<Int, Int>?
        do {
            val markType = MarkType.values().random()
            val size = gameLogic.getLineCount(markType) - 1
            val markPosition = (0..size).random()
            positionMarked = gameLogic.markPosition(markPosition, markType)
        } while (positionMarked == null)
        Logger.info("New move found $positionMarked")
        return gameState.copy(
            board = gameLogic.getBoardRepresentation(),
            movement = positionMarked.toList()
        )
    }
}

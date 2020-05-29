package game.core.games.dotsAndBoxes.gameLogic

import game.core.games.dotsAndBoxes.models.MarkType
import game.core.games.dotsAndBoxes.models.PlayerTurn

interface IDotsAndBoxes {
    fun getPlayerTurn(): PlayerTurn
    fun getPlayerTurnId() = getPlayerTurn().id
    fun setBoard(boardRepresentation: List<List<Int>>, playerTurnId: PlayerTurn)
    fun getBoardRepresentation(): List<List<Int>>
    fun getLineCount(type: MarkType): Int
    fun getEmptyPositions(type: MarkType): List<Int>
    fun getCurrentScore(): Pair<Int, Int>
    fun getClosedBoxesCount(): Int
    fun markPosition(position: Int, type: MarkType, player: PlayerTurn): Pair<Int, Int>?
    fun isGameOver(): Boolean
    fun emptyPositions(position: Int, type: MarkType): Pair<Int, Int>
    fun isRepeatingTurn(): Boolean

}

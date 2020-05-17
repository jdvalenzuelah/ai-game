package game.core.games.dotsAndBoxes.gameLogic

import game.core.IGamer
import game.core.games.dotsAndBoxes.models.MarkType

interface IDotsAndBoxes : IGamer {
    fun setBoard(boardRepresentation: List<List<Int>>)
    fun getBoardRepresentation(): List<List<Int>>
    fun getLineCount(type: MarkType): Int
    fun getCurrentScore(): Pair<Int, Int>
    fun getClosedBoxesCount(): Int
    fun markPosition(position: Int, type: MarkType): Pair<Int, Int>?
}

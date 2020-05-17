package game.core.games.dotsAndBoxes.gameLogic

import game.core.games.dotsAndBoxes.models.MarkType
import game.models.TournamentGame
import org.pmw.tinylog.Logger

class DotsAndBoxes(val emptyValue: Int): IDotsAndBoxes {

    private var horizontalLines = mutableListOf<Int>()
    private var verticalLines = mutableListOf<Int>()

    override fun setBoard(boardRepresentation: List<List<Int>>) {
        Logger.info("Setting up new board")
        horizontalLines = boardRepresentation.component1().toMutableList()
        verticalLines = boardRepresentation.component2().toMutableList()
    }

    override fun getBoardRepresentation(): List<List<Int>> = listOf(horizontalLines, verticalLines)

    override fun getLineCount(type: MarkType): Int = when(type) {
        MarkType.VERTICAL -> verticalLines.size
        MarkType.HORIZONTAL -> horizontalLines.size
    }

    override fun getClosedBoxesCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getCurrentScore(): Pair<Int, Int> {
        TODO("Not yet implemented")
    }

    override fun makeMove(gameState: TournamentGame): TournamentGame {
        TODO("Not yet implemented")
    }

    override fun markPosition(position: Int, type: MarkType): Pair<Int, Int>? {
        //Logger.info("Marking up new position $position on $type")
        return when(type) {
            MarkType.HORIZONTAL -> {
                if(horizontalLines[position] == emptyValue) {
                    horizontalLines[position] = 0
                    return Pair(type.position,position)
                } else {
                    null
                }
            }
            MarkType.VERTICAL -> {
                if(verticalLines[position] == emptyValue) {
                    verticalLines[position] = 0
                    return Pair(type.position, position)
                } else {
                    null
                }
            }
        }
    }

}

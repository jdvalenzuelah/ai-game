package game.core.games.dotsAndBoxes.gameLogic

import game.core.games.dotsAndBoxes.*
import game.core.games.dotsAndBoxes.models.MarkType
import org.pmw.tinylog.Logger

class DotsAndBoxes: IDotsAndBoxes {

    private var horizontalLines = mutableListOf<Int>()
    private var verticalLines = mutableListOf<Int>()
    private var boardSize = 0

    override fun setBoard(boardRepresentation: List<List<Int>>) {
        Logger.info("Setting up new board")
        horizontalLines = boardRepresentation.component1().toMutableList()
        verticalLines = boardRepresentation.component2().toMutableList()
        boardSize = horizontalLines.size
    }

    override fun getBoardRepresentation(): List<List<Int>> = listOf(horizontalLines, verticalLines)

    override fun getLineCount(type: MarkType): Int = when(type) {
        MarkType.VERTICAL -> verticalLines.size
        MarkType.HORIZONTAL -> horizontalLines.size
    }

    override fun getEmptyPositions(type: MarkType): List<Int> {

        val lineList = when(type) {
            MarkType.VERTICAL -> verticalLines
            MarkType.HORIZONTAL -> horizontalLines
        }

        return lineList
            .mapIndexed { index, line -> Pair(index, line == EMPTY_SPACE) }
            .filter { it.second }.map { it.first }

    }

    override fun getClosedBoxesCount(): Int {
        var points = 0
        var accumulator = 0
        var counter = 0
        for( index in (0 until boardSize)) {
            if( ((index + 1) % BOARD_DIMENSIONS) != 0 ){
                if(isBoxedClosed(horizontalLines[index],
                        horizontalLines[index + 1],
                        verticalLines[counter + accumulator],
                        verticalLines[counter + accumulator + 1])) {
                    points++
                }
                accumulator += BOARD_DIMENSIONS
            } else {
                counter++
                accumulator = 0
            }
        }
        return points
    }

    private fun isBoxedClosed(vararg walls: Int) = walls.takeWhile { it != EMPTY_SPACE }.size == walls.size

    override fun getCurrentScore(): Pair<Int, Int> {
        var player1 = 0
        var player2 = 0
        for(index in (0 until boardSize)){
            when(verticalLines[index]){
                FILLED_P12 -> player1 += 2
                FILLED_P11 -> player1 += 1
                FILLED_P22 -> player2 += 2
                FILLED_P21 -> player2 += 1
            }
            when(horizontalLines[index]){
                FILLED_P12 -> player1 += 2
                FILLED_P11 -> player1 += 1
                FILLED_P22 -> player2 += 2
                FILLED_P21 -> player2 += 1
            }
        }
        return Pair(player1, player2)
    }

    override fun markPosition(position: Int, type: MarkType): Pair<Int, Int>? {
        Logger.info("Current score: ${getClosedBoxesCount()} for board ${getBoardRepresentation()}")
        val playerScores = getCurrentScore()
        Logger.info("Player1 score: ${playerScores.first} Player2 score: ${playerScores.second}")
        return when(type) {
            MarkType.HORIZONTAL -> {
                if(horizontalLines[position] == EMPTY_SPACE) {
                    horizontalLines[position] = 0
                    return Pair(type.position,position)
                } else {
                    null
                }
            }
            MarkType.VERTICAL -> {
                if(verticalLines[position] == EMPTY_SPACE) {
                    verticalLines[position] = 0
                    return Pair(type.position, position)
                } else {
                    null
                }
            }
        }
    }

}

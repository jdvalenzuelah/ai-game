package game.core.ai.miniMax

interface IZeroSumGame {
    fun getScore(): Int
    fun isOver(): Boolean
    fun isMaximizing(): Boolean
    fun getStates(): List<IZeroSumGame>
    fun move(move: Int)
}

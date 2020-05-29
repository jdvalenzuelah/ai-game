package game.core.games.dotsAndBoxes.gamers

import game.core.games.dotsAndBoxes.models.PlayerTurn

fun PlayerTurn.getOposite() = when(this) {
    PlayerTurn.PLAYER_1 -> PlayerTurn.PLAYER_2
    PlayerTurn.PLAYER_2 -> PlayerTurn.PLAYER_1
}

fun getPlayerTurnFromId(id: Int) = when(id) {
    1 -> PlayerTurn.PLAYER_1
    else -> PlayerTurn.PLAYER_2
}

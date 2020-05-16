package game.core

import game.models.TournamentGame

interface IGamer {
    fun makeMove(gameState: TournamentGame): TournamentGame
}

package game.gameService

import game.models.GameUser
import game.models.TournamentGame

fun GameUser.toMap() = mapOf("user_name" to userName, "tournament_id" to tournamentId, "user_role" to userRole.role)

fun TournamentGame.toPlayMap() = mapOf(
    "tournament_id" to tournamentId,
    "player_turn_id" to playerTurnId,
    "game_id" to gameId,
    "movement" to movement
)

fun TournamentGame.toReadyMap() = mapOf("tournament_id" to tournamentId, "player_turn_id" to playerTurnId, "game_id" to gameId)


package game.models

data class GameUser(val userName: String,
                    val tournamentId: Int,
                    val userRole: UserRole = UserRole.PLAYER
)

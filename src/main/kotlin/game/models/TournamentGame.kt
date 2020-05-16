package game.models

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigInteger

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class TournamentGame(val gameId: BigInteger,
                          val playerTurnId: Int,
                          val board: List<List<Int>>,
                          val tournamentId: Int?,
                          val winnerTurnId: Int?,
                          @JsonAlias("movementNumber")
                          val movement: Int?)

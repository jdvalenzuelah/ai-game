package game.gameService

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.nkzawa.socketio.client.Socket
import game.exceptions.NoMovementMadeException
import game.gameLogic.IGamer
import game.models.GameUser
import game.models.TournamentGame
import org.json.JSONObject
import org.pmw.tinylog.Logger

class GameController(private val socket: Socket,
                     private val user: GameUser,
                     private val gamer: IGamer,
                     private val mapper: ObjectMapper) {

    fun start() {
        socket.connect()
            .on(Socket.EVENT_CONNECT) { onConnection() }
            .on("ok_signin") { onSignInSucces() }
            .on("ready") { params ->
                val incomingData = params.toJsonObject().toString().parseToTournamentGame()
                Logger.info("Game completed received $incomingData")
                onGameMove(incomingData)
            }
            .on("finish") { params ->
                val incomingData = params.toJsonObject().toString().parseToTournamentGame()
                Logger.info("Game completed received $incomingData")
                onGameCompleted(incomingData)
            }
    }

    private fun Array<Any>.toJsonObject() = this[0] as JSONObject

    private fun String.parseToTournamentGame() = mapper.readValue<TournamentGame>(this)

    fun onConnection() {
        Logger.info("Logging in as ${user.userRole.role} username: ${user.userName} tournament ID: ${user.tournamentId}")
        val response = user.toMap()
        socket.emit("signin", response )
    }

    fun onSignInSucces() { Logger.info("Logged in successfully as ${user.userName} on tournament ${user.tournamentId}") }

    fun onGameMove(game: TournamentGame) {
        Logger.info("Game move received $game")
        val resultPlay = gamer.makeMove(game)
        emitPlay(resultPlay)
    }

    fun onGameCompleted(game: TournamentGame) {
        Logger.info("Game ${game.gameId} completed. Winner ${game.winnerTurnId}")
        //clean up
        emitPlayerReady(game)
    }

    fun emitPlay(play: TournamentGame) {
        if(play.movement == null) throw NoMovementMadeException("Move was not specified")
        Logger.info("Sending play $play for game ${play.gameId}")
        socket.emit("play", play.toPlayMap())
    }

    fun emitPlayerReady(game: TournamentGame) {
        Logger.info("Waiting for new games on tournament ${game.tournamentId}")
        socket.emit("player_ready", game.copy(tournamentId = user.tournamentId).toReadyMap())
    }
}

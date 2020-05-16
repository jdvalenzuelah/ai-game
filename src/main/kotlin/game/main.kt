package game

import com.xenomachina.argparser.ArgParser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.nkzawa.socketio.client.IO
import game.core.IGamer
import game.gameService.GameController
import game.models.GameUser
import game.models.TournamentGame
import game.models.UserRole

lateinit var arguments: Args

class Args(parser: ArgParser) {
    val serverAddress by parser.storing(
        "-s", "--server",
        help = "Server address -> address:port")
    val tournamentId by parser.storing(
        "-t", "--tid",
        help = "Tournament ID" ) { toInt() }
    val userName by parser.storing(
        "-u", "--user",
        help = "Username")
}


fun main(args: Array<String>) {

    ArgParser(args).parseInto(::Args).run { arguments = this }

    //"http://localhost:4000/"
    val socket = IO.socket(arguments.serverAddress)

    val user = GameUser(arguments.userName, arguments.tournamentId, UserRole.PLAYER)
    val jackson = jacksonObjectMapper()

    // Mock pending implementation
    val gamer =  object : IGamer {
        override fun makeMove(gameState: TournamentGame): TournamentGame {
            return gameState.copy(movement = 1)
        }
    }

    GameController(socket, user, gamer, jackson).start()
}

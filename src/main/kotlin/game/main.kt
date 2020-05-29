package game

import com.xenomachina.argparser.ArgParser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.nkzawa.socketio.client.IO
import com.xenomachina.argparser.default
import com.xenomachina.argparser.mainBody
import game.gameService.GameController
import game.core.games.dotsAndBoxes.gamers.RandomGamer
import game.core.games.dotsAndBoxes.gameLogic.DotsAndBoxes
import game.core.games.dotsAndBoxes.gamers.AIGamer
import game.models.GameUser
import game.models.UserRole

lateinit var arguments: Args

class Args(parser: ArgParser) {
    val serverAddress by parser.storing(
        "-s", "--server",
        help = "Server address -> address:port").default("http://localhost:4000/")
    val tournamentId by parser.storing(
        "-t", "--tid",
        help = "Tournament ID" ) { toInt() }.default(12)
    val userName by parser.storing(
        "-u", "--user",
        help = "Username").default("player")
}


fun main(args: Array<String>) = mainBody {

    ArgParser(args).parseInto(::Args).run { arguments = this }

    //"http://localhost:4000/"
    //node ./bin/www —tid=2 —rrt=2 —game=dotsAndBoxes —port=5000
    val socket = IO.socket(arguments.serverAddress)

    val user = GameUser(arguments.userName, arguments.tournamentId, UserRole.PLAYER)
    val jackson = jacksonObjectMapper()

    val dotsAndBoxes = DotsAndBoxes()
    val gamer = AIGamer(dotsAndBoxes)

    GameController(socket, user, gamer, jackson).start()
}

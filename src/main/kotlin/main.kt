import domain.ConsoleDisplay
import domain.Game

fun main(args: Array<String>) {
	val display = ConsoleDisplay()
	val game = Game(display)

	game.roll(args.map { it.toInt() })
	game.displayFramesScore()
	game.displayTotalScore()
}
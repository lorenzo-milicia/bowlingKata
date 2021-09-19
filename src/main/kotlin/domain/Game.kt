package domain

class Game {

	private val scoreboard: Scoreboard = Scoreboard()

	fun roll(pins: Int) {
		if (scoreboard.isGameOver()) return
		scoreboard.roll(pins)
	}


}
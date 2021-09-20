package domain

class Game(
	private val display: IDisplay
) {

	private val scoreboard: Scoreboard = Scoreboard()

	fun roll(pins: Int) {
		if (scoreboard.isGameOver()) return
		scoreboard.roll(pins)
	}

	fun roll(listRolls: List<Int>) {
		listRolls.forEach { roll(it) }
	}

	fun displayFramesScore() {
		display.displayFramesScore(scoreboard.frameScores())
	}

	fun displayTotalScore() {
		display.displayTotalScore(scoreboard.totalScore())
	}

}
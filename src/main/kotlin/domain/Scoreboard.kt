package domain

class Scoreboard {

	val frames = mutableListOf(Frame())

	fun roll(pinsKnockedDown: Int) {
		if (!frames.last().isClosed) frames.last().roll(pinsKnockedDown)
		else {
			if (frames.size < 9) frames.add(Frame().also { it.roll(pinsKnockedDown) })
			else frames.add(LastFrame().also {it.roll(pinsKnockedDown)})
		}
	}

	fun isGameOver() = frames.last().let { it is LastFrame || it.isClosed }

	fun frameScores(): List<FrameScore> = ScoreComputer.computeFramesScore(frames)

	fun totalScore(): Int = ScoreComputer.computeTotalScore(frames)
}
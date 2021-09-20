import domain.FrameScore
import domain.LastFrame
import domain.ScoreComputer
import domain.Scoreboard
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ScoreboardTest {

	@Test
	internal fun `scoreboard one roll`() {
		val scoreboard = Scoreboard()

		scoreboard.roll(5)

		assertEquals(1, scoreboard.frames.size)
	}

	@Test
	internal fun `scoreboard many rolls`() {
		val scoreboard = Scoreboard()

		scoreboard.roll(5)
		scoreboard.roll(5)
		scoreboard.roll(10)
		scoreboard.roll(2)

		assertEquals(3, scoreboard.frames.size)
	}

	@Test
	internal fun `last frame test`() {
		val scoreboard = Scoreboard()

		scoreboard.roll(10)
		scoreboard.roll(10)
		scoreboard.roll(10)
		scoreboard.roll(10)
		scoreboard.roll(3)
		scoreboard.roll(3)
		scoreboard.roll(10)
		scoreboard.roll(10)
		scoreboard.roll(10)
		scoreboard.roll(10)
		scoreboard.roll(5)
		scoreboard.roll(5)
		scoreboard.roll(5)


		assertEquals(10, scoreboard.frames.size)
		assertTrue(scoreboard.frames.last() is LastFrame)
		assertTrue(scoreboard.isGameOver())
	}

	@Test
	internal fun `frames scores test`() {
		val scoreboard = Scoreboard()

		scoreboard.roll(9)
		scoreboard.roll(1)
		scoreboard.roll(3)
		scoreboard.roll(2)
		scoreboard.roll(9)
		scoreboard.roll(0)
		scoreboard.roll(3)
		scoreboard.roll(3)
		scoreboard.roll(4)
		scoreboard.roll(0)
		scoreboard.roll(10)

		val scores = listOf(
			FrameScore(13, false),
			FrameScore(5, false),
			FrameScore(9, false),
			FrameScore(6, false),
			FrameScore(4, false),
			FrameScore(10, true))
		assertEquals(scores, scoreboard.frameScores())
	}
}


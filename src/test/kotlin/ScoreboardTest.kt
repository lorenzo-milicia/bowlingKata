import domain.Frame
import domain.LastFrame
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
		scoreboard.roll(10)
		scoreboard.roll(10)
		scoreboard.roll(10)
		scoreboard.roll(10)
		scoreboard.roll(10)
		scoreboard.roll(5)

		assertEquals(10, scoreboard.frames.size)
		assertTrue(scoreboard.frames.last() is LastFrame)
	}
}

class Scoreboard {

	val frames = mutableListOf<Frame>(Frame())

	fun roll(pinsKnockedDown: Int) {
		if (!frames.last().isClosed) frames.last().roll(pinsKnockedDown)
		else {
			if (frames.size < 9) frames.add(Frame().also { it.roll(pinsKnockedDown) })
			else frames.add(LastFrame().also {it.roll(pinsKnockedDown)})
		}
	}
}

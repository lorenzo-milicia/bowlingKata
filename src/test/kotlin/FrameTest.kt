import domain.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FrameTest {

	@Test
	internal fun `regular frame`() {
		val frame = Frame()

		frame.roll(5)
		frame.roll(4)

		assertFalse(frame.firstRoll is EmptyRoll)
		assertFalse(frame.secondRoll is EmptyRoll)
		assertTrue(frame.isClosed)
	}

	@Test
	internal fun `incomplete frame`() {
		val frame = Frame()

		frame.roll(8)

		assertFalse(frame.firstRoll is EmptyRoll)
		assertTrue(frame.secondRoll is EmptyRoll)
		assertFalse(frame.isClosed)
	}

	@Test
	internal fun `strike frame`() {
		val frame = Frame()

		frame.roll(10)

		assertTrue(frame.firstRoll is Strike)
		assertTrue(frame.secondRoll is EmptyRoll)
		assertTrue(frame.isClosed)
	}

	@Test
	internal fun `spare frame`() {
		val frame = Frame()

		frame.roll(5)
		frame.roll(5)

		assertFalse(frame.firstRoll is EmptyRoll)
		assertTrue(frame.secondRoll is Spare)
		assertTrue(frame.isClosed)

	}

	@Test
	internal fun `cheating test`() {
		val frame = Frame()

		assertThrows<CheatingException>{frame.roll(12)}
	}

	@Test
	internal fun `cheating test 2`() {
		val frame = Frame()

		frame.roll(4)

		assertThrows<CheatingException> { frame.roll(10) }
	}
}
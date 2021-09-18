import domain.Frame
import domain.Spare
import domain.Strike
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

		assertNotNull(frame.firstRoll)
		assertNotNull(frame.secondRoll)
		assertTrue(frame.isClosed)
	}

	@Test
	internal fun `incomplete frame`() {
		val frame = Frame()

		frame.roll(8)

		assertNotNull(frame.firstRoll)
		assertNull(frame.secondRoll)
		assertFalse(frame.isClosed)
	}

	@Test
	internal fun `strike frame`() {
		val frame = Frame()

		frame.roll(10)

		assertTrue(frame.firstRoll is Strike)
		assertTrue(frame.secondRoll == null)
		assertTrue(frame.isClosed)
	}

	@Test
	internal fun `spare frame`() {
		val frame = Frame()

		frame.roll(5)
		frame.roll(5)

		assertNotNull(frame.firstRoll)
		assertTrue(frame.secondRoll is Spare)
		assertTrue(frame.isClosed)

	}

	@Test
	internal fun `cheating test`() {
		val frame = Frame()

		assertThrows<Exception>{frame.roll(12)}
	}

	@Test
	internal fun `cheating test 2`() {
		val frame = Frame()

		frame.roll(4)

		assertThrows<java.lang.Exception> { frame.roll(10) }
	}
}
import org.junit.jupiter.api.Test
import java.util.*

class WannaTrySomething {

	@Test
	internal fun `testing something`() {
		val queue = TestRollQueue(mutableListOf(2, 4, 10, 5, 5, 0, 7, 10))

		val roll = TestRollFactory.makeRolls(queue, null)

		val rolls = TestRollFactory.rolls.reversed()
		val points = rolls.map { it.points() }
		println()
	}
}

interface TestRoll {

	val pinsKnockedDown: Int
	val nextRoll: TestRoll?
	fun points(): Int
	fun two(): Int = pinsKnockedDown + (nextRoll?.one() ?: 0)
	fun one(): Int = pinsKnockedDown
}

class TestRegularRoll(
	override val pinsKnockedDown: Int,
	override val nextRoll: TestRoll?
): TestRoll {
	override fun points(): Int = pinsKnockedDown
}

class TestStrikeRoll(
	override val pinsKnockedDown: Int,
	override val nextRoll: TestRoll?
): TestRoll {

	override fun points(): Int =
		pinsKnockedDown + (nextRoll?.two() ?: 0)

}

class TestSpareRoll(
	override val pinsKnockedDown: Int,
	override val nextRoll: TestRoll?
): TestRoll {

	override fun points(): Int =
		pinsKnockedDown + (nextRoll?.one() ?: 0)

}


class TestRollQueue(private val rolls: MutableList<Int> = mutableListOf<Int>()) {

	fun isEmpty() = rolls.isEmpty()

	fun remove() = rolls.removeFirst()

}

object TestRollFactory {
	val rolls: MutableList<TestRoll> = mutableListOf()

	fun makeRolls(queue: TestRollQueue, spareCheck: Int?): TestRoll? {
		if(queue.isEmpty()) return null
		val pins = queue.remove()

		if (spareCheck == null) {
			return if (pins == 10) TestStrikeRoll(
				pins,
				makeRolls(queue, null)
			). also { rolls.add(it) }
			else TestRegularRoll(
				pins,
				makeRolls(queue, pins)
			). also { rolls.add(it) }
		}
		else {
			return if (pins + spareCheck == 10) TestSpareRoll(
				pins,
				makeRolls(queue, null)
			). also { rolls.add(it) }
			else TestRegularRoll(
				pins,
				makeRolls(queue, null)
			). also { rolls.add(it) }
		}
	}
}
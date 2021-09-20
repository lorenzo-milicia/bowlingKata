package domain

open class Frame {

	var firstRoll: Roll = EmptyRoll()
	var secondRoll: Roll = EmptyRoll()

	open val isClosed: Boolean
		get() = firstRoll is Strike || secondRoll !is EmptyRoll

	open val frameStatus: FrameStatus
		get() {
			if (firstRoll is EmptyRoll) return FrameStatus.EMPTY
			if (!isClosed) return FrameStatus.INCOMPLETE
			if (firstRoll is Strike) return FrameStatus.STRIKE
			if (secondRoll is Spare) return FrameStatus.SPARE
			else return FrameStatus.REGULAR
		}

	open fun roll(pinsKnockedDown: Int) {
		if (isClosed) return
		if (firstRoll is EmptyRoll) setFirstRoll(pinsKnockedDown)
		else setSecondRoll(pinsKnockedDown)
	}

	open fun setFirstRoll(pinsKnockedDown: Int) {
		if (pinsKnockedDown > 10) throw CheatingException()
		firstRoll = if (pinsKnockedDown == 10) Strike()
		else Roll(pinsKnockedDown)
	}

	open fun setSecondRoll(pinsKnockedDown: Int) {
		if (firstRoll.pinsKnockedDown + pinsKnockedDown > 10) throw CheatingException()
		secondRoll = if (firstRoll.pinsKnockedDown + pinsKnockedDown == 10) Spare(pinsKnockedDown)
		else Roll(pinsKnockedDown)
	}

	open val selfScore: Int
		get() {
			return firstRoll.pinsKnockedDown +
					secondRoll.pinsKnockedDown
		}

}

class LastFrame: Frame() {

	var thirdRoll: Roll = EmptyRoll()

	override val isClosed: Boolean
		get() {
			return if ((firstRoll is Strike || secondRoll is Spare) && thirdRoll !is EmptyRoll) true
			else firstRoll !is Strike && secondRoll !is Spare && secondRoll !is EmptyRoll
		}

	override val frameStatus: FrameStatus
		get() =
			if (isClosed) FrameStatus.LAST
			else if (firstRoll is EmptyRoll) FrameStatus.EMPTY
			else if (secondRoll is EmptyRoll) FrameStatus.LAST_ONLY_FIRST
			else FrameStatus.LAST_FIRST_AND_SECOND


	override fun roll(pinsKnockedDown: Int) {
		if (isClosed) return
		if (firstRoll is EmptyRoll) setFirstRoll(pinsKnockedDown)
		else if (secondRoll is EmptyRoll) setSecondRoll(pinsKnockedDown)
		else setThirdRoll(pinsKnockedDown)
	}


	override fun setFirstRoll(pinsKnockedDown: Int) {
		if (pinsKnockedDown > 10) throw CheatingException()
		firstRoll = if (pinsKnockedDown == 10) Strike()
		else Roll(pinsKnockedDown)
	}

	override fun setSecondRoll(pinsKnockedDown: Int) {
		if (pinsKnockedDown > 10) throw CheatingException()
		if (firstRoll is Strike) {
			secondRoll = if (pinsKnockedDown == 10) Strike()
			else Roll(pinsKnockedDown)
		}
		else {
			if (firstRoll.pinsKnockedDown + pinsKnockedDown > 10) throw CheatingException()
			if (firstRoll.pinsKnockedDown + pinsKnockedDown == 10) secondRoll = Spare(pinsKnockedDown)
			else secondRoll = Roll(pinsKnockedDown)
		}
	}

	private fun setThirdRoll(pinsKnockedDown: Int) {
		if (pinsKnockedDown > 10) throw CheatingException()
		thirdRoll = if (secondRoll is Strike || secondRoll is Spare) {
			if (pinsKnockedDown == 10) Strike()
			else Roll(pinsKnockedDown)
		}
		else {
			if (secondRoll.pinsKnockedDown + pinsKnockedDown > 10) throw CheatingException()
			if (secondRoll.pinsKnockedDown + pinsKnockedDown == 10) Spare(pinsKnockedDown)
			else Roll(pinsKnockedDown)
		}
	}

	override val selfScore: Int
		get() {
			return firstRoll.pinsKnockedDown +
					secondRoll.pinsKnockedDown +
					thirdRoll.pinsKnockedDown
		}
}
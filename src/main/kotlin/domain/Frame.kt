package domain

open class Frame {

	var firstRoll: Roll? = null
	var secondRoll: Roll? = null

	open val isClosed: Boolean
		get() = firstRoll is Strike || secondRoll != null

	open val frameStatus: FrameStatus
		get() {
			if (firstRoll == null) return FrameStatus.EMPTY
			if (!isClosed) return FrameStatus.INCOMPLETE
			if (firstRoll is Strike) return FrameStatus.STRIKE
			if (secondRoll is Spare) return FrameStatus.SPARE
			else return FrameStatus.REGULAR
		}

	open fun roll(pinsKnockedDown: Int) {
		if (isClosed) return
		if (firstRoll == null) setFirstRoll(pinsKnockedDown)
		else setSecondRoll(pinsKnockedDown)
	}

	open fun setFirstRoll(pinsKnockedDown: Int) {
		if (pinsKnockedDown > 10) throw CheatingException()
		firstRoll = if (pinsKnockedDown == 10) Strike()
		else Roll(pinsKnockedDown)
	}

	open fun setSecondRoll(pinsKnockedDown: Int) {
		if (firstRoll!!.pinsKnockedDown + pinsKnockedDown > 10) throw CheatingException()
		secondRoll = if (firstRoll!!.pinsKnockedDown + pinsKnockedDown == 10) Spare(pinsKnockedDown)
		else Roll(pinsKnockedDown)
	}

	open val selfScore: Int
		get() {
			return (firstRoll?.pinsKnockedDown ?: 0) +
					(secondRoll?.pinsKnockedDown ?: 0)
		}

}

class LastFrame: Frame() {

	var thirdRoll: Roll? = null

	override val isClosed: Boolean
		get() {
			return if ((firstRoll is Strike || secondRoll is Spare) && thirdRoll != null) true
			else firstRoll !is Strike && secondRoll !is Spare && secondRoll != null
		}

	override val frameStatus: FrameStatus
		get() =
			if (isClosed) FrameStatus.LAST
			else if (firstRoll == null) FrameStatus.EMPTY
			else if (secondRoll == null) FrameStatus.LAST_ONLY_FIRST
			else FrameStatus.LAST_FIRST_AND_SECOND


	override fun roll(pinsKnockedDown: Int) {
		if (isClosed) return
		if (firstRoll == null) setFirstRoll(pinsKnockedDown)
		else if (secondRoll == null) setSecondRoll(pinsKnockedDown)
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
			if (firstRoll!!.pinsKnockedDown + pinsKnockedDown > 10) throw CheatingException()
			if (firstRoll!!.pinsKnockedDown + pinsKnockedDown == 10) secondRoll = Spare(pinsKnockedDown)
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
			if (secondRoll!!.pinsKnockedDown + pinsKnockedDown > 10) throw CheatingException()
			if (secondRoll!!.pinsKnockedDown + pinsKnockedDown == 10) Spare(pinsKnockedDown)
			else Roll(pinsKnockedDown)
		}
	}

	override val selfScore: Int
		get() {
			return (firstRoll?.pinsKnockedDown ?: 0) +
					(secondRoll?.pinsKnockedDown ?: 0) +
					(thirdRoll?.pinsKnockedDown ?: 0)
		}
}
package domain

open class Frame {

	var firstRoll: Roll? = null
	var secondRoll: Roll? = null
	var isClosed: Boolean = false

	open fun roll(pinsKnockedDown: Int) {
		if (isClosed) return
		if (firstRoll == null) setFirstRoll(pinsKnockedDown)
		else setSecondRoll(pinsKnockedDown)
	}

	open fun setFirstRoll(pinsKnockedDown: Int) {
		if (pinsKnockedDown > 10) throw CheatingException()
		firstRoll = if (pinsKnockedDown == 10) Strike().also { isClosed = true }
		else Roll(pinsKnockedDown)
	}

	open fun setSecondRoll(pinsKnockedDown: Int) {
		if (firstRoll!!.pinsKnockedDown + pinsKnockedDown > 10) throw CheatingException()
		secondRoll = if (firstRoll!!.pinsKnockedDown + pinsKnockedDown == 10) Spare(pinsKnockedDown)
		else Roll(pinsKnockedDown)
		isClosed = true
	}
}

class LastFrame: Frame() {
	var thirdRoll: Roll? = null

	override fun roll(pinsKnockedDown: Int) {
		if (isClosed) return
		if (firstRoll == null) setFirstRoll(pinsKnockedDown)
		else if (secondRoll == null) setSecondRoll(pinsKnockedDown)
		else setThirdRoll(pinsKnockedDown)
	}

	private fun setThirdRoll(pinsKnockedDown: Int) {
		if (pinsKnockedDown > 10) throw CheatingException()
		thirdRoll = if (secondRoll is Strike) {
			if (pinsKnockedDown == 10) Strike()
			else Roll(pinsKnockedDown)
		}
		else if (secondRoll is Spare) {
			if (pinsKnockedDown == 10) Strike()
			else Roll(pinsKnockedDown)
		}
		else {
			if (secondRoll!!.pinsKnockedDown + pinsKnockedDown > 10) throw CheatingException()
			if (secondRoll!!.pinsKnockedDown + pinsKnockedDown == 10) Spare(pinsKnockedDown)
			else Roll(pinsKnockedDown)
		}
		isClosed = true
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
			else secondRoll = Roll(pinsKnockedDown).also { isClosed = true }
		}
	}
}
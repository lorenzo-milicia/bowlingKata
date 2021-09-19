package domain

object ScoreComputer {

	fun computeFramesScore(frames: List<Frame>): List<Int?> {
		return frames.mapIndexed { index, frame ->
			when (frame.frameStatus) {
				FrameStatus.EMPTY -> null
				FrameStatus.INCOMPLETE ->null
				FrameStatus.LAST -> if (!frame.isClosed) null else frame.selfScore
				FrameStatus.REGULAR -> frame.selfScore
				FrameStatus.SPARE -> {
					if (index == frames.lastIndex) null
					else computeSpareFrameScore(frame, frames[index + 1])
				}
				FrameStatus.STRIKE -> {
					when (index) {
						frames.lastIndex   -> null
						frames.lastIndex-1 -> computeStrikeFrameScore(frame, frames[index + 1])
						else               -> computeStrikeFrameScore(frame, frames[index + 1], frames[index + 2])
					}
				}
			}
		}
	}

	private fun computeSpareFrameScore(referenceFrame: Frame, bonusFrame: Frame): Int? {
		return if (bonusFrame.frameStatus != FrameStatus.EMPTY) referenceFrame.selfScore + bonusFrame.firstRoll!!.pinsKnockedDown
		else null
	}

	private fun computeStrikeFrameScore(referenceFrame: Frame, firstBonusFrame: Frame): Int? {
		return when (firstBonusFrame.frameStatus) {
			FrameStatus.STRIKE -> null
			FrameStatus.EMPTY -> null
			FrameStatus.INCOMPLETE -> null
			else -> referenceFrame.selfScore + referenceFrame.selfScore
		}
	}

	private fun computeStrikeFrameScore(referenceFrame: Frame, firstBonusFrame: Frame, secondBonusFrame: Frame): Int? {
		if (firstBonusFrame.frameStatus == FrameStatus.STRIKE && secondBonusFrame.frameStatus == FrameStatus.EMPTY) return null
		if (firstBonusFrame.frameStatus == FrameStatus.STRIKE) return referenceFrame.selfScore + firstBonusFrame.selfScore + secondBonusFrame.firstRoll!!.pinsKnockedDown
		else return referenceFrame.selfScore + firstBonusFrame.selfScore
	}
}
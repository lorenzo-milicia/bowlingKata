package domain

object ScoreComputer {

	fun computeFramesScore(frames: List<Frame>): List<FrameScore> {
		return frames.mapIndexed { index, frame ->
			when (frame.frameStatus) {
				FrameStatus.EMPTY -> FrameScore(0,true)
				FrameStatus.INCOMPLETE -> FrameScore(frame.selfScore, true)
				FrameStatus.LAST -> if (!frame.isClosed) FrameScore(frame.selfScore, true) else FrameScore(frame.selfScore, false)
				FrameStatus.REGULAR -> FrameScore(frame.selfScore, false)
				FrameStatus.SPARE -> {
					if (index == frames.lastIndex) FrameScore(10, true)
					else computeSpareFrameScore(frame, frames[index + 1])
				}
				FrameStatus.STRIKE -> {
					when (index) {
						frames.lastIndex   -> FrameScore(10, true)
						frames.lastIndex-1 -> computeStrikeFrameScore(frame, frames[index + 1])
						else               -> computeStrikeFrameScore(frame, frames[index + 1], frames[index + 2])
					}
				}
			}
		}
	}

	fun computeTotalScore(frames: List<Frame>): Int = computeFramesScore(frames).sumOf { it.score }

	private fun computeSpareFrameScore(referenceFrame: Frame, bonusFrame: Frame): FrameScore {
		return if (bonusFrame.frameStatus != FrameStatus.EMPTY) FrameScore(referenceFrame.selfScore + bonusFrame.firstRoll!!.pinsKnockedDown, false)
		else FrameScore(referenceFrame.selfScore, true)
	}

	private fun computeStrikeFrameScore(referenceFrame: Frame, firstBonusFrame: Frame): FrameScore {
		return when (firstBonusFrame.frameStatus) {
			FrameStatus.STRIKE -> FrameScore(20, true)
			FrameStatus.EMPTY -> FrameScore(10, true)
			FrameStatus.INCOMPLETE -> FrameScore(10 + firstBonusFrame.firstRoll!!.pinsKnockedDown, true)
			else -> FrameScore(referenceFrame.selfScore + referenceFrame.selfScore, false)
		}
	}

	private fun computeStrikeFrameScore(referenceFrame: Frame, firstBonusFrame: Frame, secondBonusFrame: Frame): FrameScore {
		if (firstBonusFrame.frameStatus == FrameStatus.STRIKE && secondBonusFrame.frameStatus == FrameStatus.EMPTY) FrameScore(20, true)
		if (firstBonusFrame.frameStatus == FrameStatus.STRIKE) return FrameScore(referenceFrame.selfScore + firstBonusFrame.selfScore + secondBonusFrame.firstRoll!!.pinsKnockedDown, false)
		else return FrameScore(referenceFrame.selfScore + firstBonusFrame.selfScore, false)
	}
}
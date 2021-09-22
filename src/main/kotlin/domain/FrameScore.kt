package domain

data class FrameScore(
	val score: Int,
	//cambiare in non negazione
	val isIncomplete: Boolean
)
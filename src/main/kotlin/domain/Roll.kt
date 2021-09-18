package domain

open class Roll(
	val pinsKnockedDown: Int
)

class Spare(
	pinsKnockedDown: Int
): Roll(pinsKnockedDown)

class Strike(): Roll(10)
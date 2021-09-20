package domain

enum class FrameStatus {
	REGULAR,
	STRIKE,
	SPARE,
	LAST,
	LAST_ONLY_FIRST,
	LAST_FIRST_AND_SECOND,
	INCOMPLETE,
	EMPTY
}
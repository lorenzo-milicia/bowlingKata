package domain

class ConsoleDisplay: IDisplay {

	override fun displayFramesScore(framesScore: List<FrameScore>) {
		println("----------------------------------------------")
		print("|")
		framesScore.forEach {
			print(" ${if (it.isIncomplete) "-" else it.score} |")
		}
		print("\n")
		println("----------------------------------------------")
	}

	override fun displayTotalScore(score: Int) {
		println("-----")
		println("$score")
		println("-----")
	}

}
package com.github.mrko900.braintrainer

class MathChainsExerciseLogic(
    val totalRounds: Int,
    initialNChains: Int
) {
    var nChains = initialNChains
        private set

    private val chainVals0 = ArrayList<Int>()

    val chainVals: List<Int>
        get() = chainVals0

    init {
        for (i in 1..initialNChains) {
            chainVals0.add(34) // TODO
        }
    }
}

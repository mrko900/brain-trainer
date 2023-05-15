package com.github.mrko900.braintrainer

import java.util.Collections

class MathChainsExerciseLogic(
    val totalRounds: Int,
    initialNChains: Int
) {
    var nChains = initialNChains
        private set

    val chainVals: MutableList<Int> = ArrayList()
        get() = Collections.unmodifiableList(field)

    
}

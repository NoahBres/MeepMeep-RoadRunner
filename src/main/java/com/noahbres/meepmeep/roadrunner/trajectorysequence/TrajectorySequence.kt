package com.noahbres.meepmeep.roadrunner.trajectorysequence

import com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep.SequenceStep

class TrajectorySequence {
    private val sequenceList = mutableListOf<SequenceStep>()

    val size: Int
        get() = sequenceList.size

    fun add(step: SequenceStep) {
        sequenceList.add(step)
    }

    fun get(i: Int): SequenceStep {
        return sequenceList[i]
    }
}
package com.noahbres.meepmeep.roadrunner.trajectorysequence

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep.SequenceStep
import com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep.TrajectoryStep

class TrajectorySequence : List<SequenceStep> {
    private val sequenceList = mutableListOf<SequenceStep>()

    fun firstPose(): Pose2d {
        this.forEach {
            if(it is TrajectoryStep) return it.trajectory.start()
        }

        return Pose2d()
    }

    fun add(step: SequenceStep) {
        sequenceList.add(step)
    }

    override fun contains(element: SequenceStep) = sequenceList.contains(element)

    override fun containsAll(elements: Collection<SequenceStep>) = sequenceList.containsAll(elements)

    override fun indexOf(element: SequenceStep) = sequenceList.indexOf(element)

    override fun isEmpty() = sequenceList.isEmpty()

    override fun iterator() = sequenceList.iterator()

    override fun lastIndexOf(element: SequenceStep) = sequenceList.lastIndexOf(element)

    override fun listIterator() = sequenceList.listIterator()

    override fun listIterator(index: Int) = sequenceList.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int) = sequenceList.subList(fromIndex, toIndex)

    override val size: Int
        get() = sequenceList.size

    override fun get(index: Int): SequenceStep {
        return sequenceList[index]
    }
}
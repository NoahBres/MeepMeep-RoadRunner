package com.noahbres.meepmeep.roadrunner.trajectorysequence

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep.SequenceStep
import com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep.TrajectoryStep
import com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep.TurnStep
import com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep.WaitStep

class TrajectorySequence : List<SequenceStep> {
    private val sequenceList = mutableListOf<SequenceStep>()

    private var cachedFirstPose = Pose2d()
    private var firstPoseCachedDirty = false

    private var cachedDuration = 0.0
    private var durationCacheDirty = false

    val firstPose: Pose2d
        get() {
            if (!firstPoseCachedDirty) return cachedFirstPose

            this.forEach {
                if (it is TrajectoryStep) {
                    cachedFirstPose = it.trajectory.start()
                    return cachedFirstPose
                }
            }

            return Pose2d()
        }

    val duration: Double
        get() {
            if(!durationCacheDirty) return cachedDuration

            cachedDuration = 0.0
            this.forEach {
                if(it is TrajectoryStep)
                    cachedDuration += it.trajectory.duration()
                if(it is TurnStep)
                    cachedDuration += it.motionProfile.duration()
                else if(it is WaitStep)
                    cachedDuration+= it.seconds
            }

            return cachedDuration
        }

    fun add(step: SequenceStep) {
        sequenceList.add(step)

        firstPoseCachedDirty = true
        durationCacheDirty
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
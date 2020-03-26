package com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep

import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.profile.MotionProfile
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.noahbres.meepmeep.roadrunner.trajectorysequence.WaitCallback

sealed class SequenceStep(open val startTime: Double, open val duration: Double)

data class TrajectoryStep(
        val trajectory: Trajectory,
        override val startTime: Double, override val duration: Double
) : SequenceStep(startTime, duration)

data class TurnStep(
        val pos: Vector2d, val angle: Double, val motionProfile: MotionProfile,
        override val startTime: Double, override val duration: Double
) : SequenceStep(startTime, duration)

data class WaitStep(
        val seconds: Double, override val startTime: Double,
        override val duration: Double
) : SequenceStep(startTime, duration)

data class WaitConditionalStep(
        val callback: WaitCallback,
        override val startTime: Double,
        override val duration: Double
) : SequenceStep(startTime, duration)
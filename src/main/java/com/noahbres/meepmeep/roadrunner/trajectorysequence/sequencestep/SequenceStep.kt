package com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep

import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.profile.MotionProfile
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.noahbres.meepmeep.roadrunner.trajectorysequence.WaitCallback

sealed class SequenceStep

data class TrajectoryStep(val trajectory: Trajectory): SequenceStep()
data class TurnStep(val pos: Vector2d, val angle: Double, val motionProfile: MotionProfile): SequenceStep()
data class WaitStep(val seconds: Double): SequenceStep()
data class WaitConditionalStep(val callback: WaitCallback): SequenceStep()
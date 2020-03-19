package com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep

import com.acmerobotics.roadrunner.trajectory.Trajectory

class TrajectoryStep(val trajectory: Trajectory): SequenceStep {
    override val type= SequenceStepType.TRAJECTORY
}
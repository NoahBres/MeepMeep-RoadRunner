package com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep

import com.acmerobotics.roadrunner.profile.MotionProfile

class TurnStep(val angle: Double, val motionProfile: MotionProfile): SequenceStep {
    override val type = SequenceStepType.TURN
}
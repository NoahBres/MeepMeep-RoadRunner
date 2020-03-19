package com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep

class WaitStep(val seconds: Double): SequenceStep {
    override val type = SequenceStepType.WAIT
}
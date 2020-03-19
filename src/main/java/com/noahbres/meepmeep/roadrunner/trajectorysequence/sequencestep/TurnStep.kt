package com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep

class TurnStep(val angle: Double): SequenceStep {
    override val type = SequenceStepType.TURN
}
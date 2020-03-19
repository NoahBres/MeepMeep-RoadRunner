package com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep

import com.noahbres.meepmeep.roadrunner.trajectorysequence.WaitCallback

class WaitConditionalStep(val callback: WaitCallback): SequenceStep {
    override val type = SequenceStepType.WAIT_CONDITIONAL
}
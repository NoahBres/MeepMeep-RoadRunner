package com.noahbres.meepmeep.roadrunner

import com.noahbres.meepmeep.core.MeepMeep

class MeepMeepRoadRunner(windowSize: Int): MeepMeep<MeepMeepRoadRunner>(windowSize) {
    override fun start(): MeepMeepRoadRunner {
       super.start()

        removeEntity(DEFAULT_BOT_ENTITY)

        return this
    }

    fun addTrajectory(): MeepMeepRoadRunner {
        return this
    }
}

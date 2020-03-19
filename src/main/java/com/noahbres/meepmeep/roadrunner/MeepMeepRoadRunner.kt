package com.noahbres.meepmeep.roadrunner

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryConstraints
import com.noahbres.meepmeep.core.MeepMeep
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence

class MeepMeepRoadRunner(windowSize: Int) : MeepMeep<MeepMeepRoadRunner>(windowSize) {
    companion object {
        @JvmStatic
        lateinit var DEFAULT_ROADRUNNER_BOT_ENTITY: RoadRunnerBotEntity
    }

    init {
        DEFAULT_ROADRUNNER_BOT_ENTITY = RoadRunnerBotEntity(
                DriveConstraints(
                        30.0, 30.0, 0.0,
                        Math.toRadians(180.0), Math.toRadians(180.0), 0.0
                ),
                18.0, 18.0,
                Pose2d(), colorManager.theme, 0.8)

        addEntity(DEFAULT_ROADRUNNER_BOT_ENTITY)
    }

    override fun start(): MeepMeepRoadRunner {
        super.start()

        removeEntity(DEFAULT_BOT_ENTITY)

        return this
    }

    //-------------Robot Settings-------------//
    override fun setBotDimensions(width: Double, height: Double): MeepMeepRoadRunner {
        if (DEFAULT_ROADRUNNER_BOT_ENTITY in entityList)
            DEFAULT_ROADRUNNER_BOT_ENTITY.setDimensions(width, height)

        return this
    }

    //-------------Road Runner Settings-------------//
    fun setStartPose(pose: Pose2d): MeepMeepRoadRunner {
        if (DEFAULT_ROADRUNNER_BOT_ENTITY in entityList) DEFAULT_ROADRUNNER_BOT_ENTITY.pose = pose.toMeepMeepPose()

        return this
    }

    fun setConstraints(constraints: TrajectoryConstraints): MeepMeepRoadRunner {
        if (DEFAULT_ROADRUNNER_BOT_ENTITY in entityList)
            DEFAULT_ROADRUNNER_BOT_ENTITY.constraints = constraints

        return this
    }

    fun addTrajectorySequence(callback: AddTrajectorySequenceCallback): MeepMeepRoadRunner {
        if (DEFAULT_ROADRUNNER_BOT_ENTITY in entityList)
            DEFAULT_ROADRUNNER_BOT_ENTITY.addTrajectorySequence(
                    callback.buildTrajectorySequence(DEFAULT_ROADRUNNER_BOT_ENTITY.drive)
            )

        return this
    }

    fun addTrajectorySequence(trajectorySequence: TrajectorySequence): MeepMeepRoadRunner {
        if (DEFAULT_ROADRUNNER_BOT_ENTITY in entityList)
            DEFAULT_ROADRUNNER_BOT_ENTITY.addTrajectorySequence(trajectorySequence)

        return this
    }

    fun addTrajectory(callback: AddTrajectoryCallback): MeepMeepRoadRunner {
        if (DEFAULT_ROADRUNNER_BOT_ENTITY in entityList)
            DEFAULT_ROADRUNNER_BOT_ENTITY.addTrajectory(
                    callback.buildTrajectory(DEFAULT_ROADRUNNER_BOT_ENTITY.drive)
            )

        return this
    }

    fun addTrajectory(trajectory: Trajectory): MeepMeepRoadRunner {
        if (DEFAULT_ROADRUNNER_BOT_ENTITY in entityList)
            DEFAULT_ROADRUNNER_BOT_ENTITY.addTrajectory(trajectory)

        return this
    }
}

fun com.noahbres.meepmeep.core.util.Pose2d.toRRPose() = Pose2d(this.x, this.y, this.heading)
fun Pose2d.toMeepMeepPose() = com.noahbres.meepmeep.core.util.Pose2d(this.x, this.y, this.heading)
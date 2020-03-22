package com.noahbres.meepmeep.roadrunner

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryConstraints
import com.noahbres.meepmeep.core.MeepMeep
import com.noahbres.meepmeep.core.colorscheme.ColorScheme
import com.noahbres.meepmeep.core.entity.BotEntity
import com.noahbres.meepmeep.roadrunner.entity.TrajectorySequenceEntity
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence
import com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep.TrajectoryStep

class RoadRunnerBotEntity(
        meepMeep: MeepMeep<*>,
        var constraints: TrajectoryConstraints,
        width: Double, height: Double,
        pose: Pose2d,
        private val colorScheme: ColorScheme,
        opacity: Double
) : BotEntity(meepMeep, width, height, pose.toMeepMeepPose(), colorScheme, opacity) {
    override val zIndex: Int = 3

    var drive = DriveShim(constraints)

    var followMode = FollowMode.TRAJECTORY_LIST

    private var currentTrajectoryList = emptyList<Trajectory>()
    private var currentTrajectorySequence: TrajectorySequence? = null

    private var currentTrajectorySequenceIndex = 0

    private var trajectorySequenceEntity: TrajectorySequenceEntity? = null

    override fun update(deltaTime: Long) {
        if (followMode == FollowMode.TRAJECTORY_LIST) {

        } else if (followMode == FollowMode.TRAJECTORY_SEQUENCE && currentTrajectorySequence != null) {
            val currentStep = currentTrajectorySequence?.get(currentTrajectorySequenceIndex)

            if (currentStep is TrajectoryStep) {
//                println(currentStep.trajectory)
            }
        }
    }

    fun followTrajectorySequence(sequence: TrajectorySequence) {
        followMode = FollowMode.TRAJECTORY_SEQUENCE

        currentTrajectorySequence = sequence

        trajectorySequenceEntity = TrajectorySequenceEntity(meepMeep, sequence, colorScheme)
        meepMeep.addEntity(trajectorySequenceEntity!!)
    }

    fun followTrajectoryList(trajectoryList: List<Trajectory>) {
        followMode = FollowMode.TRAJECTORY_LIST

        currentTrajectoryList = trajectoryList
    }

    enum class FollowMode {
        TRAJECTORY_LIST,
        TRAJECTORY_SEQUENCE
    }
}
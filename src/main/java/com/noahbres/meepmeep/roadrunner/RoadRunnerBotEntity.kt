package com.noahbres.meepmeep.roadrunner

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryConstraints
import com.noahbres.meepmeep.core.colorscheme.ColorScheme
import com.noahbres.meepmeep.core.entity.BotEntity
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence

class RoadRunnerBotEntity(var constraints: TrajectoryConstraints, width: Double, height: Double, pose: Pose2d, colorScheme: ColorScheme, opacity: Double) : BotEntity(width, height, pose.toMeepMeepPose(), colorScheme, opacity) {
    var drive = DriveShim(constraints)

    // List of either <TrajectorySequence> or <Trajectory> to follow
    private val followList = mutableListOf<Any>()
    // List of followed <TrajectorySequence> or <Trajectory>
    private val followedList = mutableListOf<Any>()

    // For following either <TrajectorySequence> or <Trajectory>
    private var isCurrentlyFollowingSomeTrajectory = false
    private var someTrajectoryCurrentlyFollowing: Any? = null

    // For following <TrajectorySequence>
    private var stepInTrajectorySequence = -1
    private var isCurrentlyFollowingStepInTrajectorySequence = false

    // For following <Trajectory>

    @ExperimentalStdlibApi
    override fun update(deltaTime: Long) {
        if(!isCurrentlyFollowingSomeTrajectory && followList.isNotEmpty()) {
            someTrajectoryCurrentlyFollowing = followList.first()
            followedList.add(followList.first())

            followList.removeFirst()

            isCurrentlyFollowingSomeTrajectory = true
        }

        if(someTrajectoryCurrentlyFollowing != null) {
            if(someTrajectoryCurrentlyFollowing is TrajectorySequence) {
                val ts = someTrajectoryCurrentlyFollowing as TrajectorySequence

                // Bail out and stop following once we have finished the TrajectorySequence stack
                if(stepInTrajectorySequence >= ts.size) {
                    isCurrentlyFollowingSomeTrajectory = false
                    someTrajectoryCurrentlyFollowing = null

                    isCurrentlyFollowingStepInTrajectorySequence = false
                    stepInTrajectorySequence = -1

                    return
                }

                if(!isCurrentlyFollowingStepInTrajectorySequence) {
                    stepInTrajectorySequence++
                }

//                println((currentlyFollowing as TrajectorySequence).size)
            } else if(someTrajectoryCurrentlyFollowing is Trajectory) {

            }
        }
    }

    fun addTrajectorySequence(trajectorySequence: TrajectorySequence) {
        println(trajectorySequence.size)
        followList.add(trajectorySequence)
    }

    fun addTrajectory(trajectory: Trajectory) {

    }
}
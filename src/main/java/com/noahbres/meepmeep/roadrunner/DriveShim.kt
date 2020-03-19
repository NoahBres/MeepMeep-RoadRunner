package com.noahbres.meepmeep.roadrunner

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryConstraints
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder

class DriveShim(private val constraints: TrajectoryConstraints) {
    fun trajectorySequenceBuilder(startPose: Pose2d): TrajectorySequenceBuilder {
        return TrajectorySequenceBuilder(startPose, constraints)
    }

    @JvmOverloads
    fun trajectoryBuilder(startPose: Pose2d, startHeading: Double = startPose.heading): TrajectoryBuilder {
        return TrajectoryBuilder(startPose, startHeading, constraints)
    }

    fun trajectoryBuilder(startPose: Pose2d, reversed: Boolean): TrajectoryBuilder {
        return TrajectoryBuilder(startPose, reversed, constraints)
    }
}
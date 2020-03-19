package com.noahbres.meepmeep.roadrunner.trajectorysequence.bufferedmarker

import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.MarkerCallback

class BufferedSpatialMarker (val point: Vector2d, val callback: MarkerCallback): BufferedMarker
package com.noahbres.meepmeep.roadrunner.trajectorysequence.bufferedmarker

import com.acmerobotics.roadrunner.trajectory.MarkerCallback

class BufferedDisplacementMarker(val displacement: (Double) -> Double, val callback: MarkerCallback): BufferedMarker
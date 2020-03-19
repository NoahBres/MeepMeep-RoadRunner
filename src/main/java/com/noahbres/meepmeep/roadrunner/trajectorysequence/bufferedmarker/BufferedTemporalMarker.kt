package com.noahbres.meepmeep.roadrunner.trajectorysequence.bufferedmarker

import com.acmerobotics.roadrunner.trajectory.MarkerCallback

class BufferedTemporalMarker(val time: (Double) -> Double, val callback: MarkerCallback): BufferedMarker
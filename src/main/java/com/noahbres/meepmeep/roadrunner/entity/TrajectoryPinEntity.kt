package com.noahbres.meepmeep.roadrunner.entity

import com.noahbres.meepmeep.core.MeepMeep
import com.noahbres.meepmeep.core.colorscheme.ColorScheme
import com.noahbres.meepmeep.core.entity.ThemedEntity
import com.noahbres.meepmeep.core.util.FieldUtil
import java.awt.Graphics2D

class TrajectoryPinEntity(override val meepMeep: MeepMeep<*>, private var colorScheme: ColorScheme) : ThemedEntity {
    private var canvasWidth = FieldUtil.CANVAS_WIDTH
    private var canvasHeight = FieldUtil.CANVAS_HEIGHT

    override val zIndex = 5

    override fun update(deltaTime: Long) {
    }

    override fun render(gfx: Graphics2D, canvasWidth: Int, canvasHeight: Int) {
    }

    override fun setCanvasDimensions(canvasWidth: Double, canvasHeight: Double) {
        this.canvasWidth = canvasWidth
        this.canvasHeight = canvasHeight
    }

    override fun switchScheme(scheme: ColorScheme) {
        this.colorScheme = scheme
    }
}

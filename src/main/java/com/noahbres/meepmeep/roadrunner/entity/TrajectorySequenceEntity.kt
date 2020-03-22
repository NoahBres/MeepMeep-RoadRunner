package com.noahbres.meepmeep.roadrunner.entity

import com.acmerobotics.roadrunner.geometry.Vector2d
import com.noahbres.meepmeep.core.MeepMeep
import com.noahbres.meepmeep.core.colorscheme.ColorScheme
import com.noahbres.meepmeep.core.entity.ThemedEntity
import com.noahbres.meepmeep.core.util.FieldUtil
import com.noahbres.meepmeep.roadrunner.*
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence
import com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep.TrajectoryStep
import com.noahbres.meepmeep.roadrunner.trajectorysequence.sequencestep.TurnStep
import java.awt.*
import java.awt.geom.Path2D
import java.awt.image.BufferedImage
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.roundToInt

class TrajectorySequenceEntity(
        override val meepMeep: MeepMeep<*>,
        private val trajectorySequence: TrajectorySequence,
        private var colorScheme: ColorScheme
) : ThemedEntity {
    private var canvasWidth = FieldUtil.CANVAS_WIDTH
    private var canvasHeight = FieldUtil.CANVAS_HEIGHT

    override val zIndex: Int = 2

    private val PATH_STROKE_WIDTH = 0.8

    private val TURN_CIRCLE_WIDTH = 1.0
    private val TURN_ARC_WIDTH = 7.0
    private val TURN_STROKE_WIDTH = 0.5
    private val TURN_ARROW_LENGTH = 1.5
    private val TURN_ARROW_ANGLE = 30.0.toRadians()
    private val TURN_ARROW_ANGLE_ADJUSTMENT = -12.5.toRadians()

    private val SAMPLE_RESOLUTION = 1.2

    private lateinit var baseBufferedImage: BufferedImage

    init {
        redrawPath()
    }

    private fun redrawPath() {
        val environment = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val device = environment.defaultScreenDevice
        val config = device.defaultConfiguration

        baseBufferedImage = config.createCompatibleImage(canvasWidth.toInt(), canvasHeight.toInt(), Transparency.TRANSLUCENT)
        val gfx = baseBufferedImage.createGraphics()

        gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        gfx.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

        val turnIndicatorList = mutableListOf<TurnIndicator>()

        val path2d = Path2D.Double()

        var currentEndPose = trajectorySequence.firstPose()

        trajectorySequence.forEach { step ->
            if (step is TrajectoryStep) {
                val traj = step.trajectory

                gfx.stroke = BasicStroke(FieldUtil.scaleInchesToPixel(PATH_STROKE_WIDTH).toFloat(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND)
                gfx.color = colorScheme.TRAJCETORY_PATH_COLOR

                val displacementSamples = (traj.path.length() / SAMPLE_RESOLUTION).roundToInt()

                val displacements = (0..displacementSamples).map {
                    it / displacementSamples.toDouble() * traj.path.length()
                }

                val poses = displacements.map { traj.path[it] }
                val firstVec = FieldUtil.fieldCoordsToScreenCoords(poses.first().vec().toMeepMeepVector())
                path2d.moveTo(firstVec.x, firstVec.y)

                for (pose in poses.drop(1)) {
                    val coord = FieldUtil.fieldCoordsToScreenCoords(pose.vec().toMeepMeepVector())
                    path2d.lineTo(coord.x, coord.y)
                }

                currentEndPose = step.trajectory.end()
            } else if (step is TurnStep) {
                turnIndicatorList.add(TurnIndicator(currentEndPose.vec(), currentEndPose.heading, currentEndPose.heading + step.angle))
            }
        }

        gfx.draw(path2d)

        gfx.color = colorScheme.TRAJECTORY_TURN_COLOR
        gfx.stroke = BasicStroke(TURN_STROKE_WIDTH.scaleInToPixel().toFloat(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)

        turnIndicatorList.forEach {
            gfx.fillOval(
                    (it.pos.toScreenCoord().x - TURN_CIRCLE_WIDTH.scaleInToPixel() / 2).toInt(),
                    (it.pos.toScreenCoord().y - TURN_CIRCLE_WIDTH.scaleInToPixel() / 2).toInt(),
                    TURN_CIRCLE_WIDTH.scaleInToPixel().toInt(), TURN_CIRCLE_WIDTH.scaleInToPixel().toInt()
            )

            gfx.drawArc(
                    (it.pos.toScreenCoord().x - TURN_ARC_WIDTH.scaleInToPixel() / 2).toInt(),
                    (it.pos.toScreenCoord().y - TURN_ARC_WIDTH.scaleInToPixel() / 2).toInt(),
                    TURN_ARC_WIDTH.scaleInToPixel().toInt(), TURN_ARC_WIDTH.scaleInToPixel().toInt(),
                    min(it.startAngle.toDegrees().toInt(), it.endAngle.toDegrees().toInt()),
                    abs(it.startAngle.toDegrees().toInt() - it.endAngle.toDegrees().toInt())
            )

            val arrowPointVec = Vector2d(TURN_ARC_WIDTH / 2, 0.0).rotated(it.endAngle)
            val translatedPoint = (it.pos + arrowPointVec).toScreenCoord()

            var arrow1Rotated = it.endAngle - 90.0.toRadians() + TURN_ARROW_ANGLE + TURN_ARROW_ANGLE_ADJUSTMENT
            if(it.endAngle < it.startAngle) arrow1Rotated = 360.0.toRadians() - arrow1Rotated

            var arrow2Rotated = it.endAngle - 90.0.toRadians() - TURN_ARROW_ANGLE + TURN_ARROW_ANGLE_ADJUSTMENT
            if(it.endAngle < it.startAngle) arrow2Rotated = 360.0.toRadians() - arrow2Rotated

            val arrowEndVec1 = (it.pos + arrowPointVec) + Vector2d(TURN_ARROW_LENGTH, 0.0)
                    .rotated(arrow1Rotated)
            val translatedArrowEndVec1 = arrowEndVec1.toScreenCoord()

            val arrowEndVec2 = (it.pos + arrowPointVec) + Vector2d(TURN_ARROW_LENGTH, 0.0).rotated(arrow2Rotated)
            val translatedArrowEndVec2 = arrowEndVec2.toScreenCoord()

            gfx.drawLine(translatedPoint.x.toInt(), translatedPoint.y.toInt(), translatedArrowEndVec1.x.toInt(), translatedArrowEndVec1.y.toInt())
            gfx.drawLine(translatedPoint.x.toInt(), translatedPoint.y.toInt(), translatedArrowEndVec2.x.toInt(), translatedArrowEndVec2.y.toInt())
        }
    }

    override fun update(deltaTime: Long) {

    }

    override fun render(gfx: Graphics2D, canvasWidth: Int, canvasHeight: Int) {
        gfx.drawImage(baseBufferedImage, null, 0, 0)
    }

    override fun setCanvasDimensions(canvasWidth: Double, canvasHeight: Double) {
        if (this.canvasWidth != canvasWidth || this.canvasHeight != canvasHeight) redrawPath()
        this.canvasWidth = canvasWidth
        this.canvasHeight = canvasHeight
    }

    override fun switchScheme(scheme: ColorScheme) {
        if (this.colorScheme != scheme) redrawPath()
        this.colorScheme = scheme
    }

    data class TurnIndicator(val pos: Vector2d, val startAngle: Double, val endAngle: Double)
}
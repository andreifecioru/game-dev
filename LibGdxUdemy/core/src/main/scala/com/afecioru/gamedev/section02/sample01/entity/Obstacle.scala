package com.afecioru.gamedev.section02.sample01.entity

import com.afecioru.gamedev.section02.GdxUtils
import com.afecioru.gamedev.section02.common.GameScreenInfo
import com.afecioru.gamedev.section02.common.extensions.ShapeRendererExtensions
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.{MathUtils, Rectangle, Vector2}

final case class Obstacle(private val fallSpeed: Float,
                          private val startPosition: Vector2)
                         (implicit screenInfo:  GameScreenInfo) extends Entity {
  import GdxUtils._
  import Obstacle._
  import ShapeRendererExtensions._

  private val random = scala.util.Random

  override def boundsRadius: Float = BOUNDS_RADIUS

  override val initialPos: Vector2 = startPosition
  override val initialSpeed: Vector2 = new Vector2(0.0f, fallSpeed)
  override val ui: GameScreenInfo = screenInfo

  private val rectangle: Rectangle = new Rectangle(
    initialPos.x - boundsRadius, // x
    initialPos.y - boundsRadius, // y
    boundsSize, // width
    boundsSize  // height
  )

  override def refresh(): Unit = {
    super.refresh()

    rectangle.x = pos.x - boundsRadius
    rectangle.y = pos.y - boundsRadius
  }

  override def update(): Unit = {
    val posY = MathUtils.clamp(pos.y - speed.y * DELTA_TIME, -boundsSize, ui.WORLD_HEIGHT + boundsSize)

    if (posY <= -boundsRadius) {
      val posX = random.nextFloat() * ui.WORLD_WIDTH
      pos = new Vector2(posX, initialPos.y)
    } else {
      pos = new Vector2(pos.x, posY)
    }
  }

  override def draw(): Unit = {
    ui.renderer.withShape(ShapeType.Filled) {
      ui.renderer.withColor(Color.RED) {
        ui.renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height)
      }
    }
  }

  // -------------[ INITIALIZATION ]-------------
  {
    init()
  }
}

object Obstacle {
  private val BOUNDS_RADIUS = 0.4f
}

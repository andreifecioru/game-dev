package com.afecioru.gamedev.section02.sample01.entity

import com.afecioru.gamedev.section02.common.{DirectionalControls, GameScreenInfo}
import com.afecioru.gamedev.section02.common.extensions.ShapeRendererExtensions
import com.afecioru.gamedev.section02.sample01.config.PlayerConfig
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.{Circle, MathUtils, Vector2, Vector3}

final case class Player(config: PlayerConfig, screenInfo: GameScreenInfo) extends DirectionalControls {
  import Player._
  import ShapeRendererExtensions._

  var debugEnabled = true
  val circle: Circle = new Circle(Vector2.Zero, BOUNDS_RADIUS)

  override val speed: Vector3 = config.controls.speed

  override protected val KEY_CODE_UP: Int = config.controls.keyCodeUp
  override protected val KEY_CODE_DOWN: Int = config.controls.keyCodeDown
  override protected val KEY_CODE_LEFT: Int = config.controls.keyCodeLeft
  override protected val KEY_CODE_RIGHT: Int = config.controls.keyCodeRight

  def pos: Vector2 = new Vector2(circle.x, circle.y)
  def pos_=(position: Vector2): Unit = {
    circle.setPosition(position)
  }

  def draw(renderer: ShapeRenderer): Unit = {
    update()

    if (debugEnabled)
      debugDraw(renderer)
  }

  private def update(): Unit = {
    val x = MathUtils.clamp(pos.x + movement.x, BOUNDS_RADIUS, screenInfo.WORLD_WIDTH - BOUNDS_RADIUS)
    val y = MathUtils.clamp(pos.y + movement.y, BOUNDS_RADIUS, screenInfo.WORLD_HEIGHT- BOUNDS_RADIUS)
    pos = new Vector2(x, y)
  }

  private def debugDraw(renderer: ShapeRenderer): Unit = {
    renderer.withShape(ShapeType.Filled) {
      renderer.withColor(Color.GOLD) {
        renderer.circle(circle.x, circle.y, circle.radius, 30)
      }
    }
  }
}

object Player {
  private val BOUNDS_RADIUS = 0.4f
  private val BOUNDS_SIZE = 2 * BOUNDS_RADIUS
}


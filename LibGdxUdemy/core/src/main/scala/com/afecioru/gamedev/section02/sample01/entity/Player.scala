package com.afecioru.gamedev.section02.sample01.entity

import com.afecioru.gamedev.section02.GdxUtils
import com.afecioru.gamedev.section02.common.extensions.ShapeRendererExtensions
import com.afecioru.gamedev.section02.common.{DirectionalControls, GameScreenInfo}
import com.afecioru.gamedev.section02.sample01.config.PlayerConfig
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.{Circle, MathUtils, Vector2}

final case class Player(config: PlayerConfig)
                       (implicit screenInfo: GameScreenInfo)
  extends Entity with DirectionalControls {

  import GdxUtils._
  import Player._
  import ShapeRendererExtensions._

  override val ui: GameScreenInfo = screenInfo

  override val speed: Vector2 = new Vector2(config.controls.speed.x, config.controls.speed.y)

  override val initialSpeed: Vector2 = Vector2.Zero
  override val initialPos: Vector2 = new Vector2(ui.WORLD_WIDTH / 2, 1.0f)

  override def boundsRadius: Float = BOUNDS_RADIUS

  override protected val KEY_CODE_UP: Int = config.controls.keyCodeUp
  override protected val KEY_CODE_DOWN: Int = config.controls.keyCodeDown
  override protected val KEY_CODE_LEFT: Int = config.controls.keyCodeLeft
  override protected val KEY_CODE_RIGHT: Int = config.controls.keyCodeRight

  protected val circle : Circle = new Circle(Vector2.Zero, boundsRadius)

  override def refresh(): Unit = {
    super.refresh()
    circle.setPosition(pos)
  }

  override def update(): Unit = {
    val deltaX = movement.x * speed.x * DELTA_TIME
    val deltaY = movement.y * speed.y * DELTA_TIME

    val x = MathUtils.clamp(pos.x + deltaX, BOUNDS_RADIUS, ui.WORLD_WIDTH - BOUNDS_RADIUS)
    val y = MathUtils.clamp(pos.y + deltaY, BOUNDS_RADIUS, ui.WORLD_HEIGHT- BOUNDS_RADIUS)

    pos = new Vector2(x, y)
  }

  override def draw(): Unit = {
    ui.renderer.withShape(ShapeType.Filled) {
      ui.renderer.withColor(Color.DARK_GRAY) {
        ui.renderer.circle(circle.x, circle.y, circle.radius, 30)
      }
    }
  }

  // -------------[ INITIALIZATION ]-------------
  {
    init()
    ui.inputMultiplexer.addProcessor(directionalInputHandler)
  }
}

object Player {
  private val BOUNDS_RADIUS = 0.4f
}


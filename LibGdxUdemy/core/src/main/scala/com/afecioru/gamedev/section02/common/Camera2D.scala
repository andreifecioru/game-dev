package com.afecioru.gamedev.section02.common

import com.afecioru.gamedev.section02.sample01.config.CameraConfig
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.{MathUtils, Vector3}
import com.badlogic.gdx.{InputAdapter, InputProcessor}
import com.afecioru.gamedev.section02.GdxUtils

case class Camera2D(initialPosition: Vector3,
                    config: CameraConfig)
                   (implicit val ui: GameScreenInfo)
  extends OrthographicCamera with DirectionalControls {

  import GdxUtils._

  override protected val KEY_CODE_UP: Int = config.controls.keyCodeUp
  override protected val KEY_CODE_DOWN: Int = config.controls.keyCodeDown
  override protected val KEY_CODE_LEFT: Int = config.controls.keyCodeLeft
  override protected val KEY_CODE_RIGHT: Int = config.controls.keyCodeRight
  override protected val KEY_CODE_ZOOM_IN: Int = config.controls.keyCodeZoomIn
  override protected val KEY_CODE_ZOOM_OUT: Int = config.controls.keyCodeZoomOut
  private val KEY_CODE_SHOW_DEBUG_INFO: Int = config.controls.keyCodeDebugInfo
  private def KEY_CODE_RESET: Int = config.controls.keyCodeReset

  private var _speed: Vector3 = Vector3.Zero
  def speed: Vector3 = _speed
  def speed_=(value: Vector3): Unit = _speed = value


  override def update(): Unit = {
    val deltaX = movement.x * speed.x * DELTA_TIME
    val deltaY = movement.y * speed.y * DELTA_TIME
    val deltaZ = movement.z * speed.z * DELTA_TIME

    zoom = MathUtils.clamp(zoom + deltaZ, config.maxZoomIn, config.maxZoomOut)
    position.set(position.x + deltaX, position.y + deltaY, zoom)

    super.update()
  }

  private def showDebugInfo(): Unit = {
    val message = s"x: ${position.x}, y: ${position.y} | zoom: $zoom"
    logger.debug(message)
  }

  private def reset(): Unit = {
    position.set(initialPosition)
    zoom = initialPosition.z
  }

  private val debugControlsInputHandler: InputProcessor = new InputAdapter {
    override def keyDown(keycode: Int): Boolean = {
      keycode match {
        case _ if keycode == KEY_CODE_SHOW_DEBUG_INFO => showDebugInfo(); true
        case _ if keycode == KEY_CODE_RESET => reset(); true
        case _ => false
      }
    }
  }

  // -----------------[ INITIALIZATION ]-----------------
  {
    reset()
    speed = config.controls.speed
    ui.inputMultiplexer.addProcessor(directionalInputHandler)
    ui.inputMultiplexer.addProcessor(debugControlsInputHandler)
  }
}

package com.afecioru.gamedev.section02.common

import com.afecioru.gamedev.section02.sample01.config.CameraConfig
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.{MathUtils, Vector2, Vector3}

case class Camera2D(initialPosition: Vector3, config: CameraConfig)
  extends OrthographicCamera with DirectionalControls {

  override protected val KEY_CODE_UP: Int = config.controls.keyCodeUp
  override protected val KEY_CODE_DOWN: Int = config.controls.keyCodeDown
  override protected val KEY_CODE_LEFT: Int = config.controls.keyCodeLeft
  override protected val KEY_CODE_RIGHT: Int = config.controls.keyCodeRight
  override protected val KEY_CODE_ZOOM_IN: Int = config.controls.keyCodeZoomIn
  override protected val KEY_CODE_ZOOM_OUT: Int = config.controls.keyCodeZoomOut
  override protected val KEY_CODE_SHOW_DEBUG_INFO: Int = config.controls.keyCodeDebugInfo
  override protected def KEY_CODE_RESET: Int = config.controls.keyCodeReset

  { // initialization
    reset()
    speed = config.controls.speed
  }

  override def update(): Unit = {
    zoom = MathUtils.clamp(zoom + movement.z, config.maxZoomIn, config.maxZoomOut)
    position.set(position.x + movement.x, position.y + movement.y, zoom)

    super.update()
  }

  override protected def showDebugInfo(): Unit = {
    val message = s"x: ${position.x}, y: ${position.y} | zoom: $zoom"
    logger.debug(message)
  }

  override protected def reset(): Unit = {
    position.set(initialPosition)
    zoom = initialPosition.z
  }
}

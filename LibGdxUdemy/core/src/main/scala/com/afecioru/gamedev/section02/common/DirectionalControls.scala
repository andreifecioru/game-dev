package com.afecioru.gamedev.section02.common

import com.afecioru.gamedev.section02.{GdxUtils, GenericUtils, LoggingSupport}
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.{InputAdapter, InputProcessor}

trait DirectionalControls extends LoggingSupport {
  import GdxUtils._

  protected def KEY_CODE_UP: Int = Int.MinValue
  protected def KEY_CODE_DOWN: Int = Int.MinValue
  protected def KEY_CODE_LEFT: Int = Int.MinValue
  protected def KEY_CODE_RIGHT: Int = Int.MinValue
  protected def KEY_CODE_ZOOM_IN: Int = Int.MinValue
  protected def KEY_CODE_ZOOM_OUT: Int = Int.MinValue
  protected def KEY_CODE_SHOW_DEBUG_INFO: Int = Int.MinValue
  protected def KEY_CODE_RESET: Int = Int.MinValue

  private var isUpPressed = false
  private var isDownPressed = false
  private var isLeftPressed = false
  private var isRightPressed = false
  private var isZoomInPressed = false
  private var isZoomOutPressed = false

  protected def showDebugInfo(): Unit = ()
  protected def reset(): Unit = ()

  private var _speed: Vector3 = Vector3.Zero
  def speed: Vector3 = _speed
  def speed_=(value: Vector3): Unit = _speed = value

  def movement: Vector3 = {
    import GenericUtils.implicits._
    val deltaX = ((isRightPressed:Int) - (isLeftPressed:Int)) * speed.x * DELTA_TIME
    val deltaY = ((isUpPressed:Int) - (isDownPressed:Int)) * speed.y * DELTA_TIME
    val deltaZ = ((isZoomOutPressed:Int) - (isZoomInPressed: Int)) * speed.z * DELTA_TIME

    new Vector3(deltaX, deltaY, deltaZ)
  }

  val inputHandler: InputProcessor = new InputAdapter {
    override def keyDown(keycode: Int): Boolean = {
      keycode match {
        case _ if keycode == KEY_CODE_UP => isUpPressed = true; true
        case _ if keycode == KEY_CODE_DOWN => isDownPressed = true; true
        case _ if keycode == KEY_CODE_LEFT => isLeftPressed = true; true
        case _ if keycode == KEY_CODE_RIGHT => isRightPressed = true; true
        case _ if keycode == KEY_CODE_ZOOM_IN => isZoomInPressed = true; true
        case _ if keycode == KEY_CODE_ZOOM_OUT => isZoomOutPressed = true; true
        case _ if keycode == KEY_CODE_SHOW_DEBUG_INFO => showDebugInfo(); true
        case _ if keycode == KEY_CODE_RESET => reset(); true
        case _ => false
      }
    }

    override def keyUp(keycode: Int): Boolean = {
      keycode match {
        case _ if keycode == KEY_CODE_UP => isUpPressed = false; true
        case _ if keycode == KEY_CODE_DOWN => isDownPressed = false; true
        case _ if keycode == KEY_CODE_LEFT => isLeftPressed = false; true
        case _ if keycode == KEY_CODE_RIGHT => isRightPressed = false; true
        case _ if keycode == KEY_CODE_ZOOM_IN => isZoomInPressed = false; true
        case _ if keycode == KEY_CODE_ZOOM_OUT => isZoomOutPressed = false; true
        case _ => false
      }
    }
  }
}

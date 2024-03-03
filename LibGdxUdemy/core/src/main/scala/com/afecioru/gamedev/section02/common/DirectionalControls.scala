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

  private var isUpPressed = false
  private var isDownPressed = false
  private var isLeftPressed = false
  private var isRightPressed = false
  private var isZoomInPressed = false
  private var isZoomOutPressed = false

  def movement: Vector3 = {
    import GenericUtils.implicits._
    val deltaX = ((isRightPressed:Int) - (isLeftPressed:Int)).toFloat
    val deltaY = ((isUpPressed:Int) - (isDownPressed:Int)).toFloat
    val deltaZ = ((isZoomOutPressed:Int) - (isZoomInPressed: Int)).toFloat

    new Vector3(deltaX, deltaY, deltaZ)
  }

  val directionalInputHandler: InputProcessor = new InputAdapter {
    override def keyDown(keycode: Int): Boolean = {
      keycode match {
        case _ if keycode == KEY_CODE_UP => isUpPressed = true; true
        case _ if keycode == KEY_CODE_DOWN => isDownPressed = true; true
        case _ if keycode == KEY_CODE_LEFT => isLeftPressed = true; true
        case _ if keycode == KEY_CODE_RIGHT => isRightPressed = true; true
        case _ if keycode == KEY_CODE_ZOOM_IN => isZoomInPressed = true; true
        case _ if keycode == KEY_CODE_ZOOM_OUT => isZoomOutPressed = true; true
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

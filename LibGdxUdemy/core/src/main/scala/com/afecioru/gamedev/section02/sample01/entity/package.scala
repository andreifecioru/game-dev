package com.afecioru.gamedev.section02.sample01

import com.afecioru.gamedev.section02.LoggingSupport
import com.afecioru.gamedev.section02.common.GameContext
import com.badlogic.gdx.math.{Circle, Vector2, Vector3}
import com.afecioru.gamedev.section02.common.extensions.ShapeRendererExtensions
import com.badlogic.gdx.{InputAdapter, InputProcessor}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType

package object entity {

  trait Entity extends LoggingSupport {
    import ShapeRendererExtensions._

    protected var debugEnabled = true

    protected def KEY_CODE_SHOW_DEBUG_INFO: Int = Int.MinValue
    protected def KEY_CODE_RESET: Int = Int.MinValue

    def boundsRadius: Float
    def boundsSize: Float = 2 * boundsRadius

    def ctx: GameContext

    val bounds: Circle = new Circle(Vector2.Zero, boundsRadius)

    private var _speed: Vector2 = Vector2.Zero
    def speed: Vector2 = _speed
    def speed_=(value: Vector2): Unit = _speed = value
    protected def initialSpeed: Vector2

    def pos: Vector2 = new Vector2(bounds.x, bounds.y)
    def pos_=(position: Vector2): Unit = {
      bounds.setPosition(position)
    }

    protected def initialPos: Vector2

    protected def init(): Unit = {
      ctx.inputMultiplexer.addProcessor(debugControlsInputHandler)
      reset()
    }

    def reset(): Unit = {
      pos = initialPos
      speed = initialSpeed
    }

    def refresh(): Unit = {
      update()
      draw()
      if (debugEnabled) drawDebug()
    }

    def update(): Unit = ()
    def draw(): Unit = ()
    def dispose(): Unit = ()

    private def drawDebug(): Unit = {
      ctx.renderer.withShape(ShapeType.Line) {
        ctx.renderer.withColor(Color.WHITE) {
          ctx.renderer.circle(bounds.x, bounds.y, bounds.radius, 30)
        }
      }
    }

    private val debugControlsInputHandler: InputProcessor = new InputAdapter {
      override def keyDown(keycode: Int): Boolean = {
        keycode match {
          case _ if keycode == KEY_CODE_SHOW_DEBUG_INFO => debugEnabled = !debugEnabled; true
          case _ if keycode == KEY_CODE_RESET => reset(); true
          case _ => false
        }
      }
    }


  }

}

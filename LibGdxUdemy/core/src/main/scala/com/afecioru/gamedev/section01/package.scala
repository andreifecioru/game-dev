package com.afecioru.gamedev

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.{Color, GL20, OrthographicCamera}
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.{FitViewport, Viewport}
import com.badlogic.gdx.{ApplicationAdapter, Gdx, InputMultiplexer, InputProcessor}

package object section01 {
  trait LoggingSupport {
    def logLevel: Int = Logger.DEBUG

    lazy val logger = new Logger(getClass.getSimpleName, logLevel)
  }

  trait InputAdapter extends InputProcessor {
    override def keyDown(keycode: Int): Boolean = true
    override def keyUp(keycode: Int): Boolean = true
    override def keyTyped(character: Char): Boolean = true
    override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = true
    override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = true
    override def touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = true
    override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = true
    override def mouseMoved(screenX: Int, screenY: Int): Boolean = true
    override def scrolled(amountX: Float, amountY: Float): Boolean = true
  }

  object GdxUtils {
    def clearScreen(): Unit = {
      clearScreen(Color.BLACK)
    }

    def clearScreen(color: Color): Unit = {
      Gdx.gl.glClearColor(color.r, color.g, color.b, color.a)
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    object implicits {
      implicit class SpriteBatchExtensions(val batch: SpriteBatch) {
        def render(camera: OrthographicCamera)
                  (block: => Unit): Unit = {
          batch.setProjectionMatrix(camera.combined)
          batch.begin()
          block
          batch.end()
        }

        def withColor(color: Color)(block: => Unit): Unit = {
          val oldColor = new Color
          oldColor.set(batch.getColor)

          batch.setColor(color)
          block
          batch.setColor(oldColor)
        }
      }

      implicit class ShapeRendererExtensions(val renderer: ShapeRenderer) {
        import ShapeRenderer.ShapeType

        def render(camera: OrthographicCamera)
                  (block: => Unit): Unit = {
          renderer.setProjectionMatrix(camera.combined)
          block
        }

        def withShape(shapeType: ShapeType)
                     (block: => Unit): Unit = {
          renderer.begin(shapeType)
          block
          renderer.end()
        }

        def withColor(color: Color)
                     (block: => Unit): Unit = {
          val oldColor = new Color
          oldColor.set(renderer.getColor)

          renderer.setColor(color)
          block
          renderer.setColor(oldColor)
        }
      }
    }
  }

  abstract class BaseGameEngine extends ApplicationAdapter {
    import GdxUtils.implicits._


    protected def WINDOW_WIDTH: Int
    protected def WINDOW_HEIGHT: Int
    protected def WORLD_UNIT_IN_PIXELS: Int = 1

    protected lazy val WORLD_WIDTH: Float = WINDOW_WIDTH / WORLD_UNIT_IN_PIXELS.toFloat
    protected lazy val WORLD_HEIGHT: Float = WINDOW_HEIGHT / WORLD_UNIT_IN_PIXELS.toFloat

    protected var camera: OrthographicCamera = _
    protected var viewport: Viewport = _
    protected var batch: SpriteBatch = _
    protected var renderer: ShapeRenderer = _

    protected val inputMultiplexer = new InputMultiplexer

    protected def drawBatch(): Unit = ()
    protected def drawShape(): Unit = ()

    override def create(): Unit = {
      Gdx.graphics.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT)
      Gdx.input.setInputProcessor(inputMultiplexer)

      camera = new OrthographicCamera
      batch = new SpriteBatch()
      renderer = new ShapeRenderer()
      viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    }

    override def render(): Unit = {
      GdxUtils.clearScreen(Color.BLACK)
      batch.render(camera) {
        drawBatch()
      }
      renderer.render(camera) {
        drawShape()
      }
    }

    override def resize(width: Int, height: Int): Unit = {
      viewport.update(width, height, true) // center the camera
    }

    override def dispose(): Unit = {
      batch.dispose()
      renderer.dispose()
    }
  }
}

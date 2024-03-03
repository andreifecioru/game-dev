package com.afecioru.gamedev.section01.sample07

import com.afecioru.gamedev.section01.{BaseGameEngine, GdxUtils, LoggingSupport}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.{Application, Gdx, Input, InputAdapter}

final case class GameEngine() extends BaseGameEngine with LoggingSupport {
  import GdxUtils.implicits._
  import ShapeRenderer.ShapeType

  override val WINDOW_WIDTH = 1080
  override val WINDOW_HEIGHT = 720
  override val WORLD_UNIT_IN_PIXELS: Int = 20

  private var drawGrid = false
  private var drawCircles = false
  private var drawRectangles = false
  private var drawPoints = false

  override def create(): Unit = {
    super.create()

    Gdx.app.setLogLevel(Application.LOG_DEBUG)

    inputMultiplexer.addProcessor(inputProcessor)

    logger.debug("Creation completed...")
  }

  override def dispose(): Unit = {
    super.dispose()
  }

  override def resize(width: Int, height: Int): Unit = {
    viewport.update(width, height) // don't center the camera
  }


  override def drawShape(): Unit = {
    if (drawGrid) displayGrid()
    if (drawCircles) displayCircles()
    if (drawRectangles) displayRectangles()
    if (drawPoints) displayPoints()
  }

  private val inputProcessor = new InputAdapter {
    override def keyDown(keycode: Int): Boolean = {
      keycode match {
        case Input.Keys.G => drawGrid = !drawGrid
        case Input.Keys.C => drawCircles = !drawCircles
        case Input.Keys.R => drawRectangles = !drawRectangles
        case Input.Keys.P => drawPoints = !drawPoints
      }

      true
    }
  }

  // -----------------------------

  private def displayGrid(): Unit = {
    renderer.withShape(ShapeType.Line) {
      renderer.withColor(Color.WHITE) {
        (-WORLD_WIDTH.toInt to WORLD_WIDTH.toInt).foreach { x =>
          if (x == 0) {
            renderer.withColor(Color.RED) {
              renderer.line(x.toFloat, -WORLD_HEIGHT, x.toFloat, WORLD_HEIGHT)
            }
          } else {
            renderer.line(x.toFloat, -WORLD_HEIGHT, x.toFloat, WORLD_HEIGHT)
          }
        }

        (-WORLD_HEIGHT.toInt to WORLD_HEIGHT.toInt).foreach { y =>
          if (y == 0) {
            renderer.withColor(Color.RED) {
              renderer.line(-WORLD_WIDTH, y.toFloat, WORLD_WIDTH, y.toFloat)
            }
          } else {
            renderer.line(-WORLD_WIDTH, y.toFloat, WORLD_WIDTH, y.toFloat)
          }
        }
      }
    }
  }

  private def displayCircles(): Unit = {
    renderer.withShape(ShapeType.Filled) {
      renderer.withColor(Color.CYAN) {
        renderer.circle(0f, 0f, 2f, 30) // last param is the no of segments used to draw the circle
      }

      renderer.withColor(Color.YELLOW) {
        renderer.circle(-5f, -5f, 3f, 30) // last param is the no of segments used to draw the circle
      }
    }
  }

  private def displayRectangles(): Unit = {
    renderer.withShape(ShapeType.Filled) {
      renderer.withColor(Color.RED) {
        renderer.rect(3f, 3f, 4f, 2f)
      }
    }
  }

  private def displayPoints(): Unit = {
    renderer.withShape(ShapeType.Filled) {
      renderer.withColor(Color.MAGENTA) {
        renderer.point(-5, 0, 0)
      }
    }

    renderer.withShape(ShapeType.Line) {
      renderer.withColor(Color.GOLDENROD) {
        renderer.x(10, -8, 0.5f)
      }
    }
  }
}

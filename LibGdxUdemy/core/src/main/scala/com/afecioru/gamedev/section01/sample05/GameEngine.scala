package com.afecioru.gamedev.section01.sample05

import com.afecioru.gamedev.section01.{BaseGameEngine, LoggingSupport}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.viewport._
import com.badlogic.gdx.{Application, Gdx, Input, InputAdapter}

import scala.collection.mutable.{Buffer => MBuffer}

final case class GameEngine() extends BaseGameEngine
  with LoggingSupport {

  override val WINDOW_WIDTH = 1080
  override val WINDOW_HEIGHT = 720
  override val WORLD_UNIT_IN_PIXELS: Int = 1
  override lazy val WORLD_WIDTH = 800.0f
  override lazy val WORLD_HEIGHT = 600.0f

  private var texture: Texture = _
  private var font: BitmapFont = _

  private val viewports = MBuffer.empty[Viewport]
  private var currentViewportIdx = 0

  override def create(): Unit = {
    super.create()

    Gdx.app.setLogLevel(Application.LOG_DEBUG)

    texture = new Texture(Gdx.files.internal("assets/section01/raw/level-bg-small.png"))
    font = new BitmapFont(Gdx.files.internal("assets/section01/fonts/oswald-32.fnt"))

    inputMultiplexer.addProcessor(inputHandler)

    viewports += new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    viewports += new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    viewports += new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    viewports += new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    viewports += new ScreenViewport(camera)

    viewport = viewports(currentViewportIdx)

    logger.debug("Creation completed...")
  }

  override def dispose(): Unit = {
    super.dispose()
    texture.dispose()
    font.dispose()
  }

  private val inputHandler = new InputAdapter() {
    override def keyDown(keycode: Int): Boolean = {
      keycode match {
        case Input.Keys.W =>
          viewport = nextViewport
          viewport.update(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
        case _ =>
      }

      camera.update()
      true
    }

    override def keyTyped(ch: Char): Boolean = {
      logger.debug(s"Key typed: $ch")
      true
    }

  }

  override def drawBatch(): Unit = {
    batch.draw(texture,
      0, 0, // x, y
      WORLD_WIDTH, WORLD_HEIGHT // width, height
    )

    val viewportName = viewport.getClass.getSimpleName
    font.draw(batch, viewportName, 10, 100)
  }

  private def nextViewport: Viewport = {
    currentViewportIdx = (currentViewportIdx + 1) % viewports.size
    viewports(currentViewportIdx)
  }
}

object GameEngine {
  private val CAMERA_SPEED = 120.0f // world units
  private val CAMERA_ZOOM_SPEED = 2.0f // world units
  private lazy val DELTA_TIME = Gdx.graphics.getDeltaTime // time between 2 consecutive render calls
}

package com.afecioru.gamedev.section01.sample04

import com.afecioru.gamedev.section01.{BaseGameEngine, LoggingSupport}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.{Application, Gdx, Input, InputAdapter}

final case class GameEngine() extends BaseGameEngine
  with LoggingSupport {
  import GameEngine._

  override val WINDOW_WIDTH = 1080
  override val WINDOW_HEIGHT = 720
  override val WORLD_UNIT_IN_PIXELS: Int = 1

  private var texture: Texture = _

  override def create(): Unit = {
    super.create()

    Gdx.app.setLogLevel(Application.LOG_DEBUG)

    // manually center the camera (it's easier to do that via viewport in resize())
    // camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0)
    // camera.update()

    texture = new Texture(Gdx.files.internal("assets/section01/raw/level-bg.png"))

    inputMultiplexer.addProcessor(inputHandler)

    logger.debug("Creation completed...")
  }

  override def dispose(): Unit = {
    super.dispose()
    texture.dispose()
  }

  private val inputHandler = new InputAdapter() {
    override def keyDown(keycode: Int): Boolean = {
      keycode match {
        case Input.Keys.PAGE_UP =>
          camera.zoom -= CAMERA_ZOOM_SPEED * DELTA_TIME
        case Input.Keys.PAGE_DOWN =>
          camera.zoom += CAMERA_ZOOM_SPEED * DELTA_TIME

        case Input.Keys.LEFT =>
          camera.position.x -= CAMERA_SPEED * DELTA_TIME
        case Input.Keys.RIGHT =>
          camera.position.x += CAMERA_SPEED * DELTA_TIME
        case Input.Keys.UP =>
          camera.position.y += CAMERA_SPEED * DELTA_TIME
        case Input.Keys.DOWN =>
          camera.position.y -= CAMERA_SPEED * DELTA_TIME

        case Input.Keys.ENTER =>
          logger.debug(s"camera pos: ${camera.position}")
          logger.debug(s"camera zoom: ${camera.zoom}")

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
  }
}

object GameEngine {
  private val CAMERA_SPEED = 120.0f // world units
  private val CAMERA_ZOOM_SPEED = 2.0f // world units
  private lazy val DELTA_TIME = Gdx.graphics.getDeltaTime // time between 2 consecutive render calls
}

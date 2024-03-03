package com.afecioru.gamedev.section01.sample06

import com.afecioru.gamedev.section01.{BaseGameEngine, GdxUtils, LoggingSupport}
import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.{Application, Gdx}

final case class GameEngine() extends BaseGameEngine with LoggingSupport {
  import GameEngine._
  import GdxUtils.implicits._


  override val WINDOW_WIDTH = 1080
  override val WINDOW_HEIGHT = 720
  override val WORLD_UNIT_IN_PIXELS: Int = 1

  private var texture: Texture = _

  override def create(): Unit = {
    super.create()

    Gdx.app.setLogLevel(Application.LOG_DEBUG)

    texture = new Texture(Gdx.files.internal("assets/section01/raw/character.png"))

    logger.debug("Creation completed...")
  }

  override def dispose(): Unit = {
    super.dispose()
    texture.dispose()
  }

  override def drawBatch(): Unit = {
    batch.draw(texture,
      0, 0,                                   // x, y
      TEXTURE_WIDTH / 2, TEXTURE_HEIGHT / 2,  // originX, originY (rotation anchor point)
      TEXTURE_WIDTH, TEXTURE_HEIGHT,          // width, height
      1.0f, 1.0f,                             // scaleX, scaleY
      0.0f,                                   // rotation
      0, 0,                                   // sourceX, sourceY
      texture.getWidth, texture.getHeight,    // sourceWidth, sourceHeight
      false, false                            // flipX, flipY
    )

    batch.withColor(Color.RED) {
      batch.draw(texture,
        200, 200,                               // x, y
        TEXTURE_WIDTH / 2, TEXTURE_HEIGHT / 2,  // originX, originY (rotation anchor point)
        TEXTURE_WIDTH, TEXTURE_HEIGHT,          // width, height
        2.0f, 2.0f,                             // scaleX, scaleY
        0.0f,                                   // rotation
        0, 0,                                   // sourceX, sourceY
        texture.getWidth, texture.getHeight,    // sourceWidth, sourceHeight
        false, false                            // flipX, flipY
      )
    }

    batch.draw(texture,
      500, 0,                                   // x, y
      TEXTURE_WIDTH / 2, TEXTURE_HEIGHT / 2,  // originX, originY (rotation anchor point)
      TEXTURE_WIDTH, TEXTURE_HEIGHT,          // width, height
      1.0f, 1.0f,                             // scaleX, scaleY
      45.0f,                                   // rotation
      0, 0,                                   // sourceX, sourceY
      texture.getWidth, texture.getHeight,    // sourceWidth, sourceHeight
      false, false                            // flipX, flipY
    )
  }
}

object GameEngine {
  private val CAMERA_SPEED = 120.0f // world units
  private val CAMERA_ZOOM_SPEED = 2.0f // world units
  private lazy val DELTA_TIME = Gdx.graphics.getDeltaTime // time between 2 consecutive render calls
  private val TEXTURE_WIDTH = 100.0f
  private val TEXTURE_HEIGHT = 100.0f
}

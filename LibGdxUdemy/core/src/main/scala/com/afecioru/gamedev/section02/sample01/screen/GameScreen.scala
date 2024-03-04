package com.afecioru.gamedev.section02.sample01.screen

import com.afecioru.gamedev.section02.sample01.config.GameConfig
import com.afecioru.gamedev.section02.sample01.entity.{Obstacle, Player}
import com.afecioru.gamedev.section02.LoggingSupport
import com.afecioru.gamedev.section02.common.{BaseGameScreen, GameContext}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2

final case class GameScreen(gameConfig: GameConfig)
  extends BaseGameScreen(gameConfig) with LoggingSupport {

  private var scene: Scene2D = _

  private implicit val ctx: GameContext = this

  override def show(): Unit = {
    super.show()

    scene = Scene2D()
    scene.init()

    logger.debug(s"World width: $WORLD_WIDTH")
    logger.debug(s"World height: $WORLD_HEIGHT")
  }

  override protected def drawBatch(): Unit = {
    // batch.draw(texture, 0, 0)
  }

  override protected def drawShape(): Unit = {
    scene.refresh()
  }

  override def dispose(): Unit = {
    super.dispose()
    scene.dispose()
  }
}

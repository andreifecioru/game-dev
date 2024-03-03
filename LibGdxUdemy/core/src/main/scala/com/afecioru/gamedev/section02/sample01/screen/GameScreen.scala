package com.afecioru.gamedev.section02.sample01.screen

import com.afecioru.gamedev.section02.sample01.config.GameConfig
import com.afecioru.gamedev.section02.sample01.entity.Player
import com.afecioru.gamedev.section02.LoggingSupport
import com.afecioru.gamedev.section02.common.BaseGameScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2

final case class GameScreen(config: GameConfig) extends BaseGameScreen(config) with LoggingSupport {

  private var texture: Texture = _
  private var player: Player = _

  override def show(): Unit = {
    super.show()

    texture = new Texture(Gdx.files.internal("assets/section02/gameplay/player.png"))
    player = new Player(config.player, this)
    player.pos = new Vector2(WORLD_WIDTH / 2, 1)

    inputMultiplexer.addProcessor(player.inputHandler)

    logger.debug(s"World width: $WORLD_WIDTH")
    logger.debug(s"World height: $WORLD_HEIGHT")
  }

  override protected def drawBatch(): Unit = {
    // batch.draw(texture, 0, 0)
  }

  override protected def drawShape(): Unit = {
    player.draw(renderer)
  }

  override def dispose(): Unit = {
    super.dispose()
    texture.dispose()
  }
}

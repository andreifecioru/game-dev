package com.afecioru.gamedev.section02.sample01

import com.afecioru.gamedev.section02.LoggingSupport
import com.afecioru.gamedev.section02.sample01.config.GameConfig
import com.afecioru.gamedev.section02.sample01.screen.GameScreen
import com.badlogic.gdx.{Application, Game, Gdx}
import com.typesafe.config.Config

final case class GameEngine(config: Config) extends Game with LoggingSupport {

  override def create(): Unit = {
    Gdx.app.setLogLevel(Application.LOG_DEBUG)
    setScreen(GameScreen(GameConfig(config)))
  }

}

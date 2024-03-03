package com.afecioru.gamedev.section02.sample01

import com.badlogic.gdx.backends.lwjgl3.{Lwjgl3Application => GdxApp, Lwjgl3ApplicationConfiguration => GdxAppConfig}
import com.badlogic.gdx.{Application, Gdx}
import com.typesafe.config.ConfigFactory


object Launcher extends App {
  private val appConfig = new GdxAppConfig
  appConfig.setForegroundFPS(60)
  appConfig.setTitle("Sample 001 - Obstacle Game")
  appConfig.setResizable(false)

  private val gameConfig = ConfigFactory.load("game")
  new GdxApp(GameEngine(gameConfig), appConfig)
}

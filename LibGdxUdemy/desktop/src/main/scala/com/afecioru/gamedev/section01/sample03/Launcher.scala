package com.afecioru.gamedev.section01.sample03

import com.badlogic.gdx.backends.lwjgl3.{Lwjgl3Application => GdxApp, Lwjgl3ApplicationConfiguration => GdxAppConfig}
import com.badlogic.gdx.{Application, Gdx}


object Launcher extends App {
  private val config = new GdxAppConfig
  config.setForegroundFPS(60)
  config.setTitle("Sample 003 - Handling input (event handling)")
  new GdxApp(GameEngine(), config)

  Gdx.app.setLogLevel(Application.LOG_DEBUG)
}

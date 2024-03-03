package com.afecioru.gamedev.section01.sample01

import com.badlogic.gdx.backends.lwjgl3.{Lwjgl3Application => GdxApp, Lwjgl3ApplicationConfiguration => GdxAppConfig}


object Launcher extends App {
  private val config = new GdxAppConfig
  config.setForegroundFPS(60)
  config.setTitle("Sample 001 - Starter app")
  new GdxApp(GameEngine(), config)
}

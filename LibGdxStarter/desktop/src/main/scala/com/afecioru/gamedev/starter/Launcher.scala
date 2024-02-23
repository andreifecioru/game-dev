package com.afecioru.gamedev.starter

import com.badlogic.gdx.backends.lwjgl3.{
  Lwjgl3Application => GdxApp,
  Lwjgl3ApplicationConfiguration => GdxAppConfig
}


object Launcher extends App {
  private val config = new GdxAppConfig
  config.setForegroundFPS(60)
  config.setTitle("Starter Kit")
  new GdxApp(GameEngine(), config)
}

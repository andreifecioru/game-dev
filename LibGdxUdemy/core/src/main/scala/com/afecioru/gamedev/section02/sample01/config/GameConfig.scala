package com.afecioru.gamedev.section02.sample01.config

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector3
import com.typesafe.config.Config

case class GameConfig(private val config: Config) {
  lazy val window: WindowConfig = WindowConfig(config.getConfig("window"))
  lazy val camera: CameraConfig = CameraConfig(config.getConfig("camera"))
  lazy val player: PlayerConfig = PlayerConfig(config.getConfig("player"))
}

case class WindowConfig(private val config: Config) {
  lazy val width: Int = config.getInt("width")
  lazy val height: Int = config.getInt("height")
  lazy val worldUnitInPixels: Int = config.getInt("world_unit_px")
}

case class CameraConfig(private val config: Config) {
  lazy val maxZoomIn: Float = config.getDouble("maxZoomIn").toFloat
  lazy val maxZoomOut: Float = config.getDouble("maxZoomOut").toFloat
  lazy val controls: DirectionalControlsConfig = DirectionalControlsConfig(config.getConfig("controls"))
}

case class PlayerConfig(private val config: Config) {
  lazy val controls: DirectionalControlsConfig = DirectionalControlsConfig(config.getConfig("controls"))
}

case class DirectionalControlsConfig(private val config: Config) {
  lazy val speed: Vector3 = {
    new Vector3(
      config.getDouble("speed.x").toFloat,
      config.getDouble("speed.y").toFloat,
      config.getDouble("speed.z").toFloat
    )
  }

  lazy val keyCodeUp: Int = Input.Keys.valueOf(config.getString("keyUp"))
  lazy val keyCodeDown: Int = Input.Keys.valueOf(config.getString("keyDown"))
  lazy val keyCodeLeft: Int = Input.Keys.valueOf(config.getString("keyLeft"))
  lazy val keyCodeRight: Int = Input.Keys.valueOf(config.getString("keyRight"))
  lazy val keyCodeZoomIn: Int = Input.Keys.valueOf(config.getString("keyZoomIn"))
  lazy val keyCodeZoomOut: Int = Input.Keys.valueOf(config.getString("keyZoomOut"))
  lazy val keyCodeDebugInfo: Int = Input.Keys.valueOf(config.getString("keyDebugInfo"))
  lazy val keyCodeReset: Int = Input.Keys.valueOf(config.getString("keyReset"))
}


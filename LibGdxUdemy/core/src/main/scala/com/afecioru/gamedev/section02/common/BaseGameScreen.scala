package com.afecioru.gamedev.section02.common

import com.afecioru.gamedev.section02.GdxUtils
import com.afecioru.gamedev.section02.common.debug.DebugHud
import com.afecioru.gamedev.section02.common.extensions._
import com.afecioru.gamedev.section02.sample01.config.GameConfig
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.{FitViewport, Viewport}
import com.badlogic.gdx.{Gdx, InputMultiplexer, ScreenAdapter}

trait GameScreenInfo {
  def WORLD_WIDTH: Float
  def WORLD_HEIGHT: Float

  def WORLD_CENTER_X: Float
  def WORLD_CENTER_Y: Float
}

abstract class BaseGameScreen(config: GameConfig) extends ScreenAdapter with GameScreenInfo {
  import ShapeRendererExtensions._
  import SpriteBatchExtensions._

  protected def WINDOW_WIDTH: Int = config.window.width
  protected def WINDOW_HEIGHT: Int = config.window.height
  protected def WORLD_UNIT_IN_PIXELS: Int = config.window.worldUnitInPixels

  override lazy val WORLD_WIDTH: Float = WINDOW_WIDTH / WORLD_UNIT_IN_PIXELS.toFloat
  override lazy val WORLD_HEIGHT: Float = WINDOW_HEIGHT / WORLD_UNIT_IN_PIXELS.toFloat

  override lazy val WORLD_CENTER_X: Float = WORLD_WIDTH / 2
  override lazy val WORLD_CENTER_Y: Float = WORLD_HEIGHT / 2

  protected var camera: Camera2D = _
  protected var viewport: Viewport = _
  protected var batch: SpriteBatch = _
  var renderer: ShapeRenderer = _

  val inputMultiplexer: InputMultiplexer = new InputMultiplexer
  private val debugHud = DebugHud(this)
  debugHud.isGridEnabled = true

  protected def drawBatch(): Unit = ()
  protected def drawShape(): Unit = ()

  override def show(): Unit = {
    Gdx.graphics.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT)
    Gdx.input.setInputProcessor(inputMultiplexer)

    camera = Camera2D(new Vector3(WORLD_CENTER_X, WORLD_CENTER_Y, 1.0f), config.camera)
    batch = new SpriteBatch
    renderer = new ShapeRenderer
    viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)

    inputMultiplexer.addProcessor(camera.inputHandler)
  }

  override def render(delta: Float): Unit = {
    GdxUtils.clearScreen()

    batch.render(camera) {
      drawBatch()
    }
    renderer.render(camera) {
      debugHud.show()
      drawShape()
    }

    camera.update()
  }

  override def resize(width: Int, height: Int): Unit = {
    viewport.update(width, height, true) // center the camera
  }

  override def dispose(): Unit = {
    batch.dispose()
    renderer.dispose()
  }

  override def hide(): Unit = {
    dispose()
  }
}

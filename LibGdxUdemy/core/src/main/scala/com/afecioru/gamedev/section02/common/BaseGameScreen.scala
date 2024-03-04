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

trait GameContext {
  def gameConfig: GameConfig

  def WINDOW_WIDTH: Int = gameConfig.window.width
  def WINDOW_HEIGHT: Int = gameConfig.window.height
  def WORLD_UNIT_IN_PIXELS: Int = gameConfig.window.worldUnitInPixels

  lazy val WORLD_WIDTH: Float = WINDOW_WIDTH / WORLD_UNIT_IN_PIXELS.toFloat
  lazy val WORLD_HEIGHT: Float = WINDOW_HEIGHT / WORLD_UNIT_IN_PIXELS.toFloat

  lazy val WORLD_CENTER_X: Float = WORLD_WIDTH / 2
  lazy val WORLD_CENTER_Y: Float = WORLD_HEIGHT / 2

  def renderer: ShapeRenderer
  def batch: SpriteBatch
  def inputMultiplexer: InputMultiplexer
}

abstract class BaseGameScreen(config: GameConfig) extends ScreenAdapter with GameContext {
  import ShapeRendererExtensions._
  import SpriteBatchExtensions._

  private implicit val ctx: GameContext = this

  protected var camera: Camera2D = _
  protected var viewport: Viewport = _
  var batch: SpriteBatch = _
  var renderer: ShapeRenderer = _

  val inputMultiplexer: InputMultiplexer = new InputMultiplexer
  private val debugHud = DebugHud(this)
  debugHud.isGridEnabled = true

  protected def drawBatch(): Unit = ()
  protected def drawShape(): Unit = ()

  override def show(): Unit = {
    Gdx.graphics.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT)
    Gdx.input.setInputProcessor(inputMultiplexer)

    camera = Camera2D(new Vector3(WORLD_CENTER_X, WORLD_CENTER_Y, 1.0f))
    batch = new SpriteBatch
    renderer = new ShapeRenderer
    viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
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

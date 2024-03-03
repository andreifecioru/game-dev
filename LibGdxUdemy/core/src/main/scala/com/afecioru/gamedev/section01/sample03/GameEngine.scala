package com.afecioru.gamedev.section01.sample03

import com.afecioru.gamedev.section01.{GdxUtils, LoggingSupport}
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.utils.viewport.{FitViewport, Viewport}
import com.badlogic.gdx.{ApplicationAdapter, Gdx, InputAdapter, InputMultiplexer}

import scala.collection.mutable.{Buffer => MBuffer}

final case class GameEngine() extends ApplicationAdapter
  with LoggingSupport {
  import GameEngine._

  private var camera: OrthographicCamera = _
  private var viewport: Viewport = _
  private var batch: SpriteBatch = _
  private var font: BitmapFont = _


  private val messages = MBuffer.empty[String]

  override def create(): Unit = {
    camera = new OrthographicCamera
    viewport = new FitViewport(WINDOW_WIDTH.toFloat, WINDOW_HEIGHT.toFloat, camera)
    batch = new SpriteBatch
    font = new BitmapFont(Gdx.files.internal("assets/section01/fonts/oswald-32.fnt"))

    Gdx.graphics.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT)
    multiplexer.addProcessor(inputHandler)

    Gdx.input.setInputProcessor(multiplexer)
  }

  override def resize(width: Int, height: Int): Unit = {
    viewport.update(width, height)
  }

  override def render(): Unit = {
    // clear screen
    GdxUtils.clearScreen()

    batch.setProjectionMatrix(camera.combined)
    batch.begin()

    draw()

    batch.end()
  }

  override def dispose(): Unit = {
    batch.dispose()
    font.dispose()
  }

  private val inputHandler = new InputAdapter() {
    override def keyDown(keycode: Int): Boolean = {
      addMessage(s"Key down: $keycode")
      true
    }

    override def keyUp(keycode: Int): Boolean = {
      addMessage(s"Key up: $keycode")
      true
    }

    override def keyTyped(ch: Char): Boolean = {
      addMessage(s"Key typed: $ch")
      true

    }

    override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
      addMessage(s"touchDown: x=$screenX | y=$screenY | pointer=$pointer | button=$button")
      true
    }

    override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
      addMessage(s"touchUp: x=$screenX | y=$screenY | pointer=$pointer | button=$button")
      true
    }

    override def touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
      addMessage(s"touchCancelled: x=$screenX | y=$screenY | pointer=$pointer | button=$button")
      true
    }

    override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
      addMessage(s"touchDragged: x=$screenX | y=$screenY | pointer=$pointer")
      true
    }

    override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
      addMessage(s"mouseMoved: x=$screenX | y=$screenY")
      true
    }

    override def scrolled(amountX: Float, amountY: Float): Boolean = {
      addMessage(s"scrolled: byX=$amountX | byY=$amountY")
      true
    }
  }

  // ---------------------------
  private def addMessage(message: String): Unit = {
    messages += message

    if (messages.size > MAX_MSG_COUNT) {
      messages.remove(0)
    }
  }

  private def draw(): Unit = {
    val textBoxX = 10 -(viewport.getWorldWidth/ 2)
    val textBoxY = viewport.getWorldHeight / 2
    messages.zipWithIndex.foreach {
      case (message, idx) =>
        font.draw(batch, message, textBoxX, textBoxY - font.getLineHeight * idx)
    }
  }
}

object GameEngine {
  private val MAX_MSG_COUNT = 10
  private val WINDOW_HEIGHT = 720
  private val WINDOW_WIDTH = 1080
  private val multiplexer = new InputMultiplexer
}

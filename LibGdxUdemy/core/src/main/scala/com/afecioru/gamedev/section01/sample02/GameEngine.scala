package com.afecioru.gamedev.section01.sample02

import com.afecioru.gamedev.section01.LoggingSupport
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.utils.viewport.{FitViewport, Viewport}
import com.badlogic.gdx.{ApplicationAdapter, Gdx, Input}


final case class GameEngine() extends ApplicationAdapter  with LoggingSupport {
  private var camera: OrthographicCamera = _
  private var viewport: Viewport = _
  private var batch: SpriteBatch = _
  private var font: BitmapFont = _

  override def create(): Unit = {
    camera = new OrthographicCamera
    viewport = new FitViewport(1080, 720, camera)
    batch = new SpriteBatch
    font = new BitmapFont(Gdx.files.internal("assets/section01/fonts/oswald-32.fnt"))
  }

  override def resize(width: Int, height: Int): Unit = {
    viewport.update(width, height)
  }

  override def render(): Unit = {
    // clear screen
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    batch.setProjectionMatrix(camera.combined)
    batch.begin()

    draw()

    batch.end()
  }

  override def dispose(): Unit = {
    batch.dispose()
    font.dispose()
  }

  // ---------------------------
  private def draw(): Unit = {
    // mouse input
    val mouseX = Gdx.input.getX
    val mouseY = Gdx.input.getY

    val isLeftBtnPressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT)
    val isRightBtnPressed = Gdx.input.isButtonPressed(Input.Buttons.RIGHT)

    // keys input
    val wIsPressed = Gdx.input.isKeyPressed(Input.Keys.W)

    val textBoxX = 10 -(viewport.getWorldWidth/ 2)
    val textBoxY = (viewport.getWorldHeight / 2)
    font.draw(batch, s"Mouse: x=$mouseX | y=$mouseY", textBoxX, textBoxY)
    font.draw(batch, s"W pressed: ${if (wIsPressed) "YES" else "NO" }", textBoxX, textBoxY - font.getLineHeight)

  }
}

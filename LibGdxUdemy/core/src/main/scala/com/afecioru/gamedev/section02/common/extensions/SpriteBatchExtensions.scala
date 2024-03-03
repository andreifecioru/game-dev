package com.afecioru.gamedev.section02.common.extensions

import com.badlogic.gdx.graphics.{Color, OrthographicCamera}
import com.badlogic.gdx.graphics.g2d.SpriteBatch

object SpriteBatchExtensions {
  implicit class SpriteBatchExt(val batch: SpriteBatch) {
    def render(camera: OrthographicCamera)
              (block: => Unit): Unit = {

      batch.setProjectionMatrix(camera.combined)
      batch.begin()
      block
      batch.end()
    }

    def withColor(color: Color)
                 (block: => Unit): Unit = {
      val oldColor = new Color
      oldColor.set(batch.getColor)

      batch.setColor(color)
      block
      batch.setColor(oldColor)
    }
  }
}

package com.afecioru.gamedev.section02.common.extensions

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.{Color, OrthographicCamera}

object ShapeRendererExtensions {
  implicit class ShapeRendererExt(val renderer: ShapeRenderer) {
    import ShapeRenderer.ShapeType

    def render(camera: OrthographicCamera)
              (block: => Unit): Unit = {
      renderer.setProjectionMatrix(camera.combined)
      block
    }

    def withShape(shapeType: ShapeType)
                 (block: => Unit): Unit = {
      renderer.begin(shapeType)
      block
      renderer.end()
    }

    def withColor(color: Color)
                 (block: => Unit): Unit = {
      val oldColor = new Color
      oldColor.set(renderer.getColor)

      renderer.setColor(color)
      block
      renderer.setColor(oldColor)
    }
  }
}

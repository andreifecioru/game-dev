package com.afecioru.gamedev.section02.common.debug

import com.afecioru.gamedev.section02.common.BaseGameScreen
import com.afecioru.gamedev.section02.common.extensions.ShapeRendererExtensions
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.{Input, InputAdapter}

case class DebugHud(gameScreen: BaseGameScreen) {
  import ShapeRendererExtensions._

  var isGridEnabled = false

  private lazy val inputMultiplexer = gameScreen.inputMultiplexer
  private lazy val renderer = gameScreen.renderer

  private lazy val WORLD_WIDTH = gameScreen.WORLD_WIDTH
  private lazy val WORLD_HEIGHT = gameScreen.WORLD_HEIGHT

  def show(): Unit = {
    if (isGridEnabled) drawGrid()
  }

  private val inputProcessor = new InputAdapter {
    override def keyDown(keycode: Int): Boolean = {
      keycode match {
        case Input.Keys.F1 => isGridEnabled = !isGridEnabled
        case _ =>
      }

      false;
    }
  }

  inputMultiplexer.addProcessor(inputProcessor)

  private def drawGrid(): Unit = {
    val worldMaxHeight = WORLD_HEIGHT * 2
    val worldMaxWidth = WORLD_WIDTH * 2

    renderer.withShape(ShapeType.Line) {
      renderer.withColor(Color.WHITE) {
        (-worldMaxWidth.toInt to worldMaxWidth.toInt).foreach { x =>
          if (x == 0) {
            renderer.withColor(Color.RED) {
              renderer.line(x.toFloat, -worldMaxHeight, x.toFloat, worldMaxHeight)
            }
          } else {
            renderer.line(x.toFloat, -worldMaxHeight, x.toFloat, worldMaxHeight)
          }
        }

        (-worldMaxHeight.toInt to worldMaxHeight.toInt).foreach { y =>
          if (y == 0) {
            renderer.withColor(Color.RED) {
              renderer.line(-worldMaxWidth, y.toFloat, worldMaxWidth, y.toFloat)
            }
          } else {
            renderer.line(-worldMaxWidth, y.toFloat, worldMaxWidth, y.toFloat)
          }
        }
      }
    }

  }
}

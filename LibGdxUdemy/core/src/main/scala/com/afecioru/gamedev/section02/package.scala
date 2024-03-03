package com.afecioru.gamedev

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Color, GL20}
import com.badlogic.gdx.utils.Logger

import scala.language.implicitConversions


package object section02 {
  trait LoggingSupport {
    def logLevel: Int = Logger.DEBUG

    lazy val logger = new Logger(getClass.getSimpleName, logLevel)
  }

  object GenericUtils {
    object implicits {
      implicit def bool2int(b:Boolean): Int = if (b) 1 else 0
    }
  }

  object GdxUtils {
    def DELTA_TIME: Float = Gdx.graphics.getDeltaTime

    def clearScreen(): Unit = {
      clearScreen(Color.BLACK)
    }

    def clearScreen(color: Color): Unit = {
      Gdx.gl.glClearColor(color.r, color.g, color.b, color.a)
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }
  }
}


package com.afecioru.gamedev.section02.sample01.screen

import scala.collection.mutable.{Buffer => MBuffer}
import com.afecioru.gamedev.section02.common.GameContext
import com.afecioru.gamedev.section02.sample01.entity.{Obstacle, Player}
import com.badlogic.gdx.math.{Intersector, Vector2}

import java.util.concurrent.TimeUnit
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Scene2D()(implicit context: GameContext) {
  import Scene2D._
  import Scene2D.implicits._

  private var player: Player = _
  private val obstacles: MBuffer[Obstacle] = MBuffer.empty

  private var isPlayerAlive = true;

  def init(): Unit = {
    player = new Player()

    (1 to OBSTACLE_MAX_COUNT).map { _ =>
      Future {
        val delay = random.nextInt(OBSTACLE_MAX_DELAY_SEC)
        TimeUnit.SECONDS.sleep(delay)

        val obstacle = Obstacle(2.0f, new Vector2(0.0f, 11.0f))
        val posX = random.nextFloat() * context.WORLD_WIDTH
        obstacle.pos = new Vector2(posX, obstacle.pos.y)
        val fallSpeed = OBSTACLE_MIN_SPEED * random.nextFloat() * (OBSTACLE_MAX_SPEED - OBSTACLE_MIN_SPEED)
        obstacle.speed = new Vector2(obstacle.speed.x, fallSpeed)

        obstacles.synchronized {
          obstacles.append(obstacle)
        }
      }
    }
  }

  def refresh(): Unit = {
    isPlayerAlive = !obstacles.exists(player.collidesWith)

    if (isPlayerAlive) {
      player.refresh()

      obstacles.synchronized {
        obstacles.foreach(_.refresh())
      }
    }
  }

  def dispose(): Unit = {
    player.dispose()
    obstacles.foreach(_.dispose())
    obstacles.clear()
  }
}

object Scene2D {
  private val OBSTACLE_MIN_SPEED = 1.0f
  private val OBSTACLE_MAX_SPEED = 4.0f

  private val OBSTACLE_MAX_COUNT = 10
  private val OBSTACLE_MAX_DELAY_SEC = 10

  private val random = scala.util.Random

  object implicits {
    implicit class PlayerExtensions(val player: Player) {
      def collidesWith(obstacle: Obstacle): Boolean = {
        Intersector.overlaps(player.bounds, obstacle.bounds)
      }
    }
  }
}

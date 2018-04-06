package ui

import java.awt.event.ActionEvent

import ui.swing._
import java.{util => ju}
import javax.swing.AbstractAction

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import ui.swing.PieceKind.TKind

import scala.concurrent.Await
import scala.util.Random

/**
  * Created by Hessam Shafiei Moqaddam on 3/19/18.
  */
class AbstractUI {

  implicit val timeout = Timeout(100 millisecond)

  private[this] val stage = new Stage((10,20))

  private[this] var lastKey: String = ""

  private[this] val initialState = Stage.newState(Block(0,0),TKind) :: Nil, (10,23), randomStream(new scala.util.Random))
  private[this] val system = ActorSystem("TetrisSystem")
  private[this] val playerActor = system.actorOf(Props(new StageActor(initialState)), name = "playerActor")
  private[this] val timer = system.scheduler.schedule(0 millisecond, 700 millisecond, playerActor,Tick)
  private[this] def randomStream(random: Random): Stream[PieceKind] = PieceKind(random.nextInt % 7) #:: randomStream(random)


  private[this] var state = newState()

  def left(): Unit ={
    playerActor ! MoveLeft
  }
  def right(): Unit ={
    playerActor ! MoveRight
  }
  def up(): Unit ={
    playerActor ! RotateCW
  }
  def down(): Unit ={
    playerActor ! Tick
  }
  def space(): Unit ={
    playerActor ! Drop
  }

  def view: GameView = Await.result((stateActor ? GetView).mapTo[GameView],timeout.duration)

  def last: String = lastKey

}

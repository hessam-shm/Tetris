package ui

import java.awt.event.ActionEvent

import ui.swing.{Block, GameView, Stage}
import java.{util => ju}
import javax.swing.AbstractAction

/**
  * Created by Hessam Shafiei Moqaddam on 3/19/18.
  */
class AbstractUI {

  private[this] val stage = new Stage((10,20))

  private[this] var lastKey: String = ""

  private[this] val timer = new ju.Timer
  timer.scheduleAtFixedRate(new ju.TimerTask {
    override def run {state = tick(state)}
  },0,1000)

  val timer = new SwingTimer(100, new AbstractAction() {
    override def actionPerformed(e: ActionEvent): Unit = {repaint}
  })
  timer.start

  def left(): Unit ={
    stage.moveLeft()
  }
  def right(): Unit ={
    stage.moveRight()
  }
  def up(): Unit ={
    lastKey = "up"
  }
  def down(): Unit ={
    state = tick(state)
  }
  def space(): Unit ={
    lastKey = "space"
  }

  def last: String = lastKey

  def view: GameView = stage.view

  /*def view: GameView = GameView(Seq(Block((5,5),TKind),Block((6,5),TKind),Block((7,5),TKind),Block((6,6),TKind),Block((0,0),TKind))
    ,(10,20),
    Seq(Block((5,5),TKind),Block((6,5),TKind),Block((7,5),TKind),Block((6,6),TKind)))*/
}

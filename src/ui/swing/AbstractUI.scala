package ui

import ui.swing.{Block, GameView, Stage}

/**
  * Created by Hessam Shafiei Moqaddam on 3/19/18.
  */
class AbstractUI {

  private[this] val stage = new Stage((10,20))

  private[this] var lastKey: String = ""

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
    lastKey = "down"
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

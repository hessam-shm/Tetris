package ui.swing

import ui.AbstractUI

import scala.swing._
import event._

/**
  * Created by Hessam Shafiei Moqaddam on 3/19/18.
  */
object Main extends SimpleSwingApplication{

  import event.Key._
  import java.awt.{Dimension, Graphics2D, Graphics, Image, Rectangle}
  import java.awt.{Color => AWTColor}

  val bluishGrey = new AWTColor(48,99,99)
  val bluishSilver = new AWTColor(210,255,255)

  val ui = new AbstractUI

  def onKeyPress(keyCode: Value) = keyCode match {
    case Left => ui.left()
    case Right => ui.right()
    case Up => ui.up()
    case Down => ui.down()
    case Space => ui.space()
    case _ =>
  }

  def onPaint(g: Graphics2D): Unit ={
    g setColor bluishSilver
    g drawString (ui.last,20,20)
  }

  override def top = new MainFrame{
    title = "Tetris"
    contents = mainPanel
  }

  def mainPanel = new Panel {
    preferredSize = new Dimension(700,400)
    focusable = true
    listenTo(keys)
    reactions += {
      case KeyPressed(_,key,_,_) => onKeyPress(key)
        repaint
    }
  }

  def paint(g: Graphics2D): Unit ={
    g.setColor(bluishGrey)
    //g fillRect (0,0,size.width,size.height)
    g fillRect (0,0,700,400)
  }
}

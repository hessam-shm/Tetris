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

  private[this] def randomStream(random: util.Random): Stream[PieceKind] =
    PieceKind(random.nextInt % 7) #:: randomStream(random)

  def onPaint(g: Graphics2D): Unit ={

    val view = ui.view
    drawBorad(g,(0,0),view.gridSize,view.blocks,view.current)
    drawBoard(g,(12*(blockSize+blockMargin),0),view.minGridSize,view.next,Nil)

    //TODO:extract the rest of onpaint method to drawBoard method

    def buildRect(pos: (Int,Int)): Rectangle = new Rectangle(pos._1 * (blockSize + blockMargin),blockSize,blockMargin)

    def drawEmptyGrid: Unit ={
      g setColor bluishLighterGray
      for{
        x <- 0 to view.gridSize_1 - 1
        y <- 0 to view.gridSize_2 - 2
        val pos = (x,y)
      } g draw buildRect(pos)
    }

    def drawBlocks: Unit ={
      g setColor bluishEvenLighter
      view.blocks foreach {b => g fill buildRect(b.pos)}
    }

    def drawCurrent: Unit ={
      g setColor bluishSilver
      view.current foreach {b => g fill buildRect(b.pos)}
    }

    drawEmptyGrid
    drawBlocks
    drawCurrent
    /*g setColor bluishSilver
    g drawString (ui.last,20,20)*///TODO: no more needed
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

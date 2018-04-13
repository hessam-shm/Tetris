package ui.swing

import java.awt

import ui.AbstractUI

import scala.swing._
import event._

/**
  * Created by Hessam Shafiei Moqaddam on 3/19/18.
  */
object Main extends SimpleSwingApplication {

  import event.Key._
  import java.awt.{Dimension, Graphics2D, Graphics, Image, Rectangle}
  import java.awt.{Color => AWTColor}
  import javax.swing.{Timer => SwingTimer, AbstractAction}

  val bluishGrey = new AWTColor(48, 99, 99)
  val bluishLighterGrey = new AWTColor(79, 130, 130)
  val bluishEvenLighter = new AWTColor(145, 196, 196)
  val bluishSilver = new AWTColor(210, 255, 255)
  val blockSize = 16
  val blockMargin = 1
  val mainPanelSize = new Dimension(700, 400)

  val config = Config(minActionTime = 151, maxThinkTime = 1500, onDrop = Some(Tick))
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

  def onPaint(g: Graphics2D): Unit = {

    val (view1, view2) = ui.view
    val unitSize = blockSize + blockMargin
    val xOffset = mainPanelSize.width / 2
    drawBoard(g, (0, 0), (10, 20), view1.blocks, view1.current)
    drawBoard(g, (12 * unit, 0), view1.minGridSize, view1.next, Nil)
    drawStatus(g, (12 * unit, 0), view1)
    drawBoard(g, (xOffset, 0), (10, 20), view2.blocks, view2.current)
    drawBoard(g, (12 * unit + xOffset, 0), view2.minGridSize, view2.next, Nil)
    drawStatus(g, (12 * unit + xOffset, 0), view2)
  }

  //TODO:extract the rest of onpaint method to drawBoard method

  def buildRect(pos: (Int, Int)): Rectangle = new Rectangle(pos._1 * (blockSize + blockMargin), blockSize, blockMargin)

  def drawBoard(g: Graphics2D, offset: (Int,Int), gridSize: (Int,Int), blocks: Seq[Block], current: Seq[Block]): Unit ={

    def buildRect(pos: (Int,Int)): Rectangle = new Rectangle(offset._1 + pos._1 * (blockSize + blockMargin),
      offset._2 + (gridSize._2 - pos._2 - 1) * (blockSize + blockMargin), blockSize, blockSize)

    def drawEmptyGrid: Unit ={
      g setColor bluishLighterGrey
      for {
        x <- 0 to gridSize._1 - 1
        y <- 0 to gridSize._2 - 1
        val pos = (x,y)
      } g draw buildRect(pos)
    }

    def drawBlocks: Unit = {
      g setColor bluishEvenLighter
      blocks filter { _.pos._2 < gridSize._2} foreach { b => g fill buildRect(b.pos) }
    }

    def drawCurrent: Unit = {
      g setColor bluishSilver
      current filter { _.pos._2 < gridSize._2} foreach { b => g fill buildRect(b.pos) }
    }

    drawEmptyGrid
    drawBlocks
    drawCurrent

  }

  def drawStatus(g: Graphics2D, offset: (Int, Int), view: GameView): Unit = {
    val unit = blockSize + blockMargin
    g setColor bluishSilver

    view.status match {
      case GameOver => g drawString("Game over", offset._1, offset._2 + 8 * unit)
      case Victory => g drawString("You win!", offset._1, offset._2 + 8 * unit)
      case _ =>
    }
    g drawString("lines: " + view.lineCount.toString, offset._1, offset._2 + 7 * unit)
  }

  override def top = new MainFrame {
    title = "Tetris"
    contents = mainPanel
  }

  def mainPanel = new Panel {
    preferredSize = mainPanelSize
    focusable = true
    listenTo(keys)
    reactions += {
      case KeyPressed(_, key, _, _) => onKeyPress(key)
        repaint
    }

    val timer = new SwingTimer(100,new AbstractAction() {
      override def actionPerformed(e: awt.event.ActionEvent): Unit = {repaint}
    })
    timer.start
  }

  override def paint(g: Graphics2D): Unit = {
    g.setColor(bluishGrey)
    g fillRect (0,0,size.width,size.height)
    onPaint(g)
  }
}

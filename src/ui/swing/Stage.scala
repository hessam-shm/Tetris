package ui.swing

/**
  * Created by Hessam Shafiei Moqaddam on 3/20/18.
  */
class Stage(size: (Int,Int)) {
  private[this] def dropOfPos = (size._1/2.0, size._2 - 3.0)
  private[this] var currentPiece = Piece(dropOfPos,TKind)
  private[this] var blocks = Block((0,0),TKind) +: currentPiece.current
  def view: GameView = GameView(blocks,size,currentPiece.current)

  def moveLeft() = moveBy(-1.0,0.0)
  def moveRight() = moveBy(1.0,0.0)
  private[this] def moveBy(delta: (Double,Double)): this.type = {
    val unloaded = unload(currentPiece,blocks)
    val moved = currentPiece.moveBy(delta)
    blocks = load(moved,unloaded)
    currentPiece = moved
    this
  }
  private[this] def unload(p: Piece, bs: Seq[Block]): Seq[Block] = {
    val currentPoss = p.current map {_.pos}
    bs filterNot {currentPoss contains _.pos}
  }
  private[this] def load(p: Piece, bs: Seq[Block]): Seq[Block] = bs ++ p.current
}

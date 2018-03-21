package ui.swing

/**
  * Created by Hessam Shafiei Moqaddam on 3/20/18.
  */
class Stage(size: (Int,Int)) {
  private[this] def dropOfPos = (size._1/2.0, size._2 - 3.0)
  private[this] var currentPiece = Piece(dropOfPos,TKind)
  private[this] var blocks = Block((0,0),TKind) +: currentPiece.current
  def view: GameView = GameView(blocks,size,currentPiece.current)

  def moveLeft() = transformPiece(_.moveBy(-1.0,0.0))
  def moveRight() = transformPiece(_.moveBy(1.0,0.0))
  def rotateCW() = transformPiece(_.rotateBy(-math.Pi/2.0))

  /*private[this] def moveBy(delta: (Double,Double)): this.type = {
    validate(currentPiece.moveBy(delta),
      unload(currentPiece, blocks)) map { case (moved, unloaded) => blocks = load(moved, unloaded)
      currentPiece = moved
    }
    this
  }

  private[this] def rotateBy(theta: Double): this.type = {
    validate(currentPiece.rotateBy(theta),
      unload(currentPiece,blocks)) map {case (moved,unloaded) =>
        blocks = load(moved,unloaded)
        currentPiece = moved
    }
    this
  }*///TODO:test refactored part

  private def transformPiece(trans: Piece => Piece): this.type = {
    validate(trans(currentPiece),
      unload(currentPiece,blocks)) map {case (moved,unloaded) =>
        blocks = load(moved,unloaded)
        currentPiece = moved
    }
    this
  }

  private[this] def validate(p: Piece, bs: Seq[Block]): Option[(Piece,Seq[Block])] =
    if (p.current map {_.pos} forall inBounds) Some(p,bs)
    else None

  private[this] def inBounds(pos: (Int,Int)): Boolean =
    (pos._1 >= 0) && (pos._1 < size._1) && (pos._2 >= 0) && (pos._2 < size._2)

  private[this] def unload(p: Piece, bs: Seq[Block]): Seq[Block] = {
    val currentPoss = p.current map {_.pos}
    bs filterNot {currentPoss contains _.pos}
  }
  private[this] def load(p: Piece, bs: Seq[Block]): Seq[Block] = bs ++ p.current
}

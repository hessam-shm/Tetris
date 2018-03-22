package ui.swing

/**
  * Created by Hessam Shafiei Moqaddam on 3/20/18.
  */
class Stage(size: (Int,Int)) {
  private[this] def dropOffPos = (size._1/2.0, size._2 - 3.0)
  private[this] var currentPiece = Piece(dropOfPos,TKind)
  private[this] var blocks = Block((0,0),TKind) +: currentPiece.current
  def view: GameView = GameView(blocks,size,currentPiece.current)

  def moveLeft() = transit(_.moveBy(-1.0,0.0))
  def moveRight() = transit(_.moveBy(1.0,0.0))
  def rotateCW() = transit(_.rotateBy(-math.Pi/2.0))
  val tick = transit (_.moveBy(0.0,-1.0),spawn)

  def newState(blocks: Seq[Block]): GameState = {
    val size = (10,20)
    def dropOffPos = (size._1 / 2.0,size._2 - 3.0)
    val p = Piece(dropOffPos,TKind)
    GameState(blocks ++ p.current,size,p)
  }

  private[this] def transit(trans: Piece => Piece, onFail: GameState => GameState = identity): GameState => GameState =
    (s: GameState) => validate(s.copy(
      blocks = unload(s.currentPiece,s.blocks),
      currentPiece = trans(s.currentPiece))) map {case x =>
    x.copy(blocks = load(x.cuttentPiece,x.blocks))
    } getOrElse {onFail(s)}

  private[this] def validate(s: GameState): Option[GameState] = {
    val size = s.gridSize
    def inBounds(pos: (Int,Int)): Boolean =
      (pos._1 >= 0) && (pos._1 < size._1) && (pos._2 >= 0) && (pos._2 < size._2)
    val currentPoss = s.currentPiece.current map {_.pos}
    if((currentPoss forall inBounds) && (s.blocks map {_.pos} intersect currentPoss).isEmpty) Some(s)
    else None
  }

  private[this] def spawn(s: GameState): GameState = {
    def dropOffPos = (s.gridSize._1 / 2.0,s.gridSize._2 - 3.0)
    val p = Piece(dropOffPos,TKind)
    s.copy(blocks = s.blocks ++ p.current, currentPiece = p)
  }

  /*private def transformPiece(trans: Piece => Piece): this.type = {
    validate(trans(currentPiece),
      unload(currentPiece,blocks)) map {case (moved,unloaded) =>
        blocks = load(moved,unloaded)
        currentPiece = moved
    }
    this
  }*/

  /*private[this] def validate(p: Piece, bs: Seq[Block]): Option[(Piece,Seq[Block])] =
    if (p.current map {_.pos} forall inBounds) Some(p,bs)
    else None*/

  /*private[this] def inBounds(pos: (Int,Int)): Boolean =
    (pos._1 >= 0) && (pos._1 < size._1) && (pos._2 >= 0) && (pos._2 < size._2)*/

  private[this] def unload(p: Piece, bs: Seq[Block]): Seq[Block] = {
    val currentPoss = p.current map {_.pos}
    bs filterNot {currentPoss contains _.pos}
  }
  private[this] def load(p: Piece, bs: Seq[Block]): Seq[Block] = bs ++ p.current
}

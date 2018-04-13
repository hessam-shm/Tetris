package ui.swing

/**
  * Created by Hessam Shafiei Moqaddam on 3/20/18.
  */

sealed trait PieceKind {def toInt: Int}

case object IKind extends PieceKind {
  override def toInt: Int = 0}
case object JKind extends PieceKind {
  override def toInt: Int = 1}
case object LKind extends PieceKind {
  override def toInt: Int = 2}
case object OKind extends PieceKind {
  override def toInt: Int = 3}
case object SKind extends PieceKind {
  override def toInt: Int = 4}
case object TKind extends PieceKind {
  override def toInt: Int = 5}
case object ZKind extends PieceKind {
  override def toInt: Int = 6}
case object PieceKind {
  def apply(x: Int): PieceKind = x match {
    case 0 => IKind
    case 1 => JKind
    case 2 => LKind
    case 3 => OKind
    case 4 => SKind
    case 5 => TKind
    case _ => ZKind
  }
}

sealed trait GameStatus
case object ActiveStatus extends GameStatus
case object GameOver extends GameStatus
case object Victory extends GameStatus

case class Block(pos: (Int,Int), kind: PieceKind)

case class GameView(blocks: Seq[Block], gridSize: (Int,Int), current: Seq[Block], minGridSize: (Int,Int),
                    next: Seq[Block], status: GameStatus, lineCount: Int)

case class GameState(blocks: Seq[Block], gridSize: (Int,Int), currentPiece: Piece, nextPiece: Piece,
                     kinds: Seq[PieceKind], status: GameStatus = ActiveStatus, lineCounts: Seq[Int] = Seq(0,0,0,0,0),
                     lastDeleted: Int = 0, pendingAttacks: Int = 0){
  def lineCount: Int = lineCounts.zipWithIndex map {case (n,i) => n * i} sum
  def attackCount: Int = lineCounts.drop(1).zipWithIndex map {case (n,i) => n * i} sum
  def view: GameView = GameView(blocks, gridSize, currentPiece.current, (4,4), nextPiece.current, status, lineCount)
  def unload(p: Piece): GameState = {
    val currentPoss = p.current map {_.pos}
    this.copy(blocks = blocks filterNot {currentPoss contains _.pos})
  }
  def load(p: Piece): GameState = this.copy(blocks = blocks ++ p.current)
}

case class Piece(pos: (Double,Double), kind: PieceKind, locals: Seq[(Double,Double)]){
  def current: Seq[Block] = locals map {case (x,y) =>
          Block((math.floor(x+pos._1).toInt,math.floor(y+pos._2).toInt),kind)
  }

  def moveBy(delta: (Double,Double)): Piece = copy(pos = (pos._1 + delta._1, pos._2 + delta._2))

  def rotateBy(theta: Double): Piece = {
    val c = math.cos(theta)
    val s = math.sin(theta)
    def roundToHalf(v: (Double,Double)): (Double,Double) =
      (math.round(v._1 * 2.0) * 0.5,math.round(v._2 * 2.0) * 0.5)
    copy(locals = locals map {case (x,y) => (x*c - y*s, x*s + y*c)} map roundToHalf)
  }
}

case object Piece{
  def apply(pos: (Double, Double), kind: PieceKind): Piece =
    Piece(pos,kind,kind match {
      case IKind => Seq((-1.5,0.0),(-0.5,0.0),(0.5,0.0),(1.5,0.0))
      case JKind => Seq((-1.0,0.5),(0.0,0.5),(1.0,0.5),(1.0,-0.5))
      case LKind => Seq((-1.0,0.5),(0.0,0.5),(1.0,0.5),(-1.0,-0.5))
      case OKind => Seq((-0.5,0.5),(0.5,0.5),(-0.5,-0.5),(0.5,-0.5))
      case SKind => Seq((0.0,0.5),(1.0,0.5),(-1.0,-0.5),(0.0,-0.5))
      case TKind => Seq((-1.0,0.0),(0.0,0.0),(1.0,0.0),(0.0,1.0))
      case ZKind => Seq((-1.0,0.5),(0.0,0.5),(0.0,-0.5),(1.0,-0.5))
    })
}
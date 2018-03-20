package ui.swing

/**
  * Created by Hessam Shafiei Moqaddam on 3/20/18.
  */
case class Piece(pos: (Double,Double), kind: PieceKind, locals: Seq[(Double,Double)]){
  def current: Seq[Block] = locals map {case (x,y) =>
          Block((math.floor(x+pos._1).toInt,math.floor(y+pos._2).toInt),kind)
  }

  def moveBy(delta: (Double,Double)): Piece = copy(pos = (pos._1 + delta._1, pos._2 + delta._2))
}

case object Piece{
  def apply(pos: (Double, Double), kind: PieceKind): Piece =
    kind match {
      case TKind => Piece(pos,kind,Seq((-1.0,0.0),(0.0,0.0),(1.0,0.0),(0.0,1.0)))
    }
}
package ui.swing

/**
  * Created by Hessam Shafiei Moqaddam on 3/19/18.
  */
sealed trait PieceKind {

  case object IKind extends PieceKind
  case object JKind extends PieceKind
  case object LKind extends PieceKind
  case object OKind extends PieceKind
  case object SKind extends PieceKind
  case object TKind extends PieceKind
  case object ZKind extends PieceKind
}

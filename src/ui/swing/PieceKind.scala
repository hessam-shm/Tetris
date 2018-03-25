package ui.swing

import ui.swing.PieceKind.{IKind, JKind, ZKind}

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

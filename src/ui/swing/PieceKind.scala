package ui.swing

import ui.swing.PieceKind.{IKind, JKind, ZKind}

/**
  * Created by Hessam Shafiei Moqaddam on 3/19/18.
  */
sealed trait PieceKind {
  def toInt: Int
}

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
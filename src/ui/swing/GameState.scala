package ui.swing

/**
  * Created by Hessam Shafiei Moqaddam on 3/22/18.
  */
case class GameState(blocks: Seq[Block], gridSize: (Int,Int), currentPiece: Piece,
                     nextPiece: Piece, kinds: Seq[PieceKind]){
  def view: GameView = GameView(blocks,gridSize,currentPiece.current,nextPiece.current)
}

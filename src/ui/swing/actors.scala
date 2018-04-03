package ui.swing

/**
  * Created by Hessam Shafiei Moqaddam on 4/4/18.
  */
class actors {

  sealed trait StageMessage
  case object MoveLeft extends StageMessage
  case object MoveRight extends StageMessage
  case object RotateCW extends StageMessage
  case object Tick extends StageMessage
  case object Drop extends StageMessage
  case object View extends StageMessage

  class StageActor(s0: GameState) extends Actor{
    import ui.swing.Stage._

    private[this] var state: GameState = s0

    def receive = {
      case MoveLeft => state = moveLeft(state)
      case MoveRight => state = moveRight(state)
      case RotateCW => state = rotateCW(state)
      case Tick => state = tick(state)
      case Drop => state = drop(state)
      case View => sender ! state.view
    }
  }
}

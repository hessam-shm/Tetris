package ui.swing

import akka.actor.{Actor, ActorRef}

/**
  * Created by Hessam Shafiei Moqaddam on 4/4/18.
  */
class actors {

  sealed trait StageMessage
  case object GetState extends StageMessage
  case class SetState(s: GameState) extends StageMessage
  case object GetView extends StageMessage
  case object MoveLeft extends StageMessage
  case object MoveRight extends StageMessage
  case object RotateCW extends StageMessage
  case object Tick extends StageMessage
  case object Drop extends StageMessage
  case object View extends StageMessage

  class StateActor(s0: GameState) extends Actor{
    private[this] var state: GameState = s0

    override def receive = {
      case GetState => sender ! state
      case SetState(s) => state = s
      case GetView => sender ! state.view
    }
  }

  class StageActor(stateActor: ActorRef) extends Actor{
    import ui.swing.Stage._

    def receive = {
      case MoveLeft => updateState {moveLeft}
      case MoveRight => updateState {moveRight}
      case RotateCW => updateState {rotateCW}
      case Tick => updateState {tick}
      case Drop => updateState {drop}
    }

    private[this] def updateState(trans:  GameState => GameState) {
      val future = (stateActor ? GetState)(1 second).mapTo[GameState]
      val s2 = trans(s1)
      stateActor ! SetState(s2)
    }
  }
}

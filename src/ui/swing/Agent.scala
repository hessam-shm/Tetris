package ui.swing

/**
  * Created by Hessam Shafiei Moqaddam on 4/14/18.
  */
class Agent {

  import Stage._
  import scala.annotation.tailrec

  private[this] val minUtility = -1000.0

  def utility(state: GameState): Double = if(state.status == GameOver) minUtility else reward(state) + (penalty(state)/minUtility)

  def reward(s: GameState): Double = if(s.lastDeleted < 2) 0 else s.lastDeleted

  def penalty(s: GameState): Double = {
    val groupedByX = s.unload(s.currentPiece).blocks map {_.pos} groupBy {_._1}
    val heights = groupedByX map {case (k,v) => (k, v.map({_._2+1}).max)}
    val heightWeight = 11
    val weightedHeights = heights.values map {heightWeight * _}
    val hWithDefault = heights withDefault {x => if(x<0 || x>s.gridSize._1-1) s.gridSize._2 else 0}
    val crevassesWeight = 10
    val crevasses = (-1 to s.gridSize._1 - 2) flatMap {x =>
      val down = hWithDefault(x+1) - hWithDefault(x)
      val up = hWithDefault(x+2) - hWithDefault(x+1)
      if(down < -3 && up > 3)
        Some(math.min(crevassesWeight * hWithDefault(x), crevassesWeight * hWithDefault(x+2)))
      else None
    }
    val coverupWeight = 1
    val coverups = groupedByX flatMap {case (k,vs) =>
        if(vs.size < heights(k)) Some(coverupWeight * heights(k))
        else None
    }
    math.sqrt((weightedHeights ++ crevasses ++ coverups) map {x => x*x} sum)
  }

}

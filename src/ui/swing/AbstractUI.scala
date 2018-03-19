package ui

/**
  * Created by Hessam Shafiei Moqaddam on 3/19/18.
  */
class AbstractUI {

  private[this] var lastKey: String = ""

  def left(): Unit ={
    lastKey = "left"
  }
  def right(): Unit ={
    lastKey = "right"
  }
  def up(): Unit ={
    lastKey = "up"
  }
  def down(): Unit ={
    lastKey = "down"
  }
  def space(): Unit ={
    lastKey = "space"
  }

  def last: String = lastKey
}

package DevTest

object StringTest extends App{

  val string = "hoang dep trai vai noi, nhung ma chim be"
  val result = string.replace("be", "to").replace("nhung", "")
  println(result)
  println(classOf[String].getName)


}

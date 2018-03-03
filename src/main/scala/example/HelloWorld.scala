package example

object HelloWorld extends App {
  println("Hello World!")
  var w = "lalala., sasas; dsdkds dsdsdsds aaaa10"
  println(w)
  val x = w.replaceAll("[^A-Za-z]","")
  println(x)
}

import mill._
import $ivy.`com.lihaoyi::mill-contrib-playlib:`,  mill.playlib._

object socialnetwork extends PlayModule with SingleModule {

  def scalaVersion = "2.13.10"
  def playVersion = "2.8.19"
  def twirlVersion = "1.5.1"

  object test extends PlayTests
}
// play-slick 5.0.x is currently built and tested against version 1.4.200
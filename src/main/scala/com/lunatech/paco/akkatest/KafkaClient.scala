package com.lunatech.paco.akkatest

import javafx.scene.paint.Color

import akka.actor.Actor
import KafkaClient.RawEvent

class KafkaClient extends Actor {

  override def preStart(): Unit = {
    println(Console.MAGENTA + "KafkaClient started" + Console.RESET)
  }

  override def receive: Receive = {
    case RawEvent(payload) =>
      println(Console.MAGENTA + s"Received RawEvent($payload)\npath: ${self.path}" + Console.RESET)
      println(Console.MAGENTA + s"Waiting…\npath: ${self.path}" + Console.RESET)
      Thread.sleep(5000)
      println(Console.MAGENTA + s"Done waiting\npath: ${self.path}" + Console.RESET)
  }
}

object KafkaClient {
  case class RawEvent(payload: String)
}

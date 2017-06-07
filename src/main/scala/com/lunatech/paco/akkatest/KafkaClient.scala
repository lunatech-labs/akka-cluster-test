package com.lunatech.paco.akkatest

import javafx.scene.paint.Color

import akka.actor.Actor
import KafkaClient.RawEvent

class KafkaClient extends Actor {
  override def receive: Receive = {
    case RawEvent(payload) =>
      println(Console.MAGENTA + s"Received RawEvent($payload)\npath: ${self.path}\naddress: ${self.path.address}" + Console.RESET)
  }
}

object KafkaClient {
  case class RawEvent(payload: String)
}

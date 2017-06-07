package com.lunatech.paco.akkatest

import akka.actor.{Actor, ActorSelection}
import Connector.MqttMsg
import KafkaClient.RawEvent

class Connector extends Actor {
  val kafkaClient: ActorSelection = context.actorSelection("/kafka-client")

  override def receive: Receive = {
    case MqttMsg(payload) =>
      println(Console.MAGENTA + s"Received MqttMsg($payload)\npath: ${self.path}" + Console.RESET)
//      println(Console.MAGENTA + "waiting…" + Console.RESET)
//      Thread.sleep(10000)
//      println(Console.MAGENTA + s"Done waiting\n${self.path}" + Console.RESET)
      kafkaClient ! RawEvent(payload)
  }
}

object Connector {
  case class MqttMsg(payload: String)
}

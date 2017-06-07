package com.lunatech.paco.akkatest

import akka.actor.{Actor, ActorSelection}
import Connector.MqttMsg
import KafkaClient.RawEvent

class Connector extends Actor {
  val kafkaClient: ActorSelection = context.actorSelection("/user/kafka-client-proxy")


  override def preStart(): Unit = {
    println(Console.MAGENTA + "Connector started!" + Console.RESET)
  }

  override def receive: Receive = {
    case MqttMsg(payload) =>
      println(Console.MAGENTA + s"Received MqttMsg($payload)\npath: ${self.path}" + Console.RESET)
      println(Console.MAGENTA + s"Waiting…\npath: ${self.path}" + Console.RESET)
      Thread.sleep(5000)
      println(Console.MAGENTA + s"Done waiting\npath: ${self.path}" + Console.RESET)
      kafkaClient ! RawEvent(payload)
  }
}

object Connector {
  case class MqttMsg(payload: String)
}

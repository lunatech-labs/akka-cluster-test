package com.lunatech.paco.akkatest

import akka.actor.{ActorSystem, Props}
import akka.routing.FromConfig
import Connector.MqttMsg

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.io.StdIn.readLine

object Sender extends App {
  override def main(args: Array[String]): Unit = {
    val system = ActorSystem("my-cluster")

    println("Enter a message to send, or “q” to quit.")

//    val kafkaRouter = system.actorOf(FromConfig.props(Props[KafkaClient]), "kafka-client")
    val connectorRouter = system.actorOf(FromConfig.props(Props[Connector]), "connector")
    val connector = system.actorSelection("/user/connector")

    def rl: String = {
      readLine(Console.MAGENTA + "> " + Console.RESET)
    }

    var message: String = rl

    while (message != "q") {
      for (i <- 1 to 20) {
        connector ! MqttMsg(s"$message-$i")
      }
      message = rl
    }

    println(Console.MAGENTA + "Terminating actor system" + Console.RESET)
    Await.result(system.terminate(), 1000.seconds)
    println(Console.MAGENTA + "Actor system terminated" + Console.RESET)
  }
}

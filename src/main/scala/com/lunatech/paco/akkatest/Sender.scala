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

    val connector = system.actorOf(FromConfig.props(Props[Connector]), "connector")

    var message: String = readLine("> ")

    while (message != "q") {
      connector ! MqttMsg(message)
      message = readLine("> ")
    }

    println(Console.MAGENTA + "Terminating actor system" + Console.RESET)
    Await.result(system.terminate(), 1000.seconds)
    println(Console.MAGENTA + "Actor system terminated" + Console.RESET)
  }
}

package com.lunatech.paco.akkatest

import akka.actor.ActorSystem

import scala.concurrent.duration.DurationInt
import scala.concurrent.Await
import scala.io.StdIn.readLine

object ClusterNode extends App {
  override def main(args: Array[String]): Unit = {
    val system = ActorSystem("my-cluster")

    readLine

    println(Console.MAGENTA + "Terminating actor system “my-cluster”" + Console.RESET)
    Await.ready(system.terminate(), 1000.seconds)
    println(Console.MAGENTA + "Actor system “my-cluster” terminated" + Console.RESET)
  }
}

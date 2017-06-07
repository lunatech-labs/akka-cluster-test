package com.lunatech.paco.akkatest

import akka.actor.{ActorSystem, CoordinatedShutdown, PoisonPill, Props}
import akka.cluster.Cluster
import akka.cluster.singleton.{ClusterSingletonManager, ClusterSingletonManagerSettings, ClusterSingletonProxy, ClusterSingletonProxySettings}
import akka.routing.FromConfig

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future, Promise}
import scala.io.StdIn.readLine
import scala.util.Try

object ClusterNode extends App {
  override def main(args: Array[String]): Unit = {
    val system = ActorSystem("my-cluster")

    system.actorOf(ClusterSingletonManager.props(
      singletonProps = FromConfig.props(FromConfig.props(Props[KafkaClient])),
      terminationMessage = PoisonPill,
      settings = ClusterSingletonManagerSettings(system)
    ), "kafka-client")

    system.actorOf(ClusterSingletonProxy.props(
      singletonManagerPath = "/user/kafka-client",
      settings = ClusterSingletonProxySettings(system)
    ), "kafka-client-proxy")

    readLine

    println(Console.MAGENTA + "Terminating actor system “my-cluster”" + Console.RESET)
    val cluster = Cluster.get(system)
    cluster.leave(cluster.selfAddress)
    Try { Await.ready(Promise.apply().future, 11.seconds) }
    Await.ready(CoordinatedShutdown(system).run(), 1000.seconds)
    println(Console.MAGENTA + "Actor system “my-cluster” terminated" + Console.RESET)
  }
}

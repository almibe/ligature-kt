/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package dev.ligature.wander.zeromq

import org.zeromq.{ZMQ, ZContext, SocketType}

import dev.ligature.wander.run as runBend
import dev.ligature.wander.BendValue
import dev.ligature.wander.printBendValue
import dev.ligature.wander.printResult
import dev.ligature.wander.*
import com.typesafe.scalalogging.Logger
import dev.ligature.wander.modules.*
import dev.ligature.inmemory.LigatureInMemory
import dev.ligature.Ligature

private class LigatureZeroMQ(val port: Int, ligature: Ligature)
    extends Runnable
    with AutoCloseable {
  val logger = Logger("LigatureZeroMQ")
  private val zContext = ZContext()

  override def run(): Unit =
    val environment = stdWithLigature(ligature)
    val socket = zContext.createSocket(SocketType.REP)
    socket.bind(s"tcp://localhost:$port")
    var continue = true
    logger.info(s"Starting server loop on $port!")
    while (!Thread.currentThread().isInterrupted() && continue)
      try
        val command = String(socket.recv(0), ZMQ.CHARSET) // blocks waiting for a request
        logger.info(s"Command: $command")
        val result = runBend(command, environment)
        result match
          case Left(err) =>
            val message = s"Error running command: $command -- ${err.userMessage}"
            logger.error(message)
            val _ = socket.send(message.getBytes(ZMQ.CHARSET), 0)
          case result: Right[BendError, (BendValue, Environment)] =>
            logger.info(s"Result for command: $command -- ${printResult(result)}")
            val _ = socket.send(printResult(result).getBytes(ZMQ.CHARSET), 0)
      catch
        case e =>
          socket.close()
          zContext.close()
          e.printStackTrace()
          continue = false

  override def close(): Unit = zContext.close()
}

def printError(message: String): String =
  printBendValue(BendValue.Module(Map(Field("error") -> BendValue.String(message))))

def runServer(port: Int): AutoCloseable = {
  val server = LigatureZeroMQ(port, LigatureInMemory())
  val thread = Thread(server)
  thread.start()
  new AutoCloseable {
    def close(): Unit = server.close()
  }
}

@main def main =
  val server = LigatureZeroMQ(4200, LigatureInMemory())
  server.run()

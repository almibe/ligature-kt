/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package dev.ligature.wander

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint._
import scalafx.scene.text.Text
import scalafx.scene.control.TextArea
import scalafx.scene.layout.VBox
import scalafx.scene.control.Button
import scalafx.stage.StageStyle
import dev.ligature.wander.preludes.common
import scalafx.scene.layout.BorderPane
import scalafx.scene.control.SplitPane
import scalafx.geometry.Orientation
import scalafx.scene.input.KeyEvent
import scalafx.event.EventHandler
import javafx.scene.input.KeyCode

object ScalaFXHelloWorld extends JFXApp3 {
  override def start(): Unit = {
    val editorInput = TextArea("")
    val resultOutput = TextArea("")
    resultOutput.editable = false
    val runButton = Button("Run")
    val introButton = Button("Intro")
    runButton.onAction = { e =>
      runScript()
    }

    def inMemoryRun(script: String): Either[WanderError, (WanderValue, Environment)] = {
      // createLigatureInMemory().use { instance => 
      //   IO.pure(run(script, common(instance)))
      // }.unsafeRunSync()
      ???
    }

    def runScript() = {
      val script = editorInput.getText()
      val result = inMemoryRun(script)
      resultOutput.text = printResult(result)
    }

    def runIntro() = {
      val script = editorInput.getText()
      val intro = introspect(script)
      val result = inMemoryRun(script)

      resultOutput.text = "Tokens      :" + intro.tokens.toString() + "\n" +
        "Terms       :" + intro.terms.toString() + "\n" +
        "Expressions :" + intro.expression.toString() + "\n" +
        "Result      :" + result.toString() + "\n "
    }

    introButton.onAction = { e =>
      runIntro()
    }

    stage = new JFXApp3.PrimaryStage {
      title = "WanderPad"
      width = 800
      height = 600
      scene = new Scene {
        onShown = { _ => editorInput.requestFocus() }
        addEventFilter(
          KeyEvent.KeyPressed,
          event => {
            if (
              (event.getCode() == KeyCode.R ||
                event.getCode() == KeyCode.ENTER)
              && event.isControlDown()
            ) {
              runScript()
              event.consume()
            }
            if (event.getCode() == KeyCode.I && event.isControlDown()) {
              runIntro()
              event.consume()
            }
          }
        )
        fill = Color.rgb(255, 255, 255)
        root = new BorderPane {
          style = "-fx-font-family: Consolas, monospace"
          top = new HBox {
            children = Seq(
              runButton,
              introButton
            )
          }
          center = new SplitPane {
            items ++= Seq(editorInput, resultOutput)
            orientation = Orientation.Vertical
          }
        }
      }
    }
    editorInput.requestFocus()
  }
}

/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package dev.ligature.testsuite

import dev.ligature._
import munit.FunSuite
//import dev.ligature.inmemory.LigatureInMemory
//import dev.ligature.wander.modules.std

class LigatureTestSuite extends FunSuite {
  // val setup = ???//FunFixture[(Environment, Ligature)](
  // setup = { test =>
  //  ???
  // val instance = LigatureInMemory()
  // (stdWithLigature(instance), instance)
  // },
  // teardown = { instance =>
  //   instance._2.close()
  //  }
  // )

  def check(script: String) = ???
  // run(script) match {
  //   case Left(value)  => throw value
  //   case Right(value) => value
  // }

  // setup.test("run empty string") { (instance, _) =>
  //   assertEquals(check("", instance), LigatureValue.Module(Map()))
  // }

  // setup.test("datasets should start empty") { (instance, _) =>
  //   assertEquals(check("Ligature.datasets ()", instance), LigatureValue.Array(Seq()))
  // }

  // setup.test("create datasets") { (instance, _) =>
  //   assertEquals(
  //     check("Ligature.addDataset \"hello\", Ligature.datasets ()", instance),
  //     LigatureValue.Array(Seq(LigatureValue.String("hello")))
  //   )
  // }

  // setup.test("delete datasets") { (instance, _) =>
  //   assertEquals(
  //     check(
  //       "Ligature.addDataset \"hello\", Ligature.removeDataset \"hello\", Ligature.datasets ()",
  //       instance
  //     ),
  //     LigatureValue.Array(Seq())
  //   )
  // }
}

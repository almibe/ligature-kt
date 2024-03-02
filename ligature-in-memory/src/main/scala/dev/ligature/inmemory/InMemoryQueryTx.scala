/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package dev.ligature.inmemory

import dev.ligature.*

/** Represents a QueryTx within the context of a Ligature instance and a single
  * Graph
  */
class InMemoryQueryTx(private val store: GraphStore) extends QueryTx {

  /** Returns all PersistedStatements in this Graph. */
  def allStatements(): Iterator[Statement] = store.edges.iterator

  /** Returns all PersistedStatements that match the given criteria. If a
    * parameter is None then it matches all, so passing all Nones is the same as
    * calling allStatements.
    */
  override def matchStatements(
      source: Option[LigatureValue.Identifier],
      label: Option[LigatureValue.Identifier],
      target: Option[LigatureValue]
  ): Iterator[Statement] = {
    var res = store.edges.iterator
    if (source.isDefined) {
      res = res.filter(_.source == source.get)
    }
    if (label.isDefined) {
      res = res.filter(_.label == label.get)
    }
    if (target.isDefined) {
      res = res.filter(_.target == target.get)
    }
    res
  }

//  /** Returns all PersistedStatements that match the given criteria. If a
//    * parameter is None then it matches all.
//    */
//  override def matchStatementsRange(
//      source: Option[Identifier],
//      label: Option[Identifier],
//      range: dev.ligature.Range
//  ): Iterator[Statement] = {
//    var res = Stream.emits(store.edges.toSeq)
//    if (source.isDefined) {
//      res = res.filter(_.source == source.get)
//    }
//    if (label.isDefined) {
//      res = res.filter(_.label == label.get)
//    }
//    res = res.filter { ps =>
//      val testValue = ps.target
//      (testValue, range) match {
//        case (StringValue(v), StringValueRange(start, end)) =>
//          v >= start && v < end
//        case (IntegerValue(v), IntegerValueRange(start, end)) =>
//          v >= start && v < end
//        case _ => false
//      }
//    }
//    res
//  }
}

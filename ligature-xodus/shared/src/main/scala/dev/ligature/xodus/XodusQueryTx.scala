/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package dev.ligature.xodus

import cats.effect.IO
import dev.ligature.*
import fs2.Stream
import jetbrains.exodus.{ByteIterable, CompoundByteIterable}
import jetbrains.exodus.bindings.{ByteBinding, LongBinding, StringBinding}
import jetbrains.exodus.env.Transaction

import scala.collection.mutable.ArrayBuffer

/** Represents a QueryTx within the context of a Ligature instance and a single
  * Dataset
  */
class XodusQueryTx(
    private val tx: Transaction,
    private val xodusOperations: XodusOperations,
    private val datasetID: ByteIterable
) extends QueryTx {

  private def lookupIdentifier(internalIdentifier: ByteIterable): Identifier =
    val idToIdentifierStore = xodusOperations.openStore(tx, LigatureStore.IdToIdentifierStore)
    val result = idToIdentifierStore.get(tx, CompoundByteIterable(Array(datasetID, internalIdentifier)))
    if (result != null) {
      Identifier.fromString(StringBinding.entryToString(result)).getOrElse(???)
    } else {
      ???
    }

  private def lookupStringLiteral(internalIdentifier: ByteIterable): StringLiteral =
    val idToStringStore = xodusOperations.openStore(tx, LigatureStore.IdToStringStore)
    val result = idToStringStore.get(tx, CompoundByteIterable(Array(datasetID, internalIdentifier)))
    if (result != null) {
      StringLiteral(StringBinding.entryToString(result))
    } else {
      ???
    }

  private def constructValue(valueTypeId: Byte, valueContent: ByteIterable): Value =
    LigatureValueType.getValueType(valueTypeId) match {
      case LigatureValueType.Identifier => lookupIdentifier(valueContent)
      case LigatureValueType.Integer    => IntegerLiteral(LongBinding.entryToLong(valueContent))
      case LigatureValueType.String     => lookupStringLiteral(valueContent)
      case LigatureValueType.Bytes      => ???
    }

  private def readStatement(bytes: ByteIterable): Statement = {
    // ignore dataset id [8]
    val entityID = bytes.subIterable(8, 8)
    val entity = lookupIdentifier(entityID)

    val attributeID = bytes.subIterable(16, 8)
    val attribute = lookupIdentifier(attributeID)

    val valueTypeId = ByteBinding.entryToByte(bytes.subIterable(24, 1))
    val valueContent = bytes.subIterable(25, 8)
    val value = constructValue(valueTypeId, valueContent)

    Statement(entity, attribute, value)
  }

  /** Returns all Statements in this Dataset. */
  override def allStatements(): Stream[IO, Statement] = Stream.emits {
    val output: ArrayBuffer[Statement] = ArrayBuffer()
    val eavStore = xodusOperations.openStore(tx, LigatureStore.EAVStore)
    val eavCursor = eavStore.openCursor(tx)
    var continue = eavCursor.getSearchKeyRange(datasetID) != null
    while (continue) {
      val statement = eavCursor.getKey
      if (datasetID == statement.subIterable(0, datasetID.getLength)) {
        output.append(readStatement(statement))
        continue = eavCursor.getNext
      } else {
        continue = false
      }
    }
    output
  }

  /** Returns all Statements that match the given criteria. If a
    * parameter is None then it matches all, so passing all Nones is the same as
    * calling allStatements.
    */
  override def matchStatements(
      entity: Option[Identifier],
      attribute: Option[Identifier],
      value: Option[Value]
  ): Stream[IO, Statement] = ???

  /** Returns all Statements that match the given criteria. If a
    * parameter is None then it matches all.
    */
  override def matchStatementsRange(
      entity: Option[Identifier],
      attribute: Option[Identifier],
      range: dev.ligature.Range
  ): Stream[IO, Statement] = ???
}

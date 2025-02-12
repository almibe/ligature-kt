/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander.lib

object x {}

// import dev.ligature.wander.LigatureValue
// import dev.ligature.wander.Field
// import dev.ligature.wander.HostAction
// import dev.ligature.wander.Tag
// import dev.ligature.wander.TaggedField
// import scala.util.boundary
// import dev.ligature.wander.WanderError

// val arrayModule: LigatureValue.Module = LigatureValue.Module(
//   Map(
//     Field("length") -> LigatureValue.Function(
//       HostAction(
//         "Get the number of elements in an Array.",
//         Seq(TaggedField(Field("array"), Tag.Untagged)),
//         Tag.Untagged,
//         (args, environment) =>
//           args match
//             case Seq(LigatureValue.Array(value)) =>
//               Right((LigatureValue.Int(value.length), environment))
//             case _ => ???
//       )
//     ),
//     Field("map") -> LigatureValue.Function(
//       HostAction(
//         "Map the values of an Array with the given function.",
//         Seq(TaggedField(Field("fn"), Tag.Untagged), TaggedField(Field("array"), Tag.Untagged)),
//         Tag.Untagged,
//         (args, environment) =>
//           args match
//             case Seq(LigatureValue.Function(fn), LigatureValue.Array(values)) =>
//               boundary:
//                 val results = values.map(value =>
//                   fn.call(Seq(value), environment) match
//                     case Left(_)      => ??? /// break(value)
//                     case Right(value) => value
//                 )
//                 Right((LigatureValue.Array(results), environment))
//             case _ => ???
//       )
//     ),
//     Field("filter") -> LigatureValue.Function(
//       HostAction(
//         "Filter an Array with the given predicate.",
//         Seq(TaggedField(Field("fn"), Tag.Untagged), TaggedField(Field("array"), Tag.Untagged)),
//         Tag.Untagged,
//         (args, environment) =>
//           args match
//             case Seq(LigatureValue.Function(fn), LigatureValue.Array(values)) =>
//               boundary:
//                 val results = values.filter(value =>
//                   fn.call(Seq(value), environment) match
//                     case Left(_) => ??? /// break(err)
//                     case Right(value) =>
//                       value match
//                         case LigatureValue.Bool(value) => value
//                         case _                       => ??? /// break(Left(LigatureError("")
//                 )
//                 Right((LigatureValue.Array(results), environment))
//             case _ => ???
//       )
//     ),
//     Field("first") -> LigatureValue.Function(
//       HostAction(
//         "Get the first element of an Array.",
//         Seq(TaggedField(Field("array"), Tag.Untagged)),
//         Tag.Untagged,
//         (args, environment) =>
//           args match
//             case Seq(LigatureValue.Array(value)) =>
//               if (value.length > 0) {
//                 Right((value.head, environment))
//               } else {
//                 Left(WanderError("Cannot call Array.head on empty array."))
//               }
//             case _ => ???
//       )
//     ),
//     Field("rest") -> LigatureValue.Function(
//       HostAction(
//         "Get a Array containing all elements except the first.",
//         Seq(TaggedField(Field("array"), Tag.Untagged)),
//         Tag.Untagged,
//         (args, environment) =>
//           args match
//             case Seq(LigatureValue.Array(value)) =>
//               Right((LigatureValue.Array(value.tail), environment))
//             case _ => ???
//       )
//     ),
//     Field("last") -> LigatureValue.Function(
//       HostAction(
//         "Get the last element of an Array.",
//         Seq(TaggedField(Field("array"), Tag.Untagged)),
//         Tag.Untagged,
//         (args, environment) =>
//           args match
//             case Seq(LigatureValue.Array(value)) =>
//               if (value.length > 0) {
//                 Right((value.last, environment))
//               } else {
//                 Left(WanderError("Cannot call Array.last on empty array."))
//               }
//             case _ => ???
//       )
//     ),
//     Field("cat") -> LigatureValue.Function(
//       HostAction(
//         "Concat all Strings in this Array.",
//         Seq(
//           TaggedField(Field("array"), Tag.Untagged)
//         ),
//         Tag.Untagged,
//         (args, environment) =>
//           args match
//             case Seq(LigatureValue.Array(value)) =>
//               Right(
//                 (
//                   LigatureValue.String(
//                     value
//                       .map(
//                         _ match
//                           case LigatureValue.String(value) => value
//                           case _                         => ???
//                       )
//                       .mkString("")
//                   ),
//                   environment
//                 )
//               ) // TODO make separator an arg
//             case _ => ???
//       )
//     ),
//     Field("join") -> LigatureValue.Function(
//       HostAction(
//         "Join this array.",
//         Seq(
//           TaggedField(Field("array"), Tag.Untagged)
//         ),
//         Tag.Untagged,
//         (args, environment) =>
//           args match
//             case Seq(LigatureValue.Array(value)) =>
//               Right(
//                 (
//                   LigatureValue.String(
//                     value
//                       .map(
//                         _ match
//                           case LigatureValue.String(value) => value
//                           case _                         => ???
//                       )
//                       .mkString("\n")
//                   ),
//                   environment
//                 )
//               ) // TODO make separator an arg
//             case _ => ???
//       )
//     ),
//     Field("foldLeft") -> LigatureValue.Function(
//       HostAction(
//         "Perform foldLeft on this array.",
//         Seq(
//           TaggedField(Field("initial"), Tag.Untagged),
//           TaggedField(Field("accumulator"), Tag.Untagged)
//         ),
//         Tag.Untagged,
//         (args, environment) =>
//           args match
//             case Seq(LigatureValue.Array(value)) =>
//               Right((LigatureValue.Array(value.tail), environment))
//             case _ => ???
//       )
//     )
//   )
// )

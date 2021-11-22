/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package dev.ligature.gaze

import munit.FunSuite

class TakeUntilSuite extends FunSuite {
    test("take until basic cases") {
        val nibbler = takeUntil('\n')
        val takeNewLine = takeString("\n")
        val gaze1 = Gaze.from("")
        val gaze2 = Gaze.from("\n")
        val gaze3 = Gaze.from("    \n   ")
        val gaze4 = Gaze.from("1234567\n")
        
        assertEquals(gaze1.attempt(nibbler), Right(""))
        assert(gaze1.isComplete())

        assertEquals(gaze2.attempt(nibbler), Right(""))
        assert(!gaze2.isComplete())
        assertEquals(gaze2.attempt(takeNewLine), Right("\n"))
        assert(gaze2.isComplete())

        assertEquals(gaze3.attempt(nibbler), Right("    "))

        assertEquals(gaze4.attempt(nibbler), Right("1234567"))
        assert(!gaze4.isComplete())
        assertEquals(gaze4.attempt(takeNewLine), Right("\n"))
        assert(gaze4.isComplete())        
    }
}

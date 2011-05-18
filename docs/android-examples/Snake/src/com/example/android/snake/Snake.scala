/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.snake

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.view.Window
import _root_.android.widget.TextView

object Snake {
  private val ICICLE_KEY = "snake-view"
}

/** Snake: a simple game that everyone can enjoy.
 * 
 *  This is an implementation of the classic Game "Snake", in which you control a
 *  serpent roaming around the garden looking for apples. Be careful, though,
 *  because when you catch one, not only will you become longer, but you'll move
 *  faster. Running into yourself or the walls will end the game.
 */
class Snake extends Activity {
  import SnakeView.Mode

  private var mSnakeView: SnakeView = null

  /** Called when Activity is first created. Turns off the title bar, sets up
   *  the content views, and fires up the SnakeView.
   */
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    // No Title bar
    requestWindowFeature(Window.FEATURE_NO_TITLE)

    setContentView(R.layout.snake_layout)

    mSnakeView = findViewById(R.id.snake).asInstanceOf[SnakeView]
    mSnakeView setTextView findViewById(R.id.text).asInstanceOf[TextView]

    if (savedInstanceState == null) {
      // We were just launched -- set up a new game
      mSnakeView setMode Mode.READY
    } else {
      // We are being restored
      val map = savedInstanceState getBundle Snake.ICICLE_KEY
      if (map != null) mSnakeView.restoreState(map)
      else mSnakeView setMode Mode.PAUSE
    }
  }

  override protected def onPause() {
    super.onPause()
    // Pause the game along with the activity
    mSnakeView setMode Mode.PAUSE 
  }

  override def onSaveInstanceState(outState: Bundle) {
    //Store the game state
    outState.putBundle(Snake.ICICLE_KEY, mSnakeView.saveState())
  }

}
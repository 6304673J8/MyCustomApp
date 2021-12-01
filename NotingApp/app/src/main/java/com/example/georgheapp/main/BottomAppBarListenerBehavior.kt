package com.example.georgheapp.main
import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomappbar.BottomAppBar

abstract class BottomAppBarListenerBehavior : BottomAppBar.Behavior() {
    private enum class State {
        SCROLL_DOWN, SCROLL_UP
    }

    private var currentState : State? = null

    abstract fun onSlideDown()
    abstract fun onSlideUp()

    override fun slideDown(child: BottomAppBar) {
        super.slideDown(child)
        if (currentState == State.SCROLL_DOWN) return
        currentState = State.SCROLL_DOWN
        onSlideDown()
    }

    override fun slideUp(child: BottomAppBar) {
        super.slideUp(child)
        if (currentState == State.SCROLL_UP) return
        currentState = State.SCROLL_UP
        onSlideUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val params = bottom_app_bar.layoutParams as CoordinatorLayout.LayoutParams
    }

}


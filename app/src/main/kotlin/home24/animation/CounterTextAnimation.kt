package home24.animation

import android.view.View
import android.widget.TextView

/**
 * Created by Sammy on 7/19/2018
 */
//Used to animate counter when we increase the number or decrease it
object CounterTextAnimation {
    fun animateTextUpDown(textView: TextView, text: String, goUp: Boolean = false) {
        var transitionTo = -30f
        if (!goUp) {
            transitionTo *= -1
        }

        textView.translationY = 0f
        textView.animate().setDuration(200).translationY(transitionTo).alpha(0f).withEndAction {
            textView.visibility = View.GONE
            textView.text = text
            textView.translationY = (-1 * transitionTo)
            textView.visibility = View.VISIBLE
            textView.animate().setDuration(200).translationY(0f).alpha(1f)
        }
    }


}
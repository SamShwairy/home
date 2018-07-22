package home24.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.sam.rijkmuseum.R
import home24.animation.BoundInterpolatorAnimation
import kotlinx.android.synthetic.main.fragment_home.*



/**
 * Created by sammy on 7/19/2018
 */
//Home Fragment has a view pager and adds the two fragments (EventFragment and Artwork Fragment)
class HomeFragment : Fragment() {
    //Callback and listener to interact with activity
    private var callback: FragmentActivity? = null
    private var listener: HomeFragmentListener? = null

    //Fragment new instance(in case we needed to add parameters later on)
    fun newInstance(): HomeFragment {
        return HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Initializing the animation
        val  myAnim = AnimationUtils.loadAnimation(context, R.anim.start_button_bounce_animation)
        val interpolator = BoundInterpolatorAnimation(0.2, 20.0)
        myAnim.interpolator = interpolator
        //Animation the button
        startButton.startAnimation(myAnim)
        startButton.setOnClickListener {
            //Firing the listener to go to the article fragment
            listener?.goToArticleFragment()
        }
    }
    //Attaching the callback to interact with the activity
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = activity
        listener = try {
            activity as HomeFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString()
                    + " must implement HomeFragmentListener")
        }
    }
    //Disposing the callback to avoid memory leaks
    override fun onDetach() {
        super.onDetach()
        callback = null
    }
    //Interface to go to the Article Fragment
    interface HomeFragmentListener {
        fun goToArticleFragment()
    }
}
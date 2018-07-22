package home24.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import com.jakewharton.rxbinding2.view.RxView
import com.sam.rijkmuseum.R
import home24.App
import home24.animation.CounterTextAnimation
import home24.config.Parameters
import home24.config.Parameters.API_DEFAULT_LIMIT
import home24.databases.entity.ArticleEntity
import home24.view.adapter.ArticleAdapter
import home24.viewmodel.data.ArticleList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import kotlinx.android.synthetic.main.fragment_article.*
import java.util.concurrent.TimeUnit


/**
 * Created by sammy on 3/3/2018
 */
//Artwork fragment subscribes to getting the Artwork
class ArticleFragment : MvvmFragment() {

    //Injecting the MVVM view model
    private val articleViewModel = App.injectArticleListViewModel()

    //Arraylist of the articles
    private var articleList: ArrayList<ArticleEntity>? = ArrayList()

    //Linear Layout manager -> declared on the top to get the adapter position from outside of the adapter
    private var linearLayoutManager: LinearLayoutManager? = null
    //Adapter of recycler view used to notify data set change
    private var articleAdapter: ArticleAdapter? = null

    //Total number of reviewed items
    private var totalNumberOfReviewedItems = 0
    //Current Item that is being reviewed
    private var currentItemPosition = 0

    //Callback to call the activity and the listener below it
    private var callback: FragmentActivity? = null
    private var listener: ArticleFragmentListener? = null

    //Declaring the animation at top, so it wont be created every single time
    private var animation: Animation? = null

    fun newInstance(): ArticleFragment {
        return ArticleFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onStart() {
        super.onStart()
        if (articleList == null || articleList?.isEmpty() == true) {
            //MVVM subscription -> subscribes -> calls api -> returns whether success or failure
            subscribe(articleViewModel?.getArticleList()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.repeatUntil(
                    { articleList?.isNotEmpty() == true })?.subscribe({
                showArtWork(it)
            }, {
            }))
        }
    }

    private fun showArtWork(data: ArticleList) {
        when {
            data.error == null -> {
                //Removing Loading
                loading.visibility = View.GONE
                //Assigning the list to the array
                articleList = data.elementEntity?.articleEntity?.articles
                //Initializing the linear layout manager
                linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                //RecyclerView adapter if the context is not null
                articleAdapter = context?.let { ArticleAdapter(articleList, totalNumberOfReviewedItems, it) }
                //Giving the recyclerview animation -> Sliding images
                recyclerView.itemAnimator = SlideInRightAnimator(OvershootInterpolator(Parameters.OVERSHOOT_INTERPOLATOR))
                //Assigning the adapter to the recyclerview
                recyclerView.adapter = articleAdapter

                //Snap Helper in order to force a single row per swipe/fling
                val snapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(recyclerView)
                //Assigning layout manager to recycler view
                recyclerView.layoutManager = linearLayoutManager
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Initializing the animation for the like and dislike buttons
        animation = AnimationUtils.loadAnimation(context, R.anim.like_dislike_rotate_animation)

        //Setting the counter text to 0/10
        setCounterText()


        //Rx bindings to debounce button clicks until animation and scrolling has finished
        RxView.clicks(like)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                {
                    //Animation begin
                    like.startAnimation(animation)
                    //Action of LIKE
                    likeDislike(Parameters.LIKE)
                })
        //Rx bindings to debounce button clicks until animation and scrolling has finished
        RxView.clicks(dislike)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            //Animation begin
                            dislike.startAnimation(animation)
                            //Action of Dislike
                            likeDislike(Parameters.DISLIKE)
                        })

        reviewButton.setOnClickListener {
            //Firing the interface to go to the review fragment
            listener?.goToReviewFragment(articleList)
        }
    }

    private fun setCounterText() {
        //If all items have been reviewed stop changing the counter number
        //Also adding animation while changing the number
        if (totalNumberOfReviewedItems <= API_DEFAULT_LIMIT)
            CounterTextAnimation.animateTextUpDown(counterTextView, StringBuilder((totalNumberOfReviewedItems).toString() + "/" + Parameters.API_DEFAULT_LIMIT.toString()).toString())
    }

    private fun likeDislike(@Parameters.ACTION action: Int) {
        //Getting the adapter position of the recycler view
        currentItemPosition = linearLayoutManager?.findFirstVisibleItemPosition() ?: 0
        //Setting whether an item has been liked or disliked
        articleList?.get(currentItemPosition)?.status = action
        //Incrementing the item at hand
        currentItemPosition++
        if (currentItemPosition > totalNumberOfReviewedItems) {
            //Incrementing the total number of items reviewed
            totalNumberOfReviewedItems++
            //Setting the text to the counter
            setCounterText()
        }
        //Informing the adapter to move to the next item
        articleAdapter?.setReviewedNumber(totalNumberOfReviewedItems, articleList)
        //Scrolling to the next item in the recycler view
        recyclerView?.scrollToPosition(currentItemPosition)

        //Show the review button when the user reached the 10 photos limit
        if (totalNumberOfReviewedItems == API_DEFAULT_LIMIT)
            reviewButton.visibility = View.VISIBLE


    }

    //Function used to set the current item position (after clicking on an article from the review)
    //This is being called from the main activity
    fun setReviewedItemPosition(position: Int) {
        currentItemPosition = position
        //Scrolling to the current article position
        recyclerView?.smoothScrollToPosition(currentItemPosition)
    }

    //Attaching the callback to the fragment
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = activity
        listener = try {
            activity as ArticleFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString()
                    + " must implement ArticleFragmentListener")
        }
    }

    //Disposing the callback to avoid memory leaks
    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    //Interface used to go to the review fragment
    interface ArticleFragmentListener {
        fun goToReviewFragment(articleList: ArrayList<ArticleEntity>?)
    }
}
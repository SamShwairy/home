package home24.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sam.rijkmuseum.R
import home24.databases.entity.ArticleEntity
import home24.view.fragment.ArticleFragment
import home24.view.fragment.HomeFragment
import home24.view.fragment.ListGridReviewFragment
import home24.view.fragment.ReviewFragment

//Main activity has one fragment the home fragment
class MainActivity : AppCompatActivity(), HomeFragment.HomeFragmentListener, ArticleFragment.ArticleFragmentListener,
        ListGridReviewFragment.ReviewFragmentListener {

    //Initiating the Home Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frag_container, HomeFragment().newInstance()).commit()
        }

    }

    //Interface for going to the articles Fragment from the Review fragment and setting the position of the item to be reviewed
    override fun goToArticleFragment(position: Int) {
        supportFragmentManager.popBackStack()
        (supportFragmentManager.findFragmentByTag(ArticleFragment::class.java.simpleName) as ArticleFragment)
                .setReviewedItemPosition(position)
    }
    //Interface for going to the review fragment from the article fragment
    override fun goToReviewFragment(articleList: ArrayList<ArticleEntity>?) {
        supportFragmentManager.beginTransaction()
                .add(R.id.frag_container, ReviewFragment().newInstance(articleList)).addToBackStack(ReviewFragment::class.java.simpleName).commit()
    }
    //Interface for going to the Article fragment when start is clicked on Home Fragment
    override fun goToArticleFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, ArticleFragment().newInstance(), ArticleFragment::class.java.simpleName).commit()
    }


}
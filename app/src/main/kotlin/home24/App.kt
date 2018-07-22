package home24

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.sam.rijkmuseum.R
import home24.repository.ArticleRepository
import home24.server.ApiUtils
import home24.server.RetrofitServices
import home24.viewmodel.ArticleListViewModel


/**
 * Created by sammy on 7/19/2018
 */

//Initial class that will initialize all the needed for the
class App : Application() {
    //The Ideal way is to add Dagger and inject the following items, however since this is only a task
    //and I do not want to include more third party libraries, this method has been taken
    companion object {

        private var retrofitService: RetrofitServices? = null
        private var artRepository: ArticleRepository? = null
        private var articleListViewModel: ArticleListViewModel? = null

        fun injectArticleListViewModel() = articleListViewModel
    }

    override fun onCreate() {
        super.onCreate()


        if (!isNetworkAvailable())
            Toast.makeText(this, getString(R.string.please_make_sure), Toast.LENGTH_SHORT).show()

        //Declaring the Retrofit Service
        retrofitService = ApiUtils.soService
        //Declaring the repository
        artRepository = ArticleRepository(retrofitService)
        //Declaring the View Model
        articleListViewModel = ArticleListViewModel(artRepository)
    }



//If network is not available then inform the user
private fun isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}
}
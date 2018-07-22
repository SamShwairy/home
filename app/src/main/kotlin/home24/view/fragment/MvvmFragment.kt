package home24.view.fragment

import android.support.v4.app.Fragment
import android.widget.Toast
import com.sam.rijkmuseum.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by sammy on 3/3/2018
 */

//MVVM Fragment inhertied by Article and Review Fragment in order to facilitate the subscribtions
open class MvvmFragment : Fragment() {

    private val subscriptions = CompositeDisposable()

    fun subscribe(disposable: Disposable?): Disposable? {
        subscriptions.add(disposable)
        return disposable
    }

    override fun onStop() {
        super.onStop()
        subscriptions.clear()
    }
    fun showToast(message: String = getString(R.string.please_make_sure)) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}
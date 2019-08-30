package app.delivery.ui.list

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.test.espresso.IdlingResource
import app.delivery.R
import app.delivery.constants.DataState
import app.delivery.databinding.ActivityListBinding
import app.delivery.ui.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel


class ListActivity : BaseActivity<ActivityListBinding, ListViewModel>() {
    val adapter: ListAdapter by inject()
    private var snackBar: Snackbar? = null

    override fun getModel(): ListViewModel {
        return getViewModel()
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_list
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        binding.rvList.adapter = adapter
        binding.rvList.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.srlData.setOnRefreshListener {
            modelData.resetData()
            binding.srlData.isRefreshing = false
            binding.llProgress.visibility = View.VISIBLE
        }
        modelData.checkAndInitialize()
        modelData.mResult?.data?.observe(this, Observer {
            adapter.submitList(it)
            if (!it.isNullOrEmpty()) {
                binding.llProgress.visibility = View.GONE
            }
        })
        modelData.mResult?.errorMessage?.observe(this, Observer {
            if (!TextUtils.isEmpty(it)) {
                if (TextUtils.equals(getString(R.string.check_connection), it))
                    showToastWithAction(it, getString(R.string.retry))
                else showToastWithAction(R.string.error_message, R.string.retry)
                Log.e(javaClass.simpleName, it)
            }
        })

        modelData.mResult?.dataState?.observe(this, Observer {
            if (adapter.itemCount > 0)
                adapter.setNetworkState(it)
            if (snackBar != null && snackBar is Snackbar && it == DataState.LOADED)
                snackBar?.dismiss()
            binding.llProgress.visibility =
                if (adapter.itemCount == 0 && it == DataState.LOADING) View.VISIBLE else View.GONE
            binding.llErrorScreen.visibility =
                if (adapter.itemCount == 0 && it == DataState.NETWORKERROR) View.VISIBLE else View.GONE
            binding.srlData.isEnabled = binding.llErrorScreen.visibility == View.GONE
        })
    }

    override fun showToastWithAction(message: String, retry: String) {
        if (snackBar != null && snackBar is Snackbar)
            snackBar?.dismiss()
        snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        if (snackBar != null && snackBar is Snackbar) {
            snackBar?.setAction(retry) { modelData.retry() }
            snackBar?.show()
        }
    }
}

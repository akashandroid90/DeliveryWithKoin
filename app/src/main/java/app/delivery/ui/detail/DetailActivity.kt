package app.delivery.ui.detail

import android.app.PictureInPictureParams
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Rational
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import app.delivery.R
import app.delivery.constants.AppConstant
import app.delivery.databinding.ActivityDetailBinding
import app.delivery.model.DeliveriesData
import app.delivery.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel


class DetailActivity :
    BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    private lateinit var deliveriesData: DeliveriesData

    override fun getModel(): DetailViewModel {
        return getViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deliveriesData = intent.getParcelableExtra(AppConstant.DATA)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        modelData.setDeliveryDataValue(deliveriesData)
        modelData.data.observe(this, Observer {
            if (it != null) {
                binding.deliveriesData = it
            }
        })
    }

    override fun showToastWithAction(message: String, retry: String) {

    }

    @VisibleForTesting
    fun testPIPMode() = enterPIPMode()

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        enterPIPMode()
    }

    private fun enterPIPMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && packageManager.hasSystemFeature(
                PackageManager.FEATURE_PICTURE_IN_PICTURE
            )
        ) {
            val builder = PictureInPictureParams.Builder()
            val aspectRatio = Rational(binding.map.width, binding.map.height)
            builder.setAspectRatio(aspectRatio).build()
            enterPictureInPictureMode(builder.build())
        }
    }
}

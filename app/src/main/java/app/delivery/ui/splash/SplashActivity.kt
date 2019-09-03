package app.delivery.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import app.delivery.repository.database.DbRepository
import app.delivery.ui.list.ListActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {
    private val dbRepository: DbRepository by inject()
    private val handler: Handler by inject()

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        dbRepository.deleteDeliveryData()
        handler.postDelayed({
            startActivity(Intent(this, ListActivity::class.java))
            finish()
        }, 2000)
    }
}
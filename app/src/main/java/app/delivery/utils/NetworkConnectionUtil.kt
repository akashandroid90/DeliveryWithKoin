package app.delivery.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * provides utils methods to application
 */
class NetworkConnectionUtil {
    /**
     * Checks if the Internet connection is available.
     *
     * @return Returns true if the Internet connection is available. False otherwise.
     */
    fun isInternetAvailable(ctx: Context): Boolean {
        // using received context (typically activity) to get SystemService causes memory link as this holds strong reference to that activity.
        // use application level context instead, which is available until the app dies.
        if (ctx.applicationContext != null) {
            val connectivityManager =
                ctx.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = connectivityManager.activeNetworkInfo

            // if network is NOT available networkInfo will be null
            // otherwise check if we are connected
            return networkInfo != null && networkInfo.isConnected
        }
        return false
    }
}

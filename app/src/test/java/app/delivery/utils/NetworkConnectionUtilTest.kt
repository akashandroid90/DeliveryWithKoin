package app.delivery.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class NetworkConnectionUtilTest {
    private lateinit var networkUtil: NetworkConnectionUtil
    @Before
    fun setUp() {
        networkUtil = NetworkConnectionUtil()
    }

    @Test
    fun isInternetAvailable_connected() {
        val context = Mockito.mock(Context::class.java)
        mockConnection(true, context)
        Assert.assertTrue(networkUtil.isInternetAvailable(context))
    }

    @Test
    fun isInternetAvailable_not_connected() {
        val context = Mockito.mock(Context::class.java)
        mockConnection(false, context)
        Assert.assertTrue(!networkUtil.isInternetAvailable(context))
    }

    @Test
    fun isInternetAvailable_info_not_available() {
        val context = Mockito.mock(Context::class.java)
        Mockito.`when`(context.applicationContext).thenReturn(null)
        Assert.assertTrue(!networkUtil.isInternetAvailable(context))
    }

    private fun mockConnection(value: Boolean, context: Context) {
        val appContext = Mockito.mock(Context::class.java)
        val connectionManager = Mockito.mock(ConnectivityManager::class.java)
        val networkInfo = Mockito.mock(NetworkInfo::class.java)
        Mockito.`when`(context.applicationContext).thenReturn(appContext)
        Mockito.doReturn(connectionManager).`when`(appContext)
            .getSystemService(Context.CONNECTIVITY_SERVICE)
        Mockito.`when`(connectionManager.activeNetworkInfo).thenReturn(networkInfo)
        Mockito.`when`(networkInfo.isConnected).thenReturn(value)
    }
}
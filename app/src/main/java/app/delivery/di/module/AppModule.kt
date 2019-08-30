package app.delivery.di.module

import android.os.Handler
import android.view.LayoutInflater
import app.delivery.model.ThreadModel
import app.delivery.ui.list.DeliveryBoundryCallBack
import app.delivery.ui.list.ListAdapter
import app.delivery.utils.NetworkConnectionUtil
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * provides application level instances
 */
val appModule = module {
    single { ThreadModel() }
    single { Handler() }
    single { NetworkConnectionUtil() }
    single { ListAdapter(get()) }
    single { DeliveryBoundryCallBack(get()) }
    single { LayoutInflater.from(androidContext()) }
}
package app.delivery.di.module

import app.delivery.ui.detail.DetailViewModel
import app.delivery.ui.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * provides view model and its factory instances
 */
val viewModelModule = module {
    viewModel { DetailViewModel() }
    viewModel { ListViewModel(get(), get(), get()) }
}
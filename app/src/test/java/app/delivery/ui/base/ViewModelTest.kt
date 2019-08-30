package app.delivery.ui.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import org.junit.Before
import org.junit.Rule

abstract class ViewModelTest<model : ViewModel> {
    protected lateinit var data: model
    protected abstract fun createViewModel(): model

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        data = createViewModel()
    }
}
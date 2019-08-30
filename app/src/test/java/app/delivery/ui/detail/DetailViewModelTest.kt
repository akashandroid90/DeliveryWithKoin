package app.delivery.ui.detail

import TestUtil
import app.delivery.ui.base.ViewModelTest
import org.junit.Assert
import org.junit.Test

class DetailViewModelTest : ViewModelTest<DetailViewModel>() {
    override fun createViewModel(): DetailViewModel {
        return DetailViewModel()
    }

    @Test
    fun testDetailViewModel() {
        val deliveryData = TestUtil.getRandomData()
        data.setDeliveryDataValue(deliveryData)

        Assert.assertEquals(data.data.value?.id, deliveryData.id)
        Assert.assertEquals(data.data.value?.description, deliveryData.description)
        Assert.assertEquals(data.data.value?.imageUrl, deliveryData.imageUrl)
        Assert.assertEquals(data.data.value?.location, deliveryData.location)
    }
}
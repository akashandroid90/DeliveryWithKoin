package app.delivery.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DeliveriesDataTest {
    private lateinit var data: DeliveriesData
    private lateinit var location: LocationData

    @Before
    fun setup() {
        data = DeliveriesData()
        data.id = 10
        data.description = "Deliver documents to Andrio"
        data.imageUrl =
            "https://s3-ap-southeast-1.amazonaws.com/lalamove-mock-api/images/pet-8.jpeg"

        location = LocationData()
        location.address = "Mong Kok"
        location.lat = 22.319181
        location.lng = 114.170008

        data.location = location
    }

    @Test
    fun testId() {
        Assert.assertEquals(data.id, 10)
    }

    @Test
    fun testDescription() {
        Assert.assertEquals(data.description, "Deliver documents to Andrio")
    }

    @Test
    fun testImageUrl() {
        Assert.assertEquals(
            data.imageUrl,
            "https://s3-ap-southeast-1.amazonaws.com/lalamove-mock-api/images/pet-8.jpeg"
        )
    }

    @Test
    fun testLocation() {
        Assert.assertEquals(data.location, location)
    }
}
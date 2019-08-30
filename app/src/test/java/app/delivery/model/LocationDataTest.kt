package app.delivery.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * class to hold location data corresponding to {@link Deliveries}
 */
class LocationDataTest {
    private lateinit var data: LocationData

    @Before
    fun setup() {
        data = LocationData()
        data.address = "Mong Kok"
        data.lat = 22.319181
        data.lng = 114.170008
    }

    @Test
    fun testLat() {
        Assert.assertEquals(data.lat, 22.319181, 0.toDouble())
    }

    @Test
    fun testLng() {
        Assert.assertEquals(data.lng, 114.170008, 0.toDouble())
    }

    @Test
    fun testAddress() {
        Assert.assertEquals(data.address, "Mong Kok")
    }
}
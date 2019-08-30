package app.delivery.utils

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import app.delivery.R
import app.delivery.model.DeliveriesData
import app.delivery.model.LocationData
import coil.api.load
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * provides methods to bind data to ui using data binding
 */
@BindingAdapter("imageUrl")
fun showImage(view: ImageView, url: String) {
    view.load(url) {
        placeholder(R.mipmap.ic_launcher)
    }
}

@BindingAdapter("deliveriesData")
fun locationData(view: TextView, location: DeliveriesData) {
    val builder: StringBuilder = StringBuilder()
    builder.append(location.description).append(" at ").append(location.location.address)
    view.text = builder
}

@BindingAdapter("locationData")
fun showmarkerOnmap(mapView: MapView, locationData: LocationData?) {
    mapView.onCreate(Bundle())
    mapView.getMapAsync { googleMap ->
        googleMap.clear()
        if (locationData != null) {
            val latlng = LatLng(locationData.lat, locationData.lng)
            googleMap.addMarker(MarkerOptions().position(latlng).title(locationData.address))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16f))
        }
        mapView.onResume()
    }
}
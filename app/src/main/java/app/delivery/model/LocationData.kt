package app.delivery.model

import android.os.Parcel
import android.os.Parcelable

/**
 * class to hold location data corresponding to {@link Deliveries}
 */
class LocationData : Comparable<LocationData>, Parcelable {
    var lat: Double = 0.0
    var lng: Double = 0.0
    lateinit var address: String

    constructor()
    constructor(source: Parcel) : this() {
        lat = source.readDouble()
        lng = source.readDouble()
        address = source.readString() as String
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        dest.writeDouble(lat)
        dest.writeDouble(lng)
        dest.writeString(address)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<LocationData> = object : Parcelable.Creator<LocationData> {
            override fun createFromParcel(source: Parcel): LocationData = LocationData(source)
            override fun newArray(size: Int): Array<LocationData?> = arrayOfNulls(size)
        }
    }

    override fun compareTo(other: LocationData): Int {
        return when {
            lat.compareTo(other.lat) != 0 -> 1
            lng.compareTo(other.lng) != 0 -> 1
            else -> address.compareTo(other.address)
        }
    }
}
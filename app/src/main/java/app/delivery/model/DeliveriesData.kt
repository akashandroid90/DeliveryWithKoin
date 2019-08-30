package app.delivery.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database table class to hold data
 */
@Entity(tableName = "delivery_table")
class DeliveriesData(@PrimaryKey var id: Int = 0) : Comparable<DeliveriesData>, Parcelable {

    var description: String = ""
    var imageUrl: String = ""
    @Embedded
    lateinit var location: LocationData

    constructor(source: Parcel) : this() {
        id = source.readInt()
        description = source.readString() as String
        imageUrl = source.readString() as String
        location = source.readParcelable(LocationData::class.java.classLoader) as LocationData
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        dest.writeInt(id)
        dest.writeString(description)
        dest.writeString(imageUrl)
        dest.writeParcelable(location, flags)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DeliveriesData> =
            object : Parcelable.Creator<DeliveriesData> {
                override fun createFromParcel(source: Parcel): DeliveriesData =
                    DeliveriesData(source)

                override fun newArray(size: Int): Array<DeliveriesData?> = arrayOfNulls(size)
            }
    }

    override fun compareTo(other: DeliveriesData): Int {
        return when {
            id != other.id -> 1
            description.compareTo(other.description) != 0 -> 1
            imageUrl.compareTo(other.imageUrl) != 0 -> 1
            else -> location.compareTo(other.location)
        }
    }
}
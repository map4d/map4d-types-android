package vn.map4d.types

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import vn.map4d.utils.Measure
import java.io.Serializable

@Parcelize
class MFLocationCoordinate(private var latitude: Double, private var longitude: Double): Parcelable, Serializable {
  init {
    this.latitude = Math.max(minLatitude, Math.min(maxLatitude, latitude))
    this.longitude = Math.max(minLongitude, Math.min(maxLongitude, longitude))
  }

  fun getLatitude(): Double {
    return this.latitude
  }

  fun getLongitude(): Double {
    return this.longitude
  }

  fun setLatitude(latitude: Double){
    this.latitude = Math.max(minLatitude, Math.min(maxLatitude, latitude))
  }

  fun setLongitude(longitude: Double) {
    this.longitude = Math.max(minLongitude, Math.min(maxLongitude, longitude))
  }

  fun distance(other: MFLocationCoordinate): Double {
    return Measure.distance(this, other)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || javaClass != other.javaClass) {
      return false
    }

    val coordinate = other as MFLocationCoordinate?
    coordinate?.let {
      return it.latitude.compareTo(latitude) == 0 && it.longitude.compareTo(longitude) == 0
    }
    return false
  }

  companion object {
    fun fromEncodedPolyline(polyline: String): List<MFLocationCoordinate> {
      val len = polyline.length

      // For speed we preallocate to an upper bound on the final length, then
      // truncate the array before returning.
      val path = ArrayList<MFLocationCoordinate>()
      var index = 0
      var lat = 0
      var lng = 0

      while (index < len) {
        var result = 1
        var shift = 0
        var b: Int
        do {
          b = polyline[index++].toInt() - 63 - 1
          result += b shl shift
          shift += 5
        } while (b >= 0x1f)
        lat += if (result and 1 != 0) (result shr 1).inv() else result shr 1

        result = 1
        shift = 0
        do {
          b = polyline[index++].toInt() - 63 - 1
          result += b shl shift
          shift += 5
        } while (b >= 0x1f)
        lng += if (result and 1 != 0) (result shr 1).inv() else result shr 1

        path.add(MFLocationCoordinate(lat * 1e-5, lng * 1e-5))
      }

      return path
    }

    val minLatitude = -85.051128779806604
    val maxLatitude = 85.051128779806604
    val minLongitude = -180.0
    val maxLongitude = 180.0
  }
}

fun List<MFLocationCoordinate>.area(): Double {
  return Measure.area(this)
}

fun Array<MFLocationCoordinate>.area(): Double {
  return Measure.area(this.toList())
}
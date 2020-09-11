package vn.map4d.types

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
import vn.map4d.utils.Measure
import java.io.Serializable
import kotlin.math.abs
import kotlin.math.atan

@Keep
@Parcelize
open class MFLocationCoordinate(private var latitude: Double, private var longitude: Double): Parcelable, Serializable {
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

  fun subtract(other: MFLocationCoordinate): MFLocationCoordinate {
    return MFLocationCoordinate(latitude - other.latitude, longitude - other.longitude)
  }

  fun multiply(mul: Double): MFLocationCoordinate {
    return MFLocationCoordinate(latitude * mul, longitude * mul)
  }

  fun angle(other: MFLocationCoordinate): Double {
    val dLat = other.latitude - latitude
    val dLng = other.longitude - longitude
    if (abs(dLat).compareTo(0) == 0) {
      if (dLng > 0.0) {
        return 90.0
      } else {
        return 270.0
      }
    }

    val tanA = dLng / dLat
    var a = atan(tanA)
    if (a > 0) {
      if (dLng < 0) {
        a += Math.PI
      }
    } else {
      if (dLng < 0) {
        a += 2*Math.PI
      } else {
        a += Math.PI
      }
    }
    return ((a / Math.PI * 180) + 360) % 360
  }

  companion object {
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
package vn.map4d.utils

import vn.map4d.types.MFLocationCoordinate

private object PolylineUtil {
  @JvmStatic
  fun decode(polyline: String): List<MFLocationCoordinate> {
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
}
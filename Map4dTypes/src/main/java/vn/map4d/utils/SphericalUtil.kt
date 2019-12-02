package vn.map4d.utils
import vn.map4d.types.MFLocationCoordinate

/**
 * Refer from https://github.com/NotWoods/spherical-geometry-js
 */

private object SphericalUtil {
  @JvmStatic
  fun distance(from: MFLocationCoordinate, to: MFLocationCoordinate): Double =
    computeDistanceBetweenHelper(from, to) * radiusEarthMeters

  @JvmStatic
  fun areaOf(path: List<MFLocationCoordinate>): Double =
    Math.abs(computeSignedArea(path))

  @JvmStatic
  val radiusEarthMeters = 6378137.0

  private fun toRad(degree: Double): Double {
    return (degree * Math.PI) / 180.0
  }

  private fun computeDistanceBetweenHelper(from: MFLocationCoordinate, to: MFLocationCoordinate): Double {
    val latFrom = toRad(from.getLatitude())
    val lngFrom = toRad(from.getLongitude())
    val latTo = toRad(to.getLatitude())
    val lngTo = toRad(to.getLongitude())
    return (
      2 *
        Math.asin(
          Math.sqrt(
            Math.pow(Math.sin((latFrom - latTo) / 2), 2.0) +
              Math.cos(latFrom) *
              Math.cos(latTo) *
              Math.pow(Math.sin((lngFrom - lngTo) / 2), 2.0)
          )
        )
      )
  }

  private fun sphericalExcess(a: MFLocationCoordinate, b: MFLocationCoordinate, c: MFLocationCoordinate): Double {
    val polygon = arrayListOf<MFLocationCoordinate>(a, b, c, a)
    val distances = doubleArrayOf(0.0, 0.0, 0.0)
    var sumOfDistances = 0.0

    for (i in 0..2) {
      distances[i] = computeDistanceBetweenHelper(polygon[i], polygon[i + 1])
      sumOfDistances += distances[i]
    }

    val semiPerimeter = sumOfDistances / 2.0

    var tan = Math.tan(semiPerimeter / 2.0)
    for (i in 0..2) {
      tan *= Math.tan((semiPerimeter - distances[i]) / 2)
    }
    return 4.0 * Math.atan(Math.sqrt(Math.abs(tan)))
  }

  private fun sphericalSign(a: MFLocationCoordinate, b: MFLocationCoordinate, c: MFLocationCoordinate): Double {
    var matrix = mutableListOf<Array<Double>>()
    arrayOf<MFLocationCoordinate>(a, b, c).mapTo(matrix, {
      val lat = toRad(it.getLatitude())
      val lng = toRad(it.getLongitude())
      arrayOf(Math.cos(lat) * Math.cos(lng),
        Math.cos(lat) * Math.sin(lng),
        Math.sin(lat))
    })
    val rr = (matrix[0][0] * matrix[1][1] * matrix[2][2] +
      matrix[1][0] * matrix[2][1] * matrix[0][2] +
      matrix[2][0] * matrix[0][1] * matrix[1][2] -
      matrix[0][0] * matrix[2][1] * matrix[1][2] -
      matrix[1][0] * matrix[0][1] * matrix[2][2] -
      matrix[2][0] * matrix[1][1] * matrix[0][2])
    return if (0.0 < rr) return 1.0 else return -1.0
  }

  private fun computeSphericalExcess(a: MFLocationCoordinate, b: MFLocationCoordinate, c: MFLocationCoordinate): Double {
    return sphericalExcess(a, b, c) * sphericalSign(a, b, c)
  }

  private fun computeSignedArea(path: List<MFLocationCoordinate>): Double {
    if (path.size < 3) {
      return 0.0
    }
    var totalArea = 0.0
    for (i in 1..path.size-2) {
      totalArea += computeSphericalExcess(path[0], path[i], path[i + 1])

    }
    return totalArea * radiusEarthMeters * radiusEarthMeters
  }
}

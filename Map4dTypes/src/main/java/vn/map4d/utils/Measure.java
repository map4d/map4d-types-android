package vn.map4d.utils;

import java.util.List;

import vn.map4d.types.MFLocationCoordinate;

public class Measure {
  public static double distance(MFLocationCoordinate from, MFLocationCoordinate to) {
    return SphericalUtil.distance(from, to);
  }

  public static double area(List<MFLocationCoordinate> path) {
    return SphericalUtil.areaOf(path);
  }
}

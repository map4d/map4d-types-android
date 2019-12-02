package vn.map4d.utils;

import java.util.List;

import vn.map4d.types.MFLocationCoordinate;

public class Polyline {
  public static List<MFLocationCoordinate> decode(String data) {
    return PolylineUtil.decode(data);
  }
}

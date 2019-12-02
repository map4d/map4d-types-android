package vn.map4d.typesdemo;

import org.junit.*;
import java.util.ArrayList;
import java.util.List;

import vn.map4d.types.MFLocationCoordinate;
import vn.map4d.utils.Measure;

public class TestCoordinate {
  @Test
  public void testEqual() {
    MFLocationCoordinate coordinate1 = new MFLocationCoordinate(10.7732011111101, 106.700147 );
    MFLocationCoordinate coordinate2 = new MFLocationCoordinate(10.77320111111, 106.700147 );
    Assert.assertTrue(coordinate1.equals(coordinate2));
  }

  @Test
  public void testArea() {
    MFLocationCoordinate coordinate1 = new MFLocationCoordinate(10.773201, 106.700147 );
    MFLocationCoordinate coordinate2 = new MFLocationCoordinate(10.771783, 106.700763 );
    MFLocationCoordinate coordinate3 = new MFLocationCoordinate(10.772302, 106.701901 );
    MFLocationCoordinate coordinate4 = new MFLocationCoordinate(10.773267, 106.701493 );
    List list = new ArrayList<MFLocationCoordinate>();
    list.add(coordinate1);
    list.add(coordinate2);
    list.add(coordinate3);
    list.add(coordinate4);
    Assert.assertTrue(Measure.area(list) - 19838.19500454577 < 0.0000001);
  }
}


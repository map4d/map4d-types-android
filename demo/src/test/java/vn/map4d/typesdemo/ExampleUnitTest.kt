package vn.map4d.typesdemo

import org.junit.Test

import org.junit.Assert.*
import vn.map4d.types.MFLocationCoordinate
import vn.map4d.types.area

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testAreaOfPath() {
        var coordinate1 = MFLocationCoordinate(10.773201, 106.700147 );
        var coordinate2 = MFLocationCoordinate(10.771783, 106.700763 );
        var coordinate3 = MFLocationCoordinate(10.772302, 106.701901 );
        var coordinate4 = MFLocationCoordinate(10.773267, 106.701493 );
        var list = arrayListOf<MFLocationCoordinate>(coordinate1, coordinate2, coordinate3, coordinate4)
        var area = list.area()
        assertTrue(area - 19838.19500454577 < 0.0000001)
    }
}

import org.example.Entity.BaiHat;
import org.example.Service.BaiHatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaiHatServiceTest {
    private BaiHatService baiHatService;

    @BeforeEach
    void setUp() {
        baiHatService = new BaiHatService();
    }

    @Test
    void testThemBaiHat_ThoiLuongBienDuoi() {
        BaiHat baiHat = new BaiHat("BH001", "Bài hát 1", "Ca sĩ A", 2.0f, "Pop");

        boolean result = baiHatService.themBaiHat(baiHat);

        assertTrue(result);
        assertEquals(1, baiHatService.soLuongBaiHat());
    }

    @Test
    void testThemBaiHat_ThoiLuongBienTren() {
        BaiHat baiHat = new BaiHat("BH002", "Bài hát 2", "Ca sĩ B", 5.99f, "Rock");

        boolean result = baiHatService.themBaiHat(baiHat);

        assertTrue(result);
        assertEquals(1, baiHatService.soLuongBaiHat());
    }

    @Test
    void testThemBaiHat_ThoiLuongGiuaPhanVung() {
        BaiHat baiHat = new BaiHat("BH003", "Bài hát 3", "Ca sĩ C", 3.5f, "Ballad");

        boolean result = baiHatService.themBaiHat(baiHat);

        assertTrue(result);
        assertEquals(1, baiHatService.soLuongBaiHat());
    }

    @Test
    void testThemBaiHat_ThoiLuongDuoiBienDuoi() {
        BaiHat baiHat = new BaiHat("BH004", "Bài hát 4", "Ca sĩ D", 1.99f, "EDM");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> baiHatService.themBaiHat(baiHat));

        assertEquals("Thời lượng phải trong khoảng 2:00 - 5:59 phút", exception.getMessage());
        assertEquals(0, baiHatService.soLuongBaiHat());
    }

    @Test
    void testThemBaiHat_ThoiLuongTrenBienTren() {
        BaiHat baiHat = new BaiHat("BH005", "Bài hát 5", "Ca sĩ E", 6.0f, "Jazz");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> baiHatService.themBaiHat(baiHat));

        assertEquals("Thời lượng phải trong khoảng 2:00 - 5:59 phút", exception.getMessage());
        assertEquals(0, baiHatService.soLuongBaiHat());
    }

    @Test
    void testThemBaiHat_MaRong() {
        BaiHat baiHat = new BaiHat("", "Bài hát 6", "Ca sĩ F", 3.0f, "R&B");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> baiHatService.themBaiHat(baiHat));

        assertEquals("Tất cả các trường không được để trống", exception.getMessage());
    }

    @Test
    void testThemBaiHat_TenRong() {
        BaiHat baiHat = new BaiHat("BH007", "", "Ca sĩ G", 3.0f, "Country");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> baiHatService.themBaiHat(baiHat));

        assertEquals("Tất cả các trường không được để trống", exception.getMessage());
    }

    @Test
    void testThemBaiHat_TenCaSiRong() {
        BaiHat baiHat = new BaiHat("BH008", "Bài hát 8", "", 3.0f, "Classical");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> baiHatService.themBaiHat(baiHat));

        assertEquals("Tất cả các trường không được để trống", exception.getMessage());
    }

    @Test
    void testThemBaiHat_TheLoaiRong() {
        BaiHat baiHat = new BaiHat("BH009", "Bài hát 9", "Ca sĩ I", 3.0f, "");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> baiHatService.themBaiHat(baiHat));

        assertEquals("Tất cả các trường không được để trống", exception.getMessage());
    }

    @Test
    void testThemBaiHat_MaTrung() {
        BaiHat baiHat1 = new BaiHat("BH010", "Bài hát 10", "Ca sĩ J", 4.0f, "Pop");
        BaiHat baiHat2 = new BaiHat("BH010", "Bài hát 11", "Ca sĩ K", 4.5f, "Rock");

        baiHatService.themBaiHat(baiHat1);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> baiHatService.themBaiHat(baiHat2));

        assertEquals("Mã bài hát đã tồn tại", exception.getMessage());
        assertEquals(1, baiHatService.soLuongBaiHat());
    }

    @Test
    void testThemBaiHat_ThoiLuongPhanVungThap() {
        BaiHat baiHat = new BaiHat("BH011", "Bài hát 11", "Ca sĩ L", 2.5f, "Hip Hop");

        boolean result = baiHatService.themBaiHat(baiHat);

        assertTrue(result);
        assertEquals(1, baiHatService.soLuongBaiHat());
    }

    @Test
    void testThemBaiHat_ThoiLuongPhanVungCao() {
        BaiHat baiHat = new BaiHat("BH012", "Bài hát 12", "Ca sĩ M", 5.5f, "Folk");

        boolean result = baiHatService.themBaiHat(baiHat);

        assertTrue(result);
        assertEquals(1, baiHatService.soLuongBaiHat());
    }
}

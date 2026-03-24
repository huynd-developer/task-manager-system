import org.example.Entity.SanPham;
import org.example.Service.SanPhamService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SanPhamServiceTest {
    private static SanPhamService sanPhamService;

    @BeforeAll
    public static void setUp(){
        sanPhamService = new SanPhamService();
    }

    @AfterAll
    public static void tearDown(){
        sanPhamService = null;
    }

    @Test
    public void themSanPhamNormal(){
        SanPham sp = new SanPham("SP001","Laptop Dell",2024,15000000,50,"Điện tử");
        sanPhamService.themSanPham(sp);
        assertEquals(1, sanPhamService.danhSachSanPham.size());
    }

    @Test
    public void themSanPhamSoLuongMin(){
        SanPham sp = new SanPham("SP002","Chuột máy tính",2024,250000,1,"Phụ kiện");
        sanPhamService.themSanPham(sp);
        assertEquals(1, sanPhamService.danhSachSanPham.get(0).getSoluong());
    }

    @Test
    public void themSanPhamSoLuongMax(){
        SanPham sp = new SanPham("SP003","Tai nghe",2024,500000,100,"Phụ kiện");
        sanPhamService.themSanPham(sp);
        assertEquals(100, sanPhamService.danhSachSanPham.get(0).getSoluong());
    }

    @Test
    public void themSanPhamSoLuongLessThan1(){
        SanPham sp = new SanPham("SP004","Bàn phím",2024,800000,0,"Phụ kiện");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sanPhamService.themSanPham(sp));
        assertEquals("Số lượng phải nằm trong phạm vi từ 1 - 100", exception.getMessage());
    }

    @Test
    public void themSanPhamSoLuongGreaterThan100(){
        SanPham sp = new SanPham("SP005","Màn hình",2024,3000000,101,"Điện tử");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sanPhamService.themSanPham(sp));
        assertEquals("Số lượng phải nằm trong phạm vi từ 1 - 100", exception.getMessage());
    }

    @Test
    public void themSanPhamMaTonTai(){
        SanPham sp1 = new SanPham("SP006","Laptop Asus",2024,20000000,10,"Điện tử");
        SanPham sp2 = new SanPham("SP006","Laptop HP",2024,18000000,5,"Điện tử");

        sanPhamService.themSanPham(sp1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> sanPhamService.themSanPham(sp2));
        assertEquals("Mã sản phẩm đã tồn tại: SP006", exception.getMessage());
    }

    @Test
    public void themSanPhamNull(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sanPhamService.themSanPham(null));
        assertEquals("Đối tượng sản phẩm không được rỗng", exception.getMessage());
    }

    @Test
    public void themSanPhamEmptyFields(){
        SanPham sp = new SanPham("","",2024,1000000,10,"");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sanPhamService.themSanPham(sp));
        assertEquals("Các thuộc tính không được để trống", exception.getMessage());
    }

    @Test
    public void themSanPhamGiaLessThan0(){
        SanPham sp = new SanPham("SP007","USB",2024,0,10,"Phụ kiện");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sanPhamService.themSanPham(sp));
        assertEquals("Giá phải lớn hơn 0", exception.getMessage());
    }

    @Test
    public void themSanPhamGiaBang0(){
        SanPham sp = new SanPham("SP008","Cáp sạc",2024,0,10,"Phụ kiện");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sanPhamService.themSanPham(sp));
        assertEquals("Giá phải lớn hơn 0", exception.getMessage());
    }
}
import Entity.Student;
import Service.StudentService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StudentServiceTest {
    protected static Student student;
    private static StudentService studentService;
    @BeforeAll
    public static void setUp(){
        studentService = new StudentService();
    }
    @AfterAll
    public static void tearDown(){
        studentService = null;
    }
    // Normal: Update thành công
    @Test
    public void testUpdateAvgMark(){
        // Stub dữ liệu sinh viên mới
        student = new Student(
                "TH04383","Nguyễn Đình Huy",18,7.5F,3,"CNTT"
        );
        studentService.updateStudent(student);
        assertEquals(7.5F,student.getAvgMark());
    }

    @Test
    public void testUpdateAvgMarkLessThan0(){
        // Stub dữ liệu sinh viên mới
        student = new Student(
                "TH043834","Nguyễn Đình Huy1",185,-1.0F,6,"CNTT"
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> studentService.updateStudent(student));
        assertEquals("Điểm phải nằm trong phạm vi từ 0 - 10", exception.getMessage());
    }

    @Test
    public void testUpdateAvgMarkGreaterThan10(){
        // Stub dữ liệu sinh viên mới
        student = new Student(
                "TH043834","Nguyễn Đình Huy1",185,11.0F,66,"CNTT"
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> studentService.updateStudent(student));
        assertEquals("Điểm phải nằm trong phạm vi từ 0 - 10",exception.getMessage());
    }
    @Test
    public void testUpdateAvgMarkEquals0(){
        // Stub dữ liệu sinh viên mới
        student = new Student(
                "TH04383","Nguyễn Đình Huy",18,0.0F,6,"CNTT"
        );
        studentService.updateStudent(student);
        assertEquals(0.0F,student.getAvgMark());
    }
    @Test
    public void testUpdateAvgMarkEquals10(){
        // Stub dữ liệu sinh viên mới
        student = new Student(
                "TH04383","Nguyễn Đình Huy",18,10.0F,6,"CNTT"
        );
        studentService.updateStudent(student);
        assertEquals(10.0F,student.getAvgMark());
    }
}

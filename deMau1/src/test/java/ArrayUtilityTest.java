import org.example.Utility.ArrayUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArrayUtilityTest {
//    // Kiểm tra kết quả chính xác (Normal)
//    @Test
//    public void sumOddNumbersNormalTest() {
//        assertEquals(250000, ArrayUtility.sumOddNumbers());
//    }
//
//    // Kiểm tra giá trị biên
//    @Test
//    public void sumOddNumbersBoundaryUpperTest() {
//        long expected = 500L * 500L; // n² với n=500 (số lượng số lẻ từ 1-1000)
//        assertEquals(expected, ArrayUtility.sumOddNumbers());
//    }
//
//    // Kiểm tra theo công thức tổng số lẻ
//
//    @Test
//    public void sumOddNumbersMatchesFormulaTest() {
//        int n = 500; // Số lượng số lẻ từ 1-1000
//        long expected = (long) n * n;
//        assertEquals(expected, ArrayUtility.sumOddNumbers());
//    }
//
//    // Kiểm tra tính ổn định của hàm
//    @Test
//    public void sumOddNumbersConsistencyTest() {
//        long result1 = ArrayUtility.sumOddNumbers();
//        long result2 = ArrayUtility.sumOddNumbers();
//        assertEquals(result1, result2);
//    }
//
//    // Kiểm tra giá trị không bị tràn
//    @Test
//    public void sumOddNumbersDoesNotOverFlowTest() {
//        long result1 = ArrayUtility.sumOddNumbers();
//        assertTrue(result1 < Long.MAX_VALUE, "Giá trị không vượt quá phạm vi của kiểu long");
//    }
    @Test
    public void sum0ddNumbersNormal(){
        assertEquals(250000,ArrayUtility.sumOddNumbers());
    }
    @Test
    public void sum0ddNumberBoundaryUpperTest(){
        long expected = 500L * 500L;
        assertEquals(expected,ArrayUtility.sumOddNumbers());
    }
    @Test
    public void sum0ddNumberMatchesFormularTest(){
        int n = 500;
        long expected = (long) n * n;
        assertEquals(expected,ArrayUtility.sumOddNumbers());
    }
    @Test
    public void sum0ddNumberConsistencyTest(){
        long result1 = ArrayUtility.sumOddNumbers();
        long result2 = ArrayUtility.sumOddNumbers();
        assertEquals(result1,result2);
    }
    @Test
    public void sum0ddNumberDoesNotOverFlowTest(){
        long result =  ArrayUtility.sumOddNumbers();
        assertTrue(result < Long.MAX_VALUE, "Giá trị không được vượt quá kiểu giá trị Long");
    }
}

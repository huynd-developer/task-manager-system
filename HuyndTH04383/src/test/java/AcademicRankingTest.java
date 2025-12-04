import org.example.AcademicRanking.AcademicRanking;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AcademicRankingTest {
    @Test
    void testExcellent_BoundaryValue9() {
        assertEquals("Xuất sắc", AcademicRanking.evaluateAcademicPerformance(9.0));
    }

    @Test
    void testExcellent_EquivalencePartition() {
        assertEquals("Xuất sắc", AcademicRanking.evaluateAcademicPerformance(9.5));
        assertEquals("Xuất sắc", AcademicRanking.evaluateAcademicPerformance(10.0));
    }

    @Test
    void testGood_BoundaryValues() {
        assertEquals("Giỏi", AcademicRanking.evaluateAcademicPerformance(7.0));
        assertEquals("Giỏi", AcademicRanking.evaluateAcademicPerformance(8.99));
    }

    @Test
    void testGood_EquivalencePartition() {
        assertEquals("Giỏi", AcademicRanking.evaluateAcademicPerformance(7.5));
        assertEquals("Giỏi", AcademicRanking.evaluateAcademicPerformance(8.0));
    }

    @Test
    void testAverage_BoundaryValues() {
        assertEquals("Trung bình", AcademicRanking.evaluateAcademicPerformance(5.0));
        assertEquals("Trung bình", AcademicRanking.evaluateAcademicPerformance(6.99));
    }

    @Test
    void testAverage_EquivalencePartition() {
        assertEquals("Trung bình", AcademicRanking.evaluateAcademicPerformance(5.5));
        assertEquals("Trung bình", AcademicRanking.evaluateAcademicPerformance(6.0));
    }

    @Test
    void testWeak_BoundaryValue() {
        assertEquals("Yếu", AcademicRanking.evaluateAcademicPerformance(4.99));
    }

    @Test
    void testWeak_EquivalencePartition() {
        assertEquals("Yếu", AcademicRanking.evaluateAcademicPerformance(0.0));
        assertEquals("Yếu", AcademicRanking.evaluateAcademicPerformance(2.5));
        assertEquals("Yếu", AcademicRanking.evaluateAcademicPerformance(4.5));
    }

    @Test
    void testInvalidScore_Negative() {
        assertThrows(IllegalArgumentException.class,
                () -> AcademicRanking.evaluateAcademicPerformance(-1.0));
    }

    @Test
    void testInvalidScore_AboveMax() {
        assertThrows(IllegalArgumentException.class,
                () -> AcademicRanking.evaluateAcademicPerformance(10.1));
    }
}

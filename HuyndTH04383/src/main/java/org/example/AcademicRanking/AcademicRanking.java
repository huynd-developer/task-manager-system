package org.example.AcademicRanking;

public class AcademicRanking {
    public static String evaluateAcademicPerformance(double averageScore) {
        if (averageScore < 0 || averageScore > 10) {
            throw new IllegalArgumentException("Điểm phải nằm trong khoảng từ 0 đến 10");
        }

        if (averageScore >= 9.0) {
            return "Xuất sắc";
        } else if (averageScore >= 7.0) {
            return "Giỏi";
        } else if (averageScore >= 5.0) {
            return "Trung bình";
        } else {
            return "Yếu";
        }
    }
}

package Utility;

public class ArrayUtility {
    /**
     * Tính tổng các số nguyên từ 1 đến 100
     */
    public static long sumArray(){
        long total = 0;
        for (int i = 0; i <= 1000; i++) {
            total += i;
        }
        return total;
    }
    public static long sumOddNumbers() {
        long sum = 0;
        for (int i = 1; i <= 1000; i++) {
            if (i % 2 != 0) {
                sum += i;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(sumOddNumbers());
    }
}

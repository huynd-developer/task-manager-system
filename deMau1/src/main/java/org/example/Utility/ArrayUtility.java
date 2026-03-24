package org.example.Utility;

public class ArrayUtility {
    public static long sumOddNumbers(){
        int total = 0;
        for (int i = 1; i <= 1000; i++) {
            if (i % 2 != 0)
                total += i;
        }
        return total;
    }

}

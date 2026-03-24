package org.example.testhttpurlconnection.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
* Tiện ích dữ liệu
 */
public class StreamUtils {
    // Sử dụng byteArrayOutputStream để ghi dữ liệu tạm thời

    public static String readStream(InputStream in) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Đọc 1 lần 4kb
        byte[] buffer = new byte[4*1024];
        // Đọc dữ liệu cho đến khi hết
        while (true){
            int n = in.read(buffer);// Doc du lieu vao buffer
            if(n < 0){
                break; // Dừng vòng lặp
            }
            outputStream.write(buffer,0,n); // Ghi dữ liệu vào buffer
        }
        return outputStream.toString();
    }
}

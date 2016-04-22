package com.tocong.mymobilesafe.chatper01.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tocong on 2016/4/21.
 */
public class StreamUtils {

    public static String readFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, len);


        }

        String result = out.toString();
        inputStream.close();
        ;
        out.close();
        return result;

    }


}

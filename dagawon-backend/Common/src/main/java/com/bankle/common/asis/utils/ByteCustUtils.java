package kr.co.anbu.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class ByteCustUtils extends StringUtils {

    public static byte[] HexStringToByteArray(String hexString) {

        int    len  = hexString.length();
        byte[] data = new byte[len / 2];
        
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ( (Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i+1), 16) );
        }
        return data;
    }
}

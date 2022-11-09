package com.jsdc.itss.utils;

import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * ClassName: FileUtils
 * Description:
 * date: 2022/1/18 15:52
 *
 * @author wp
 */
public class FileUtils {

    public static InputStream BaseToInputStream(String base64string){
        ByteArrayInputStream stream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64string);
            stream = new ByteArrayInputStream(bytes1);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return stream;
    }

    /**
     * 文件转byte[]
     * @param file
     * @return
     */
    public static byte[] fileToByte(File file){
        byte[] bytes = null;
        FileInputStream inputStream = null;
        try{
            inputStream = new FileInputStream(file);
            bytes = new byte[(int) file.length()];
            inputStream.read(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return bytes;
        }finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                return bytes;
            }
        }
        return bytes;
    }
}

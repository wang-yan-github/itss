package com.jsdc.itss.common.utils;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
  public static String getMD5(String content) {
    String result = "";
    try {
      MessageDigest md = MessageDigest.getInstance("md5");
      md.update(content.getBytes());
      byte[] bytes = md.digest();
      StringBuilder sb = new StringBuilder();
      for (byte b : bytes) {
        String str = Integer.toHexString(b & 0xFF);
        if (str.length() == 1) {
          sb.append("0");
        }
        sb.append(str);
      }
      result = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Test
  public void testMd5() {
    System.out.println(getMD5("123456"));
  }
}

package com.solidarychain.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpUtils {
  public static void request() {
    try {
      URL url = new URL("https://reqbin.com/echo/post/json");
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod("POST");
      http.setDoOutput(true);
      http.setRequestProperty("Accept", "application/json");
      http.setRequestProperty("Authorization", "Bearer {token}");
      http.setRequestProperty("Content-Type", "application/json");
      String data = "{\n	\"employee\":{ \"name\":\"Emma\", \"age\":28, \"city\":\"Boston\" }\n} ";
      byte[] out = data.getBytes(StandardCharsets.UTF_8);
      OutputStream stream = http.getOutputStream();
      stream.write(out);
      System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
      http.disconnect();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
    }
  }
}

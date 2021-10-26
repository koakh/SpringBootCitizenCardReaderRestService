package com.solidarychain.citizencardreaderapi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solidarychain.citizencardreaderapi.dto.SignInResponse;

public class HttpUtils {
  /**
   * helper to disable disable https verification on host require trust-store.jks
   * and System.setProperty("javax.net.ssl.trustStore", "trust-store.jks");
   * System.setProperty("javax.net.ssl.trustStorePassword", "TrustStore"); in
   * Application
   * 
   * @param host
   */
  public static void disableHttpsVerification(String host) {
    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {
      public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
        return hostname.equals(host);
      }
    });
  }

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
    }
  }

  public static void graphql(String fqdn, String serverUi) {
    // disable https verification on host
    disableHttpsVerification(fqdn);

    String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlNTBlYmE5YS1mZmZhLTQ1MTYtYWYwMS05ZmRjZWExNjRmZmMiLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImlhdCI6MTYzNDUxMDg5N30.xk5mNnIYUioKrI90OWKWzEIYTzwNQZFa8IbyuIpG-rI";
    String email = "pete@mail.com";
    String password = "password";
    try {
      URL url = new URL(serverUi);
      // HttpURLConnection http = (HttpURLConnection) url.openConnection();
      // enable self digned certificate
      HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);
      conn.setRequestProperty("Accept", "application/json");
      conn.setRequestProperty("Authorization", String.format("Bearer {token}", accessToken));
      conn.setRequestProperty("Content-Type", "application/json");
      String data = String.format(
          "{\"query\":\"mutation SignInMutation($signUpEmail: String!, $signUpPassword: String!) { signIn(email: $signUpEmail, password: $signUpPassword)}\",\"variables\":{\"signUpEmail\":\"admin@admin.com\",\"signUpPassword\":\"password\" } }",
          email, password);
      byte[] out = data.getBytes(StandardCharsets.UTF_8);
      OutputStream stream = conn.getOutputStream();
      stream.write(out);
      System.out.println(conn.getResponseCode() + " " + conn.getResponseMessage());
      // read the response from input stream
      try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
          response.append(responseLine.trim());
        }
        // System.out.println(response.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = response.toString();
        SignInResponse signInResponse = objectMapper.readValue(json, SignInResponse.class);
        System.out.println(String.format("accessToken '%s'", signInResponse.toString()));
      }
      conn.disconnect();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

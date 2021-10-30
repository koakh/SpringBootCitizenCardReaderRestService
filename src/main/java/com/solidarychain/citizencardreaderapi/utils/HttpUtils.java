package com.solidarychain.citizencardreaderapi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solidarychain.citizencardreaderapi.dto.SignInResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

public class HttpUtils {
  private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

  private HttpUtils() {
  }

  /**
   * helper to disable disable https verification on host require trust-store.jks
   * and System.setProperty("javax.net.ssl.trustStore", "trust-store.jks");
   * System.setProperty("javax.net.ssl.trustStorePassword", "TrustStore"); in
   * Application
   * 
   * @param host
   */
  public static void disableHttpsVerification(String host) {
    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> hostname.equals(host));
  }

  /**
   * simple http test request
   */
  public static void request() {
    try {
      URL url = new URL("https://reqbin.com/echo/post/json");
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod("POST");
      httpConn.setDoOutput(true);
      httpConn.setRequestProperty("Authorization", "Bearer {token}");
      httpConn.setRequestProperty("Accept", MediaType.APPLICATION_JSON.toString());
      httpConn.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON.toString());
      String data = "{\n	\"employee\":{ \"name\":\"Emma\", \"age\":28, \"city\":\"Boston\" }\n} ";
      byte[] out = data.getBytes(StandardCharsets.UTF_8);
      OutputStream stream = httpConn.getOutputStream();
      stream.write(out);
      logger.debug(String.format("{} : {}", httpConn.getResponseCode(), httpConn.getResponseMessage()));
      httpConn.disconnect();
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
      // use HttpURLConnection type for non https connection
      // enable self digned certificate
      HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
      httpsConn.setRequestMethod("POST");
      httpsConn.setDoOutput(true);
      httpsConn.setRequestProperty("Accept", MediaType.APPLICATION_JSON.toString());
      httpsConn.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON.toString());
      httpsConn.setRequestProperty("Authorization", String.format("Bearer {token}", accessToken));
      String data = String.format(
          "{\"query\":\"mutation SignInMutation($signUpEmail: String!, $signUpPassword: String!) { signIn(email: $signUpEmail, password: $signUpPassword)}\",\"variables\":{\"signUpEmail\":\"%s\",\"signUpPassword\":\"%s\" } }",
          email, password);
      byte[] out = data.getBytes(StandardCharsets.UTF_8);
      OutputStream stream = httpsConn.getOutputStream();
      stream.write(out);
      logger.debug(String.format("{} : {}", httpsConn.getResponseCode(), httpsConn.getResponseMessage()));
      // read the response from input stream
      try (BufferedReader br = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(), StandardCharsets.UTF_8))) {
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
          response.append(responseLine.trim());
        }
        // logger.debug(response.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = response.toString();
        SignInResponse signInResponse = objectMapper.readValue(json, SignInResponse.class);
        logger.debug(String.format("accessToken '%s'", signInResponse.toString()));
      }
      httpsConn.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

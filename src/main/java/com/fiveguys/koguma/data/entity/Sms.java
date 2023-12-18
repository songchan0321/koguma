package com.fiveguys.koguma.data.entity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Sms {

    public static void main(String[] args) throws Exception {
        // API Key, Secret, 그리고 요청 URL
        String apiKey = "NCSAYU7YDBXYORXC";
        String apiSecret = "RSTMDDE09K5VS2CL8JXHSM0KSUXJWS7I"; // 실제로 사용할 때는 보안을 고려하여 API Secret을 안전하게 다루어야 합니다.
        String url = "https://api.coolsms.co.kr/messages/v4/list";

        // Date, Salt, Signature 생성
        String date = "2019-07-01T00:41:48Z";
        String salt = "jqsba2jxjnrjor";
        String signature = generateSignature(apiKey, apiSecret, date, salt);

        // HTTP 요청 보내기
        sendRequest(url, apiKey, date, salt, signature);
    }

    private static String generateSignature(String apiKey, String apiSecret, String date, String salt)
            throws NoSuchAlgorithmException {
        String message = apiKey + date + salt;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(message.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    private static void sendRequest(String url, String apiKey, String date, String salt, String signature)
            throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        // Authorization 헤더 설정
        String authorizationHeader = "HMAC-SHA256 apiKey=" + apiKey +
                ", date=" + date +
                ", salt=" + salt +
                ", signature=" + signature;
        connection.setRequestProperty("Authorization", authorizationHeader);

        // 응답 읽기
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        System.out.println("Response: " + response.toString());
    }
}

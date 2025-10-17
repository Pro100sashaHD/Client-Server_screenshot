package com.example.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class ScreenshotClient {

    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private final String serverUrl;
    private final String authHeader;

    public ScreenshotClient(String ipAddress, String username, String password) {
        this.serverUrl = "http://" + ipAddress + ":8080/api/screenshot";

        String credentials = username + ":" + password;
        this.authHeader = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    public byte[] getScreenshot() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl))
                .header("Authorization", authHeader)
                .GET()
                .build();

        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() == 200) {
            return response.body();
        } else if (response.statusCode() == 401) {
            throw new SecurityException("Ошибка авторизации. Проверьте логин/пароль.");
        } else {
            throw new Exception("Ошибка сервера. Код: " + response.statusCode());
        }
    }
}

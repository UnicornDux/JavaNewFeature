package com.edu.jdk.v11;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CompletionStage;

/**
 * jdk 中提供了 webclient, 用于快速构建网络请求
 *
 */
public class WebClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        // Get 请求
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://codefather.cn"))
                .header("Content-Type", "application/json")
                .header("User-Agent", "Mozilla/5.0")
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();

        // POST 请求
        var jsonData = """
{"name": "alex"}""";
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.example.com/api"))
                .header("Content-Type", "application/json")
                .header("User-Agent", "Mozilla/5.0")
                .timeout(Duration.ofSeconds(30))
                .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                .build();


        // 处理响应
        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
        System.out.println(response.headers().map());


        // 异步请求
        client.sendAsync(postRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);


        // 自定义处理逻辑
        client.send(getRequest, new HttpResponse.BodyHandler<String>() {
            @Override
            public HttpResponse.BodySubscriber<String> apply(HttpResponse.ResponseInfo responseInfo) {
                if (responseInfo.statusCode() == 200) {
                    return HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);
                } else {
                    return HttpResponse.BodySubscribers.replacing(null);
                }
            }
        });

        // webSocket 支持
        HttpClient.newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create("ws://localhost:8080/websocket"), new WebSocket.Listener() {
                    @Override
                    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                        System.out.println("Received: " + data);
                        return WebSocket.Listener.super.onText(webSocket, data, last);
                    }

                    @Override
                    public void onOpen(WebSocket webSocket) {
                        System.out.println("WebSocket opened");
                        WebSocket.Listener.super.onOpen(webSocket);
                    }
                }).join();
    }
}

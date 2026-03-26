package server;

import com.google.gson.Gson;
import exception.ResponseException;
import model.GameRequest;
import model.GameResult;
import model.UserRequest;
import model.UserResult;

import java.net.http.HttpClient;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public UserResult register(UserRequest requestClient) throws ResponseException {
        var request = buildRequest("POST", "/user", requestClient, null);
        var response = sendRequest(request);
        return handleResponse(response, UserResult.class);
    }

    public UserResult login(UserRequest requestClient) throws ResponseException {
        var request = buildRequest("POST", "/session", requestClient, null);
        var response = sendRequest(request);
        return handleResponse(response, UserResult.class);
    }

    public void logout(String authToken) throws ResponseException {
        var request = buildRequest("DELETE", "/session", null, authToken);
        var response = sendRequest(request);
        handleResponse(response, null);
    }

    public void createGame(GameRequest gameName, String authToken) throws ResponseException {
        var request = buildRequest("POST", "/game", gameName, authToken);
        var response = sendRequest(request);
        handleResponse(response, GameResult.class);
    }

    public GameResult listGame(String authToken) throws ResponseException {
        var request = buildRequest("GET", "/game", null, authToken);
        var response = sendRequest(request);
        return handleResponse(response, GameResult.class);
    }

    public void joinGame() {

    }

    public void observeGame() {

    }

    private HttpRequest buildRequest(String method, String path, Object body, String authToken) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        }

        if (authToken != null) {
            request.setHeader("authorization", authToken);
        }

        return request.build();
    }

    private BodyPublisher makeRequestBody(Object request) {
        if (request != null) {
            return BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws ResponseException {
        try {
            return client.send(request, BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new ResponseException(ex.getMessage());
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws ResponseException {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                throw ResponseException.fromJson(body);
            }

            throw new ResponseException("other failure: " + status);
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}

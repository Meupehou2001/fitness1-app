package com.uli.fitnessapp.desktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Einfacher HTTP-Client für das Spring-Boot-Backend (gleiche API wie der React-Client)
public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public ArrayNode getWorkouts(long userId) throws Exception {
        return get(BASE_URL + "/workouts/" + userId);
    }

    public ArrayNode getProgression(long userId, long exerciseId) throws Exception {
        return get(BASE_URL + "/stats/" + userId + "/progression?exerciseId=" + exerciseId);
    }

    public ArrayNode getExercises() throws Exception {
        return get(BASE_URL + "/exercises");
    }

    private ArrayNode get(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return (ArrayNode) mapper.readTree(response.body());
    }
}

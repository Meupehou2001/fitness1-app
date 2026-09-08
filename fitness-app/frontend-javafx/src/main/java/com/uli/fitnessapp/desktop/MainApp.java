package com.uli.fitnessapp.desktop;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

// Desktop-Client (JavaFX): nutzt dieselbe REST-API wie der React-Client.
public class MainApp extends Application {

    private static final long USER_ID = 1; // durch den eingeloggten Benutzer ersetzen, sobald Auth hinzugefügt ist
    private static final long DEFAULT_EXERCISE_ID = 1;

    private final ApiClient api = new ApiClient();

    @Override
    public void start(Stage stage) {
        ListView<String> workoutList = new ListView<>();
        LineChart<Number, Number> chart = buildEmptyChart();

        VBox root = new VBox(10,
                new Label("Letzte Trainingseinheiten"), workoutList,
                new Label("Fortschritt"), chart);
        root.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(root, 600, 500));
        stage.setTitle("Fitness-App — Desktop");
        stage.show();

        loadData(workoutList, chart);
    }

    private LineChart<Number, Number> buildEmptyChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Trainingseinheit #");
        yAxis.setLabel("Max. Gewicht (kg)");
        return new LineChart<>(xAxis, yAxis);
    }

    private void loadData(ListView<String> workoutList, LineChart<Number, Number> chart) {
        // Netzwerkaufrufe außerhalb des JavaFX-Threads, um die UI nicht zu blockieren
        new Thread(() -> {
            try {
                JsonNode workouts = api.getWorkouts(USER_ID);
                List<String> lines = new ArrayList<>();
                for (JsonNode w : workouts) {
                    lines.add(w.get("date").asText() + " — " + w.get("dureeMin").asText() + " Min.");
                }

                JsonNode progression = api.getProgression(USER_ID, DEFAULT_EXERCISE_ID);
                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                series.setName("Max. Gewicht");
                int i = 1;
                for (JsonNode p : progression) {
                    series.getData().add(new XYChart.Data<>(i++, p.get("poidsMax").asDouble()));
                }

                Platform.runLater(() -> {
                    workoutList.setItems(FXCollections.observableArrayList(lines));
                    chart.getData().add(series);
                });
            } catch (Exception e) {
                Platform.runLater(() -> workoutList.setItems(
                        FXCollections.observableArrayList("Fehler: Backend nicht erreichbar (" + e.getMessage() + ")")));
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

Button average = new Button("Compute mean distance");
average.setOnAction(e -> {
    Alert meanDistance = new Alert(Alert.AlertType.INFORMATION, "Mean Distance: " + averageDistance);
    meanDistance.showAndWait().filter(response -> response == ButtonType.OK);
});
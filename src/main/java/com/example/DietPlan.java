package com.example;

import com.google.gson.Gson;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DietPlan {
    private ObservableList<Food> selectedFoods = FXCollections.observableArrayList();
    private VBox foodListVBox;
    private VBox selectedFoodVBox;
    private Label totalNutritionLabel;
    private Timeline searchTimer;
    private ProgressIndicator progressIndicator; // Loading indicator

    public void showDiet(Stage stage) {
        TextField foodInput = new TextField();
        foodInput.setPromptText("Search food...");
        foodInput.setPrefWidth(300);

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #6c5ce7; -fx-text-fill: white;");

        Button backButton = new Button("🔙 Back");
        backButton.setStyle("-fx-background-color: #00b894; -fx-text-fill: white;");
        
        //creating object before use method 
        Main obj = new Main();
        backButton.setOnAction(e -> obj.showHomePage(stage));

        HBox searchBox = new HBox(10, foodInput, searchButton);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(20));

        
        backButton.setFont(Font.font("Arial", 16));

// Set the position to bottom-left
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        StackPane.setMargin(backButton, new Insets(20));


        foodListVBox = new VBox(10);
        foodListVBox.setPadding(new Insets(10));

        // Create a loading progress indicator
        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false); // Initially hidden

        ScrollPane foodScrollPane = createScrollPane(foodListVBox);
        VBox searchResultsSection = createLabelSection("Search Results", foodScrollPane);

        selectedFoodVBox = new VBox(10);
        selectedFoodVBox.setPadding(new Insets(10));

        ScrollPane selectedFoodScrollPane = createScrollPane(selectedFoodVBox);
        VBox selectedFoodSection = createLabelSection("Selected Foods", selectedFoodScrollPane);

        totalNutritionLabel = new Label();
        totalNutritionLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button showNutrientsButton = new Button("Show Total Nutrients");
        showNutrientsButton.setStyle("-fx-background-color: #00b894; -fx-text-fill: white;");
        showNutrientsButton.setOnAction(e -> updateTotalNutrition());

        VBox fixedBottom = new VBox(10, showNutrientsButton, totalNutritionLabel);
        fixedBottom.setAlignment(Pos.CENTER);
        fixedBottom.setPadding(new Insets(10));

        VBox selectedSection = new VBox();
        selectedSection.setAlignment(Pos.TOP_CENTER);
        selectedSection.getChildren().addAll(selectedFoodSection, fixedBottom);
        selectedSection.setPrefHeight(420);
        VBox.setVgrow(selectedFoodSection, Priority.ALWAYS);
        VBox.setVgrow(selectedFoodScrollPane, Priority.ALWAYS);

        searchResultsSection.setPrefHeight(420);
        VBox.setVgrow(searchResultsSection, Priority.ALWAYS);
        VBox.setVgrow(foodScrollPane, Priority.ALWAYS);

        HBox contentArea = new HBox(30, searchResultsSection, selectedSection);
        contentArea.setAlignment(Pos.TOP_CENTER);
        contentArea.setPadding(new Insets(10));
        contentArea.setPrefHeight(420);
        HBox.setHgrow(searchResultsSection, Priority.ALWAYS);
        HBox.setHgrow(selectedSection, Priority.ALWAYS);

        VBox root1 = new VBox(20, searchBox, contentArea, progressIndicator);
        root1.setPadding(new Insets(20));
        root1.setBackground(new Background(new BackgroundFill(Color.web("#dfe6e9"), CornerRadii.EMPTY, Insets.EMPTY)));
        root1.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(contentArea, Priority.ALWAYS);
        
        StackPane root = new StackPane(root1, backButton);

        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Nutrition Tracker - Diet Plan");
        stage.setScene(scene);
        stage.show();

        // Debounce search with delay
        foodInput.textProperty().addListener((obs, oldText, newText) -> {
            if (searchTimer != null && searchTimer.getStatus() == Timeline.Status.RUNNING) {
                searchTimer.stop(); // Stop previous search if typing continues
            }

            searchTimer = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                if (newText.length() >= 3) {
                    searchAndDisplayFood(newText.trim());
                } else {
                    foodListVBox.getChildren().clear();
                }
            }));

            searchTimer.play();
        });
    }

    private ScrollPane createScrollPane(VBox content) {
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: white; -fx-border-color: lightgray;");
        scrollPane.setPrefSize(350, 400);
        scrollPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        return scrollPane;
    }

    private VBox createLabelSection(String title, ScrollPane content) {
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2d3436;");

        VBox box = new VBox(10, titleLabel, content);
        box.setPadding(new Insets(10));
        VBox.setVgrow(content, Priority.ALWAYS);
        box.setStyle("-fx-background-color: white;");
        box.setPrefWidth(400);
        box.setPrefHeight(400);
        box.setMinHeight(350);
        box.setMaxHeight(450);

        return box;
    }

    private void searchAndDisplayFood(String foodName) {
        // Show the loading indicator
        progressIndicator.setVisible(true);
        

        // Perform the API request in the background using a Task
        new Thread(() -> {
            String json = USDAApiCall.searchFood(foodName);
            Gson gson = new Gson();
            FoodResponse response = gson.fromJson(json, FoodResponse.class);

            // Update the UI on the JavaFX Application Thread
            javafx.application.Platform.runLater(() -> {
                foodListVBox.getChildren().clear();
                progressIndicator.setVisible(false); // Hide the loading indicator once data is loaded

                if (response != null && response.getFoods() != null && !response.getFoods().isEmpty()) {
                    for (Food food : response.getFoods()) {
                        VBox card = createFoodCard(food);
                        foodListVBox.getChildren().add(card);
                    }
                } else {
                    foodListVBox.getChildren().add(new Label("No food found!"));
                }
            });
        }).start(); // Start the background thread
    }

    private VBox createFoodCard(Food food) {
        Label titleLabel = new Label(food.getDescription());
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    
        // Show quantity 
        Label quantityLabel = new Label("Quantity: " + 
            (food.getHouseholdServingFullText() != null ? food.getHouseholdServingFullText() : "N/A"));
    
        VBox nutrientsBox = new VBox(5);
        if (food.getFoodNutrients() != null) {
            for (Nutrient nutrient : food.getFoodNutrients()) {
                String name = nutrient.getNutrientName();
                if (name.equalsIgnoreCase("Energy") ||
                    name.equalsIgnoreCase("Protein") ||
                    name.equalsIgnoreCase("Total lipid (fat)") ||
                    name.equalsIgnoreCase("Vitamin C, total ascorbic acid")) {
                    Label nutrientLabel = new Label(name + ": " + nutrient.getValue() + " " + nutrient.getUnitName());
                    nutrientsBox.getChildren().add(nutrientLabel);
                }
            }
        }
    
        Button addButton = new Button("➕ Add");
        addButton.setStyle("-fx-background-color: #00b894; -fx-text-fill: white;");
        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            selectedFoods.add(food);
            playAddAnimation(addButton);
            updateSelectedFoods();
        });
    
        VBox card = new VBox(10, titleLabel, quantityLabel, nutrientsBox, addButton);
        card.setPadding(new Insets(10));
        card.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        card.setEffect(new DropShadow(5, Color.GRAY));
        return card;
    }
    
    private void updateSelectedFoods() {
        selectedFoodVBox.getChildren().clear();
        for (Food food : selectedFoods) {
            VBox foodBox = new VBox(5);
            Label foodLabel = new Label(food.getDescription());
            Button removeButton = new Button("Remove");
            removeButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            removeButton.setOnAction(e -> {
                selectedFoods.remove(food);
                updateSelectedFoods();
            });
            foodBox.getChildren().addAll(foodLabel, removeButton);
            selectedFoodVBox.getChildren().add(foodBox);
        }
    }

    private void updateTotalNutrition() {
        double totalCalories = 0, totalProtein = 0, totalFat = 0, totalVitaminC = 0;

        for (Food food : selectedFoods) {
            if (food.getFoodNutrients() != null) {
                for (Nutrient nutrient : food.getFoodNutrients()) {
                    switch (nutrient.getNutrientName()) {
                        case "Energy" -> totalCalories += nutrient.getValue();
                        case "Protein" -> totalProtein += nutrient.getValue();
                        case "Total lipid (fat)" -> totalFat += nutrient.getValue();
                        case "Vitamin C, total ascorbic acid" -> totalVitaminC += nutrient.getValue();
                    }
                }
            }
        }

        totalNutritionLabel.setText(
            "Daily Intake:\n" +
            "Calories: " + String.format("%.2f", totalCalories) + " kcal\n" +
            "Protein: " + String.format("%.2f", totalProtein) + " g\n" +
            "Fat: " + String.format("%.2f", totalFat) + " g\n" +
            "Vitamin C: " + String.format("%.2f", totalVitaminC) + " mg"
        );
    }

    private void playAddAnimation(Button button) {
        ScaleTransition st = new ScaleTransition(Duration.millis(300), button);
        st.setByX(0.2);
        st.setByY(0.2);
        st.setAutoReverse(true);
        st.setCycleCount(2);

        FadeTransition ft = new FadeTransition(Duration.millis(600), button);
        ft.setFromValue(1.0);
        ft.setToValue(0.5);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);

        st.play();
        ft.play();
    }
}

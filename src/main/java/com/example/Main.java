package com.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private ImageView exerciseGifview; 
    private HashMap<String, String> exerciseGifs = new HashMap<>();



    private final List<String> beginnerExercises = Arrays.asList("Jumping Jacks", "pushUps", "squat", "Cobra Stretches");
    private final List<String> intermediateExercises = Arrays.asList("Squats", "Cross over crunches", "military pushups", "Mountain Climbers");
    private final List<String> advancedExercises = Arrays.asList("military pushups", "Diamond Pushups", "Jumping Squats", "pushUps");

    private int currentExerciseIndex = 0;
    private Text exerciseText;
    

    @Override
    public void start(Stage primaryStage) {
        LoginSignup loginSignup = new LoginSignup();
        loginSignup.showLogin(primaryStage, this);
    }


    
    public void showHomePage(Stage stage) {
        Text title = new Text("🏋️‍♂️ My Fitness App");
        title.setFont(Font.font("Verdana", 36));
        title.setFill(Color.web("#2d3436"));
    
        Button exerciseBtn = createLevelButton("🏋️ Exercise", "white", 250, "/images/exe.jpg");
        Button bmiBtn = createLevelButton("⚖️ BMI Calculator", "white", 250, "/images/sbmi.jpg");
        Button dietBtn = createLevelButton("🥗 Nutrition Tracker", "white", 250, "/images/dt.jpg");
        Button creditsBtn = createStyledButton("💡 Credits");
        Button quitBtn = createStyledButton("❌ Quit");
    
        exerciseBtn.setOnAction(e -> showLevelSelection(stage));
        bmiBtn.setOnAction(e -> showBMICalculator(stage));
        DietPlan dietPlan = new DietPlan();   // Class ka object banao
        dietBtn.setOnAction(e -> dietPlan.showDiet(stage));  // Fir us object se method call karo

        creditsBtn.setOnAction(e -> System.out.println("Credits clicked"));
        quitBtn.setOnAction(e -> stage.close());
    
        // Sidebar setup
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #2C2F33;");
        sidebar.setPrefWidth(200);
        sidebar.setMaxWidth(200);
        sidebar.setMinWidth(200);
        sidebar.setAlignment(Pos.TOP_CENTER);
    
        Button profileBtn = createSidebarButton("👤 Profile");
        Button settingsBtn = createSidebarButton("⚙️ Settings");
        Button logoutBtn = createSidebarButton("🚪 Logout");
    
        sidebar.getChildren().addAll(profileBtn, settingsBtn, logoutBtn);
    
        // Sidebar animation
        sidebar.setTranslateX(-200); // Initially hidden
        TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.3), sidebar);
        slideIn.setToX(0); // Slide into view
    
        TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.3), sidebar);
        slideOut.setToX(-200); // Slide out of view
    
        // Button to toggle the sidebar
        Button menuButton = new Button("☰");
        menuButton.setFont(Font.font(20));
        menuButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
    
        HBox topBar = new HBox(menuButton);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: transparent;");
        topBar.setAlignment(Pos.CENTER_LEFT);
    
        // Layout for the buttons
        HBox buttonBox = new HBox(15, exerciseBtn, bmiBtn, dietBtn);
        buttonBox.setAlignment(Pos.CENTER);
    
        HBox bt1 = new HBox(15, creditsBtn, quitBtn);
        bt1.setAlignment(Pos.CENTER);
    
        VBox mainLayout = new VBox(50, title, buttonBox, bt1);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40));
    
        BorderPane root = new BorderPane(mainLayout);
        root.setBackground(getGradientBackground());
        root.setTop(topBar);
    
        // Logic for sidebar toggle
        menuButton.setOnAction(e -> {
            if (root.getLeft() == null) {
                root.setLeft(sidebar);
                slideIn.play(); // Play animation to slide in
            } else {
                slideOut.play(); // Play animation to slide out
                slideOut.setOnFinished(event -> root.setLeft(null)); // Remove sidebar after animation
            }
        });
    
        Scene scene = new Scene(root, 1080, 700);
        stage.setScene(scene);
        stage.setTitle("Welcome - Fitness App");
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        scene.getRoot().requestFocus();
        stage.show();
    }
    
    

    private void showLevelSelection(Stage stage) {
        Text welcomeText = new Text("🏋️ Choose Your Level");
        welcomeText.setFont(Font.font("Verdana", 34));
        welcomeText.setFill(Color.web("#ffffff"));

        Button beginnerBtn = createLevelButton("BEGINNER", "#1e90ff", 250,"/images/beg.jpg");
        Button intermediateBtn = createLevelButton("INTERMEDIATE", "#ff4500", 250,"/images/intermediate1.png");
        Button advancedBtn = createLevelButton("ADVANCED", "#ff0000", 250,"/images/advance.png");
        Button backBtn = createStyledButton("🔙 Back");

        beginnerBtn.setOnAction(e -> showExerciseScreen(stage, "Beginner", beginnerExercises));
        intermediateBtn.setOnAction(e -> showExerciseScreen(stage, "Intermediate", intermediateExercises));
        advancedBtn.setOnAction(e -> showExerciseScreen(stage, "Advanced", advancedExercises));
        backBtn.setOnAction(e -> showHomePage(stage));

        VBox welcomeBox = new VBox(20, welcomeText);
        welcomeBox.setAlignment(Pos.TOP_CENTER); // Align it to the top center
        welcomeBox.setStyle("-fx-padding: 20");
        HBox centerBox = new HBox(35, beginnerBtn, intermediateBtn, advancedBtn);
        centerBox.setAlignment(Pos.CENTER);

        VBox leftBox = new VBox(backBtn);
        leftBox.setAlignment(Pos.TOP_LEFT);
        leftBox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setCenter(centerBox);
        root.setLeft(leftBox);
        root.setPadding(new Insets(40));
        root.setBackground(getGradientBackground());

        Scene scene = new Scene(root, 1080, 900);
        stage.setScene(scene);
        stage.setTitle("Select Level");
        stage.setResizable(false);
        stage.show();
    }
    

    private void showExerciseScreen(Stage stage, String level, List<String> exercises) {
        currentExerciseIndex = 0;

        initializeExerciseGifs(level); // Initialize GIF paths

        Text levelText = new Text(level + " Level");
        levelText.setFont(Font.font("Verdana", 32));
        levelText.setFill(Color.web("black"));

        exerciseText = new Text("Exercise: " + exercises.get(currentExerciseIndex));
        exerciseText.setFont(Font.font("Arial", 24));
        exerciseText.setFill(Color.web("black"));

        // Initialize ImageView
        exerciseGifview = new ImageView();
        exerciseGifview.setFitWidth(300);
        exerciseGifview.setPreserveRatio(true);
        updateGif(exercises.get(currentExerciseIndex));

        

        Button nextBtn = createStyledButton("Next ▶");
        Button skipBtn = createStyledButton("Skip ⏭");
        Button backBtn = createStyledButton("← Back");
        Button quitBtn = createStyledButton("❌ Quit");

        HBox buttonBox = new HBox(20, nextBtn, skipBtn);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);

        VBox centerBox = new VBox(20, levelText, exerciseText, exerciseGifview, buttonBox);
        centerBox.setAlignment(Pos.CENTER);

        VBox rightBottomBox = new VBox(15, backBtn, quitBtn);
        rightBottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        rightBottomBox.setPadding(new Insets(0, 40, 40, 0));

        BorderPane layout = new BorderPane();
        layout.setCenter(centerBox);
        layout.setRight(rightBottomBox);

        nextBtn.setOnAction(e -> {
            currentExerciseIndex = (currentExerciseIndex + 1) % exercises.size();
            if(currentExerciseIndex==0){
                showCongratsMessage(stage);
            } else{
                updateGif(exercises.get(currentExerciseIndex));
            exerciseText.setText("Exercise: " + exercises.get(currentExerciseIndex));
            }
        });

        skipBtn.setOnAction(e -> {
            currentExerciseIndex = (currentExerciseIndex + 1) % exercises.size();
            updateGif(exercises.get(currentExerciseIndex));
            exerciseText.setText("Exercise: " + exercises.get(currentExerciseIndex));
        });

        backBtn.setOnAction(e -> showLevelSelection(stage));
        quitBtn.setOnAction(e -> stage.close());

        Scene scene = new Scene(layout, 1080,900 );
        stage.setScene(scene);
        stage.setTitle(level + " Exercises");
        stage.setResizable(false);
        stage.show();
    }

    private void initializeExerciseGifs(String level) {
        exerciseGifs.clear(); // Clear old values

        if (level.equals("Beginner")) {
            exerciseGifs.put("Jumping Jacks", "/gifs/jumpingjacks.gif");
            exerciseGifs.put("pushUps", "/gifs/pushUps.gif");
            exerciseGifs.put("squat", "/gifs/squats.gif");
            exerciseGifs.put("Cobra Stretches", "/gifs/cobra_stretch.gif");

        } else if (level.equals("Intermediate")) {
            exerciseGifs.put("Squats", "/gifs/squats.gif");
            exerciseGifs.put("Cross over crunches", "/gifs/cross_over_crunches.gif");
            exerciseGifs.put("military pushups", "/gifs/military_pushups.gif");
            exerciseGifs.put("Mountain Climbers", "/gifs/mountain_climbers.gif");

        } else if (level.equals("Advanced")) {
            exerciseGifs.put("military pushups", "/gifs/military_pushups.gif");
            exerciseGifs.put("Diamond Pushups", "/gifs/diamond_pushups.gif");
            exerciseGifs.put("Jumping Squats", "/gifs/jumping_squats.gif");
            exerciseGifs.put("pushUps", "/gifs/pushups.gif");
        }
    }

    private void updateGif(String exercise) {
        String gifPath = exerciseGifs.get(exercise);

        if (gifPath != null) {
            java.net.URL gifUrl = getClass().getResource(gifPath);
            if (gifUrl != null) {
                exerciseGifview.setImage(new Image(gifUrl.toExternalForm()));
                System.out.println("Loaded GIF successfully: " + gifPath);
            } else {
                System.out.println("Error: GIF not found at " + gifPath);
            }
        } else {
            System.out.println("No GIF path found for exercise: " + exercise);
        }
    }


    private void showCongratsMessage(Stage stage) {
        // Display "Congrats" message
        Text text = new Text("🎉🎉Congratulations! \nYou completed ☠☠");
        text.setFont(Font.font("Verdana", 32));
        text.setFill(Color.web("black"));

        // Back to Menu button
        Button backBtn = new Button("Back to Menu");
        backBtn.setFont(Font.font("Arial", 18));
        backBtn.setOnAction(e -> showHomePage(stage));
    
        // Create VBox for centering text and button
        VBox centerBox = new VBox(20, text, backBtn);
        centerBox.setAlignment(Pos.CENTER);

        // Set VBox in BorderPane center
        BorderPane layout = new BorderPane(centerBox);
        layout.setBackground(getGradientBackground());

        // Create and show scene
        Scene scene = new Scene(layout, 900, 650);
        stage.setScene(scene);
        stage.setTitle("🏆 Outstanding");
        stage.setResizable(false);
        stage.show();
    }


    private Background getGradientBackground() {
        return new Background(new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#8e9eab")),
                        new Stop(1, Color.web("#eef2f3"))),
                CornerRadii.EMPTY, Insets.EMPTY));
    }

    private Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("""
            -fx-font-size: 17px;
            -fx-background-color: linear-gradient(to right, #ffffff, #dff9fb);
            -fx-text-fill: #2d3436;
            -fx-background-radius: 12;
            -fx-border-radius: 12;
            -fx-padding: 10 25;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0.3, 0, 1);
        """);
        return btn;
    }
    

    //
public void showBMICalculator(Stage stage) {
    // Labels and Fields
    Text title = new Text("⚖️ BMI Calculator");
    title.setFont(Font.font("Verdana", 36));
    title.setFill(Color.web("#ffffff"));

    // Weight Label & Input
    Label weightLabel = new Label("Weight (kg):");
    weightLabel.setFont(Font.font("Arial", 16));
    weightLabel.setTextFill(Color.web("#ffffff"));
    TextField weightField = createStyledTextField();
    weightField.setPromptText("Enter weight in kg");

    // Height Label & Input
    Label heightLabel = new Label("Height (m):");
    heightLabel.setFont(Font.font("Arial", 16));
    heightLabel.setTextFill(Color.web("#ffffff"));
    TextField heightField = createStyledTextField();
    heightField.setPromptText("Enter height in meters");

    // Calculate Button
    Button calculateBtn = createStyledButton("Calculate BMI");

    // BMI Result Text
    Text resultText = new Text("");
    resultText.setFont(Font.font("Arial", 20));
    resultText.setFill(Color.web("#ffffff"));

    // Reset Button
    Button resetBtn = createStyledButton("Reset");
    Button backBtn = createStyledButton("back<--");
    backBtn.setFont(Font.font("Arial", 18));
    backBtn.setOnAction(e -> showHomePage(stage));

    


    // Calculate Button Action
    calculateBtn.setOnAction(e -> {
        try {
            double weight = Double.parseDouble(weightField.getText()); // here we converting string to double
            double height = Double.parseDouble(heightField.getText()); // here we converting string to double
            if (weight > 0 && height > 0) {
                double bmi = weight / (height * height);
                String category = getBMICategory(bmi);
                resultText.setText(String.format("Your BMI: %.2f\nCategory: %s", bmi, category));
            } else {
                resultText.setText("Invalid input. Please enter positive values.");
            }
        } catch (NumberFormatException ex) {
            resultText.setText("Invalid input. Please enter numbers.");
        }
    });

    // Reset Button Action
    resetBtn.setOnAction(e -> {
        weightField.clear();
        heightField.clear();
        resultText.setText("");
    });

    // Layout Setup
    VBox inputBox = new VBox(15, weightLabel, weightField, heightLabel, heightField, calculateBtn, resultText, resetBtn,backBtn);
    inputBox.setAlignment(Pos.CENTER);
    inputBox.setPadding(new Insets(20));

    BorderPane root = new BorderPane();
    root.setCenter(inputBox);
    root.setBackground(getGradientBackground());  // Use the same exercise background

    Scene scene = new Scene(root, 900, 650);
    stage.setScene(scene);
    stage.setTitle("BMI Calculator");
    stage.setResizable(false);
    stage.show();
}

// Helper Method for BMI Categories
private String getBMICategory(double bmi) {
    if (bmi < 18.5) {
        return "Underweight";
    } else if (bmi >= 18.5 && bmi < 24.9) {
        return "Normal weight";
    } else if (bmi >= 25 && bmi < 29.9) {
        return "Overweight";
    } else {
        return "Obesity";
    }
}


private Button createLevelButton(String text, String color, int width, String imagePath) {
    // Create the Image for the background of the button
    Image image = new Image(imagePath);  // Each button can have a different image
    String imageUrl = getClass().getResource(imagePath).toExternalForm();  // Ensure proper path resolution

    // Create the button with text
    Button btn = new Button(text);

    // Style the button with a background image and text
    btn.setStyle(String.format("""
        -fx-font-size: 20px;
        -fx-font-weight: bold;
        -fx-text-fill: #ffffff;  /* White color */
        -fx-background-color: linear-gradient(to right, %s, #1c1c1c);
        -fx-background-image: url('%s');
        -fx-background-size: cover;  /* Makes the image cover the whole button */
        -fx-background-position: center;
        -fx-background-repeat: no-repeat;
        -fx-background-radius: 12;
        -fx-border-radius: 12;
        -fx-padding: 20 35;
        -fx-min-width: %dpx;
        -fx-min-height: 150px;
        -fx-max-height: 150px;
        -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0.5, 0, 0);
        -fx-alignment: bottom-right; /* Align the text at the bottom-right */
    """, color, imageUrl, width));

    // Handle mouse events for the button
    btn.setOnMouseEntered(e -> btn.setStyle(String.format("""
        -fx-font-size: 20px;
        -fx-font-weight: bold;
        -fx-text-fill: #ffffff;  /* White color */
        -fx-background-color: linear-gradient(to right, %s, #3e3e3e);
        -fx-background-image: url('%s');
        -fx-background-size: cover;
        -fx-background-position: center;
        -fx-background-repeat: no-repeat;
        -fx-background-radius: 12;
        -fx-border-radius: 12;
        -fx-padding: 20 35;
        -fx-min-width: %dpx;
        -fx-min-height: 180px;
        -fx-max-height: 180px;
        -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 12, 0.7, 0, 0);
        -fx-alignment: bottom-right; /* Keep the text at the bottom-right */
    """, color, imageUrl, width)));

    btn.setOnMouseExited(e -> btn.setStyle(String.format("""
        -fx-font-size: 20px;
        -fx-font-weight: bold;
        -fx-text-fill: #ffffff;  /* White color */
        -fx-background-color: linear-gradient(to right, %s, #1c1c1c);
        -fx-background-image: url('%s');
        -fx-background-size: cover;
        -fx-background-position: center;
        -fx-background-repeat: no-repeat;
        -fx-background-radius: 12;
        -fx-border-radius: 12;
        -fx-padding: 20 35;
        -fx-min-width: %dpx;
        -fx-min-height: 150px;
        -fx-max-height: 150px;
        -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0.5, 0, 0);
        -fx-alignment: bottom-right; /* Keep the text at the bottom-right */
    """, color, imageUrl, width)));

    // Return the button
    return btn;
}
private void styleButton(Button button) {
    button.setStyle(
        "-fx-background-color: transparent; " +
        "-fx-text-fill: white; " +
        "-fx-font-size: 16px;"
    );

    button.setOnMouseEntered(e -> button.setStyle(
        "-fx-background-color: #7289DA; " +  // light blue on hover
        "-fx-text-fill: white; " +
        "-fx-font-size: 16px;"
    ));
    
    button.setOnMouseExited(e -> button.setStyle(
        "-fx-background-color: transparent; " +
        "-fx-text-fill: white; " +
        "-fx-font-size: 16px;"
    ));
}
private static Button createSidebarButton(String text) {
    Button button = new Button(text);
    button.setStyle(
        "-fx-background-color: transparent;" +
        "-fx-text-fill: white;" +
        "-fx-font-size: 16px;" +
        "-fx-cursor: hand;"
    );
    button.setOnMouseEntered(e -> button.setStyle(
        "-fx-background-color: rgba(255, 255, 255, 0.2);" +
        "-fx-text-fill: white;" +
        "-fx-font-size: 16px;" +
        "-fx-cursor: hand;"
    ));
    button.setOnMouseExited(e -> button.setStyle(
        "-fx-background-color: transparent;" +
        "-fx-text-fill: white;" +
        "-fx-font-size: 16px;" +
        "-fx-cursor: hand;"
    ));
    button.setPrefWidth(200);
    return button;
}
private TextField createStyledTextField() {
    TextField textField = new TextField();
    styleTextField(textField);
    return textField;
}

private void styleTextField(TextField textField) {
    textField.setStyle("""
        -fx-background-color: #ffffff;
        -fx-border-color: #4facfe;
        -fx-border-radius: 12;
        -fx-background-radius: 12;
        -fx-font-size: 14px;
        -fx-padding: 10 12;
    """);

    textField.setOnMouseEntered(e -> textField.setStyle("""
        -fx-background-color: #f5f5f5;
        -fx-border-color: #00f2fe;
        -fx-border-radius: 12;
        -fx-background-radius: 12;
        -fx-font-size: 14px;
        -fx-padding: 10 12;
    """));
    textField.setOnMouseExited(e -> textField.setStyle("""
        -fx-background-color: #ffffff;
        -fx-border-color: #4facfe;
        -fx-border-radius: 12;
        -fx-background-radius: 12;
        -fx-font-size: 14px;
        -fx-padding: 10 12;
    """));
}


    public static void main(String[] args) {
        launch(args);
    }
}

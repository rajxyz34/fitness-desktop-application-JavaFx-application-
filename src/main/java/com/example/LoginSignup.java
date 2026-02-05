package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginSignup {

    private final DatabaseConnector db = new DatabaseConnector();

    public void showLogin(Stage stage, Main mainApp) {
        // Gradient Background
        Background gradientBackground = new Background(new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#4facfe")),
                        new Stop(1, Color.web("#00f2fe"))),
                CornerRadii.EMPTY, Insets.EMPTY));

        // Title
        Text title = new Text("Welcome to Fitness Pro! 🏋‍♂");
        title.setFont(Font.font("Verdana", 30));
        title.setFill(Color.web("#ffffff"));
        title.setEffect(new DropShadow(10, Color.BLACK));

        // Username
        Label userLabel = createStyledLabel("Username:");
        TextField userField = createStyledTextField();
        userField.setPromptText("USERNAME");

        // Password
        Label passLabel = createStyledLabel("Password:");
        PasswordField passField = createStyledPasswordField();
        passField.setPromptText("Password");

        // Buttons
        Button loginButton = createStyledButton("Login 🚀");
        Button signupButton = createStyledButton("Create Account ✨");

        // Button Actions
        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();
            if (db.validateUser(username, password)) {
                System.out.println("Login successful!");
                mainApp.showHomePage(stage);
            } else {
                showAlert("Login Failed", "Invalid username or password.");
            }
        });

        signupButton.setOnAction(e -> createAccount(stage));

        // adding logo
        Image image = new Image("/images/logo.jpg");

        // Create an ImageView to display the image
        ImageView imageView = new ImageView(image);

        // Optionally resize the image
        imageView.setFitWidth(400);
        imageView.setFitHeight(600);
        imageView.setPreserveRatio(true);

        // Layout Grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25));
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);
        grid.setPrefWidth(600); // Increased the width of the GridPane
        grid.setPrefHeight(350); // Set a preferred height to make it larger

        grid.add(title, 0, 0, 2, 1);
        grid.add(userLabel, 0, 1);
        grid.add(userField, 1, 1);
        grid.add(passLabel, 0, 2);
        grid.add(passField, 1, 2);
        grid.add(loginButton, 0, 3);
        grid.add(signupButton, 1, 3);

        HBox mainContent = new HBox(50); // spacing between image and form
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setPadding(new Insets(30));

        mainContent.getChildren().addAll(imageView, grid);

        VBox rootLayout = new VBox(20, mainContent);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setBackground(gradientBackground);
        rootLayout.setPadding(new Insets(30));

        Scene scene = new Scene(rootLayout, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Login / Signup - Fitness Pro");
        stage.setMaximized(true);
        stage.show();
    }


    private void createAccount(Stage stage) {
        Main mainApp = new Main();
        Background gradientBackground = new Background(new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#4facfe")),
                        new Stop(1, Color.web("#00f2fe"))),
                CornerRadii.EMPTY, Insets.EMPTY));
        Text title1 = new Text("MIND BODY BALANCE");
        title1.setFont(Font.font("Verdana", 28));
        title1.setFill(Color.web("#ffffff"));
        title1.setEffect(new DropShadow(10, Color.BLACK));

        //creating text field
        TextField fName = createStyledTextField();
        fName.setPromptText("First name");

        TextField lName = createStyledTextField();
        lName.setPromptText("Surname");
        
        TextField age = createStyledTextField();
        age.setPromptText("Age");
        
        TextField height = createStyledTextField();
        height.setPromptText("height(in meter)");

        TextField weight = createStyledTextField();
        weight.setPromptText("Weight");
        TextField userName = createStyledTextField();
        userName.setPromptText("UserName");
        
        TextField password = createStyledTextField();
        password.setPromptText("password");
        
        
        //adding buttons
        Button signbtn = createStyledButton("SignUp");
        Button backbtn = createStyledButton("Back");
        backbtn.setOnAction(e -> showLogin(stage ,mainApp));
        signbtn.setOnAction(e -> {
            String Fname = fName.getText();
            String Lname = lName.getText();
            int Age = Integer.parseInt(age.getText());
            double Height = Double.parseDouble(height.getText());
            double Weight = Double.parseDouble(weight.getText());
            String username = userName.getText();
            String Password = password.getText();

            
            
            
            
            
            if (db.registerUser(username,Password,Age,Height,Weight,Fname,Lname)) {
                showAlert("Signup Successful 🎉", "You can now log in!");
            } else {
                showAlert("Signup Failed ❌", "Username may already exist.");
            }
        });


        
        

        
        
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25));
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        grid.add(title1, 0, 0, 2, 1);
        
        grid.add(fName, 0, 1);
        grid.add(lName, 1, 1);
        grid.add(height, 0, 2);
        grid.add(weight, 1, 2);
        grid.add(age, 0, 3);
        grid.add(userName, 1, 3);
        grid.add(password,0,4);


        VBox rootLayout = new VBox(20, grid,signbtn,backbtn);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setBackground(gradientBackground);
        rootLayout.setPadding(new Insets(30));

        Scene scene = new Scene(rootLayout);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        scene.getRoot().requestFocus();

        stage.setScene(scene);
        stage.setTitle("Create account");
        
        
        stage.show();
        
        


        
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 16));
        label.setTextFill(Color.web("#ffffff"));
        return label;
    }

    private TextField createStyledTextField() {
        TextField textField = new TextField();
        styleTextField(textField);
        return textField;
    }

    private PasswordField createStyledPasswordField() {
        PasswordField passwordField = new PasswordField();
        styleTextField(passwordField);
        return passwordField;
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

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("""
            -fx-background-color: linear-gradient(to right, #00c6ff, #0072ff);
            -fx-text-fill: #ffffff;
            -fx-font-size: 14px;
            -fx-padding: 10 20;
            -fx-background-radius: 12;
            -fx-border-radius: 12;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0.3, 0, 1);
        """);

        button.setOnMouseEntered(e -> button.setStyle("""
            -fx-background-color: linear-gradient(to right, #0072ff, #00c6ff);
            -fx-text-fill: #ffffff;
            -fx-font-size: 14px;
            -fx-padding: 10 20;
            -fx-background-radius: 12;
            -fx-border-radius: 12;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0.3, 0, 1);
        """));

        button.setOnMouseExited(e -> button.setStyle("""
            -fx-background-color: linear-gradient(to right, #00c6ff, #0072ff);
            -fx-text-fill: #ffffff;
            -fx-font-size: 14px;
            -fx-padding: 10 20;
            -fx-background-radius: 12;
            -fx-border-radius: 12;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0.3, 0, 1);
        """));
        return button;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

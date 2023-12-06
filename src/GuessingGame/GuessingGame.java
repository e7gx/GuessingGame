package GuessingGame;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.Random;

public class GuessingGame extends Application {

    private int targetNumber;
    private int guessCount;

    private Label promptLabel;
    private TextField guessTextField;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
        showGameScreen(primaryStage);
    }

    private void showGameScreen(Stage primaryStage) {
        int maxNumber = getUserInput("Enter the maximum number:");

        targetNumber = generateTargetNumber(maxNumber);
        guessCount = 0;

        promptLabel = new Label("Guess a number between 1 and " + maxNumber + ":");
        guessTextField = new TextField();
        Button guessButton = new Button("Guess");
        Button hintButton = new Button("Hint");
        Button backButton = new Button("Back");
        resultLabel = new Label();

        guessButton.setOnAction(event -> checkGuess());
        hintButton.setOnAction(event -> provideHint(maxNumber));
        backButton.setOnAction(event -> showGameScreen(primaryStage));

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
        root.getChildren().addAll(promptLabel, guessTextField, guessButton, hintButton, backButton, resultLabel);

        Scene scene = new Scene(root, 500, 500);
        scene.setFill(Color.WHITE);

        primaryStage.setTitle("Guessing Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private int getUserInput(String message) {
        TextInputDialog dialog = new TextInputDialog("100");
        dialog.setTitle("Input");
        dialog.setHeaderText(message);
        dialog.setContentText("Maximum Number:");

        Optional<String> result = dialog.showAndWait();
        int userInput = 0;
        if (result.isPresent()) {
            try {
                userInput = Integer.parseInt(result.get());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return userInput;
    }

    private int generateTargetNumber(int maxNumber) {
        Random random = new Random();
        return random.nextInt(maxNumber) + 1;
    }

    private void checkGuess() {
        String guessText = guessTextField.getText();
        if (!guessText.isEmpty()) {
            try {
                int guess = Integer.parseInt(guessText);
                guessCount++;

                if (guess == targetNumber) {
                    displayCongratulatoryMessage();
                } else if (guess < targetNumber) {
                    resultLabel.setText("Too low! Try again.");
                } else {
                    resultLabel.setText("Too high! Try again.");
                }
            } catch (NumberFormatException e) {
                resultLabel.setText("Invalid input. Please enter a valid number.");
            }
        }
        guessTextField.clear();
    }

    private void provideHint(int maxNumber) {
        int lowerBound = Math.max(1, targetNumber - 25);
        int upperBound = Math.min(maxNumber, targetNumber + 25);
        String hint = "The number is between " + lowerBound + " and " + upperBound + ".";
        resultLabel.setText(hint);
    }

    private void displayCongratulatoryMessage() {
        Alert congratsAlert = new Alert(Alert.AlertType.INFORMATION);
        congratsAlert.setTitle("Congratulations!");
        congratsAlert.setHeaderText(null);
        congratsAlert.setContentText("Congratulations! You guessed the number in " + guessCount + " attempts.");
        congratsAlert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
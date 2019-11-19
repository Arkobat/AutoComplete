package io.github.arkobat.autocomplete.gui;

import io.github.arkobat.autocomplete.Calculate;
import io.github.arkobat.autocomplete.Config;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;


public class Setting {

    private final Stage primaryStage;

    @Getter
    private final Scene scene;

    private final Input input;
    private TextArea textArea;

    public Setting(Stage primaryStage, Input input) {
        this.primaryStage = primaryStage;
        this.input = input;

        textArea = new TextArea();

        textArea.setText(Config.getButtonsFileContent());

        TilePane tilePane = new TilePane();

        Button button = new Button();
        button.setText("Save");
        button.setPrefSize(150, 75);
        button.setFont(new Font(20));
        button.setOnAction(aVoid -> {
            Config.updateButtonFile(textArea.getText());
            primaryStage.setScene(input.getScene());
        });
        tilePane.getChildren().add(button);

        VBox vBox = new VBox(textArea, button);
        Scene scene = new Scene(vBox, 350, 400);

        this.scene = scene;
    }
}

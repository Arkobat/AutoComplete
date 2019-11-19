package io.github.arkobat.autocomplete;

import io.github.arkobat.autocomplete.gui.Input;
import io.github.arkobat.autocomplete.model.Field;
import io.github.arkobat.autocomplete.model.Word;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;


public class Program extends Application {

    public static void load(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Compact Keyboard");
        primaryStage.setScene(new Input(primaryStage).getScene());
        primaryStage.setHeight(765);
        primaryStage.setWidth(466);
        primaryStage.show();
    }


}


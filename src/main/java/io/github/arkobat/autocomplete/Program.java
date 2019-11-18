package io.github.arkobat.autocomplete;

import io.github.arkobat.autocomplete.model.Field;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;


public class Program extends Application {
    private Calculate calculate = new Calculate();
    private ListView<String> listView = new ListView<>();

    public static void load(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Compact Keyboard");
        TilePane tilePane = new TilePane();
        List<Field> fields = Config.loadButtons();
        fields.forEach(f -> {
            Button button = new Button();
            button.setId(String.valueOf(f.getId()));
            button.setText(Arrays.toString(f.getLetters()));
            button.setPrefSize(150, 75);
            button.setFont(new Font(20));

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Button clicked = (Button) e.getSource();
                    Field field = Config.getButton(Integer.parseInt(clicked.getId()));
                    calculate.nextButton(field);
                    listView.getItems().setAll(calculate.getWords());
                }
            });
            tilePane.getChildren().add(button);
        });
        tilePane.getChildren().add(getResetButton());

        this.listView.getItems().setAll(calculate.getWords());

        VBox vBox = new VBox(tilePane, listView);
        Scene scene = new Scene(vBox, 350, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button getResetButton() {
        Button button = new Button();
        button.setText("<--");
        button.setPrefSize(150, 75);
        button.setFont(new Font(20));

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                calculate.erase();
                listView.getItems().setAll(calculate.getWords());
            }
        });
        return button;
    }
}


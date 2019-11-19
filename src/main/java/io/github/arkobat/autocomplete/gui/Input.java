package io.github.arkobat.autocomplete.gui;

import io.github.arkobat.autocomplete.Calculate;
import io.github.arkobat.autocomplete.Config;
import io.github.arkobat.autocomplete.model.Field;
import io.github.arkobat.autocomplete.model.Word;
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
import lombok.Getter;

import java.io.IOException;
import java.util.List;

public class Input {

    private final Stage primaryStage;

    private Setting setting;

    @Getter
    private final Scene scene;

    private Calculate calculate = new Calculate();
    private ListView<Word> listView = new ListView<>();
    private TilePane pressedFields = new TilePane();
    private String sentence = "";

    public Input(Stage primaryStage) {
        this.primaryStage = primaryStage;

        this.pressedFields.setMinHeight(25);
        TilePane tilePane = new TilePane();

        try {
            Config.loadButtons();
        } catch (IOException e) {
            e.printStackTrace();
            this.scene = null;
            return;
        }
        List<Field> fields = Config.getFields();
        for (Field f : fields) {
            Button button = new Button();
            button.setId(String.valueOf(f.getId()));
            button.setText(f.toString());
            button.setPrefSize(150, 75);
            button.setFont(new Font(20));
            button.setOnAction(aVoid -> onFieldPressed(f));
            tilePane.getChildren().add(button);
        }
        tilePane.getChildren().add(getSettingButton());
        tilePane.getChildren().add(getSpaceButton());
        tilePane.getChildren().add(getEraseButton());

        this.listView.getItems().setAll(calculate.getWords());
        this.listView.setOnMouseClicked(this::onSpace);


        VBox vBox = new VBox(this.pressedFields, tilePane, listView);
        Scene scene = new Scene(vBox, 350, 400);


        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                onSpace(e);
                return;
            } else if (e.getCode() == KeyCode.BACK_SPACE) {
                onErase();
                return;
            }
            Field field = Config.getField(e.getCode());
            if (field == null) {
                return;
            }
            onFieldPressed(field);
        });
        this.setting = new Setting(primaryStage, this);
        this.scene = scene;
    }

    private void onFieldPressed(Field f) {
        calculate.nextButton(f);
        listView.getItems().setAll(calculate.getWords());
        Button used = new Button();
        used.setText(f.toString());
        used.setPrefSize(40, 25);
        used.setFont(new Font(10));
        pressedFields.getChildren().add(used);
    }

    private Button getSettingButton() {
        Button button = new Button();
        button.setText("⚙");
        button.setPrefSize(150, 75);
        button.setFont(new Font(20));

        button.setOnAction(e -> {
            primaryStage.setScene(setting.getScene());
        });
        return button;
    }

    private Button getSpaceButton() {
        Button button = new Button();
        button.setText("[__]");
        button.setPrefSize(150, 75);
        button.setFont(new Font(20));

        button.setOnAction(this::onSpace);
        return button;
    }

    private void onSpace(Event e) {
        Word word = this.listView.getSelectionModel().getSelectedItem();
        if (word == null) {
            if (this.calculate.getWords().size() == 0) {
                return;
            }
            word = this.calculate.getWords().get(0);
        }
        Config.addUse(word);
        this.sentence += " " + word.getWord();
        System.out.println(sentence);
        this.calculate.clear();
        this.listView.getItems().setAll(calculate.getWords());
        this.pressedFields.getChildren().clear();
    }

    private Button getEraseButton() {
        Button button = new Button();
        button.setText("<--");
        button.setPrefSize(150, 75);
        button.setFont(new Font(20));

        button.setOnAction(aVoid -> onErase());
        return button;
    }

    private void onErase() {
        calculate.erase();
        listView.getItems().setAll(calculate.getWords());
        if (pressedFields.getChildren().size() > 0) {
            pressedFields.getChildren().remove(pressedFields.getChildren().size() - 1);
        }
    }
}

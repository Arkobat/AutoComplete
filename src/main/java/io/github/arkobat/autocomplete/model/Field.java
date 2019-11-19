package io.github.arkobat.autocomplete.model;

import javafx.scene.input.KeyCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Field {
    private KeyCode keyCode;
    private int id;
    private String[] letters;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String l : letters) {
            sb.append(l).append(", ");
        }
        return sb.toString().replaceAll(", $", "");
    }
}

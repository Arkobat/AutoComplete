package io.github.arkobat.autocomplete.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Field {
    private int id;
    private String[] letters;
}

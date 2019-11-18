package io.github.arkobat.autocomplete;

import io.github.arkobat.autocomplete.model.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Calculate {

    private List<String> words = new ArrayList<>();
    private List<Field> usedFields = new ArrayList<>();
    private int charIndex = 0;

    public Calculate nextButton(Field button) {
        if (charIndex == 0) {
            words.addAll(Config.getWordList(button.getId()));
        } else {
            words = words.stream().filter(w -> {
                if (w.length() <= charIndex) {
                    return false;
                }
                String nextChar = w.split("")[charIndex];
                return Arrays.stream(button.getLetters()).anyMatch(l -> l.equalsIgnoreCase(nextChar));
            }).collect(Collectors.toList());
        }
        this.usedFields.add(button);
        charIndex++;
        return this;
    }

    public List<String> getWords() {
        return this.words;
    }

    public void erase() {
        if (this.charIndex == 0) {
            return;
        }
        this.charIndex = 0;
        List<Field> tempFields = new ArrayList<>(this.usedFields);
        tempFields.remove(this.usedFields.size()-1);
        this.words.clear();
        this.usedFields.clear();
        tempFields.forEach(this::nextButton);
    }
}

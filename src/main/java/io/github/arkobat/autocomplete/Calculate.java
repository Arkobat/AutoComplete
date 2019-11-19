package io.github.arkobat.autocomplete;

import io.github.arkobat.autocomplete.model.Field;
import io.github.arkobat.autocomplete.model.Word;

import java.util.*;
import java.util.stream.Collectors;

public class Calculate {

    private List<Word> words = new ArrayList<>();
    private List<Field> usedFields = new ArrayList<>();
    private int charIndex = 0;

    public Calculate nextButton(Field button) {
        if (charIndex == 0) {
            words.addAll(Objects.requireNonNull(Config.getWordList(button)));
        } else {
            words = words.stream().filter(w -> {
                if (w.getWord().length() <= charIndex) {
                    return false;
                }
                String nextChar = w.getWord().split("")[charIndex];
                return Arrays.stream(button.getLetters()).anyMatch(l -> l.equalsIgnoreCase(nextChar));
            }).collect(Collectors.toList());
            words.sort(Word::compareTo);

        }
        this.usedFields.add(button);
        charIndex++;
        return this;
    }

    public List<Word> getWords() {
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

    public void clear() {
        this.charIndex = 0;
        this.words.clear();
        this.usedFields.clear();
    }
}

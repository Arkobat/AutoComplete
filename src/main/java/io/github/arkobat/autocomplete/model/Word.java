package io.github.arkobat.autocomplete.model;

import lombok.Getter;

@Getter
public class Word implements Comparable {
    private final String word;
    private final int length;
    private int uses;

    public Word(String word, int uses) {
        this.word = word;
        this.length = word.length();
        this.uses = uses;
    }

    public void addUse() {
        this.uses++;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Word)) {
            return 1;
        }
        Word word = (Word) o;

        // Sort by shortest word
        int lengthCompare = this.length - word.length;
        if (lengthCompare != 0) {
            return lengthCompare;
        }

        // Sort by highest use
        int usesCompare = word.uses - this.uses;
        if (usesCompare != 0) {
            return usesCompare;
        }

        // Sort alphabetically
        return this.word.compareTo(word.getWord());
    }

    @Override
    public String toString() {
        return this.word + " (" + this.uses + ")";
    }
}

package io.github.arkobat.autocomplete;

import io.github.arkobat.autocomplete.model.Field;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Config {

    private static List<Field> buttons = new ArrayList<>();
    private static Map<Integer, List<String>> words = new HashMap<>();

    public static List<Field> loadButtons() throws IOException {
        buttons.clear();
        words.clear();
        URL url = new URL("https://tickets.arkobat.com/buttons.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        int i = 1;
        String input = in.readLine();
        while (input != null) {
            buttons.add(new Field(i++, input.split(":")));
            input = in.readLine();
        }
        in.close();
        Config.loadWords();
        return buttons;
    }

    public static void loadWords() throws IOException {
        URL url = new URL("https://tickets.arkobat.com/words.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String input = in.readLine();
        while (input != null) {
            for (int i = 0; i < buttons.size(); i++) {
                String finalInput = input;
                if (Arrays.stream(buttons.get(i).getLetters()).anyMatch(l -> l.equalsIgnoreCase(finalInput.split("")[0]))) {
                    if (words.containsKey(i + 1)) {
                        List<String> list = words.get(i + 1);
                        list.add(input);
                        words.put(i + 1, list);
                    } else {
                        List<String> list = new ArrayList<>();
                        list.add(input);
                        words.put(i + 1, list);
                    }
                    break;
                }
            }
            input = in.readLine();
        }
        in.close();
    }

    public static Field getButton(int i) {
        return buttons.stream().filter(bu -> bu.getId() == i).findFirst().orElse(null);
    }

    public static String[] getButtonValues(int buttonId) {
        Field b = buttons.stream().filter(bu -> bu.getId() == buttonId).findFirst().orElse(null);
        return b != null ? b.getLetters() : null;
    }

    public static List<String> getWordList(int i) {
        if (words == null) {
        } else {
        }
        return words.get(i);
    }
}
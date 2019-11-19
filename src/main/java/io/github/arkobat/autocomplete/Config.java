package io.github.arkobat.autocomplete;

import io.github.arkobat.autocomplete.model.Field;
import io.github.arkobat.autocomplete.model.Word;
import javafx.scene.input.KeyCode;
import lombok.Getter;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Config {

    @Getter
    private static String buttonsFileContent = "";

    @Getter
    private static List<Field> fields = new ArrayList<>();
    private static List<Word> words = new ArrayList<>();

    private static boolean createFile = false;

    private static void createFile(String pathName) throws IOException {
        BufferedReader br = null;
        try {
             br = new BufferedReader(new FileReader(pathName));
        } catch (FileNotFoundException ignored){
        }
        if (br != null) {
            return;
        }
        InputStream in = Main.class.getClassLoader().getResourceAsStream(pathName);
        br = new BufferedReader(new InputStreamReader(in));
        FileWriter fileWriter = new FileWriter(pathName);
        String s = br.readLine();
        while (s != null) {
            fileWriter.write(s + "\n");
            s = br.readLine();
        }
        fileWriter.flush();
        fileWriter.close();
    }

    public static void loadButtons() throws IOException {
        createFile("buttons.csv");
        createFile("words.csv");

        BufferedReader br = new BufferedReader(new FileReader("buttons.csv"));

        String input = br.readLine();
        int i = 1;
        while (input != null) {
            buttonsFileContent += input + "\n";
            String[] split = input.split(";");
            fields.add(new Field(KeyCode.valueOf(split[0]), i++, Arrays.copyOfRange(split, 1, split.length)));
            input = br.readLine();
        }
        br.close();
        Config.loadWords();
    }

    private static void loadWords() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("words.csv"));
        String input = br.readLine();
        while (input != null) {
            String[] split = input.split(";");
            Word word = new Word(split[0], Integer.parseInt(split[1]));
            words.add(word);
            input = br.readLine();
        }
        br.close();
    }

    public static Field getField(int i) {
        return fields.stream().filter(bu -> bu.getId() == i).findFirst().orElse(null);
    }

    public static Field getField(KeyCode key) {
        return fields.stream().filter(bu -> bu.getKeyCode() == key).findFirst().orElse(null);
    }

    public static List<Word> getWordList(Field field) {
        if (words == null) {
            return null;
        }
        return words.stream().filter(w -> Arrays.stream(field.getLetters()).anyMatch(c -> w.getWord().split("")[0].equalsIgnoreCase(c))).collect(Collectors.toList());
    }

    public static void addUse(Word word) {
        word.addUse();
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i) == word) {
                words.set(i, word);
                return;
            }
        }
    }

    public static void updateButtonFile(String text) {
        try {
            fields.clear();
            buttonsFileContent = "";
            FileWriter fileWriter = new FileWriter("buttons.csv");
            int i = 1;
            for (String s : text.split("\n")) {
                fileWriter.write(s + "\n");
                buttonsFileContent += s + "\n";
                String[] split = s.split(";");
                fields.add(new Field(KeyCode.valueOf(split[0]), i++, Arrays.copyOfRange(split, 1, split.length)));
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
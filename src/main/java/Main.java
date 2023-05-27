import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static Scanner consloleScanner = new Scanner(System.in);
    public static String[] dictionary;
    public static char[] word;
    public static char[] wordMask;
    public static char[] usedLetters = {' '};
    public static int mistakes = 0;
    public static String[] gallowsCondition =
            {""" 
                 ---------
                 |       |
                 |
                 |
                 |
                 |
                 |
            _____|_____
            """,
                 """
                 ---------
                 |       |
                 |       0
                 |
                 |
                 |
                 |
            _____|_____
            """,
                 """
                 ---------
                 |       |
                 |       0
                 |      / \\
                 |      \\ /
                 |
                 |
            _____|_____
            """,
                 """
                 ---------
                 |       |
                 |       0
                 |   ---/ \\
                 |      \\ /
                 |
                 |
            _____|_____
            """,
                 """
                 ---------
                 |       |
                 |       0
                 |   ---/ \\---
                 |      \\ /
                 |
                 |
            _____|_____
            """,
                    """
                 ---------
                 |       |
                 |       0
                 |   ---/ \\---
                 |      \\ /
                 |     /
                 |    /
            _____|_____
            """,
                    """
                 ---------
                 |       |
                 |       0
                 |   ---/ \\---
                 |      \\ /
                 |     /   \\
                 |    /     \\
            _____|_____
            """};

    public static void main(String[] args) {
        startMenu();
    }

    public static void startMenu () {
        System.out.println("Добро пожаловать в игру \"Виселица\"!");
        readWordsFromFile();

        do {
            System.out.print("Хотите начать новую игру? Введите (д)а или (н)ет: ");
            String input = consloleScanner.nextLine();
            if (input.equalsIgnoreCase("д")) {
                System.out.println("Игра началась!");
                startGameLoop();
            }
            if (input.equalsIgnoreCase("н")) {
                System.out.println("До новых встреч!");
                break;
            }
        } while (true);
    }

    public static void startGameLoop() {
        chooseRandomWord();
        makeWordMask(word);
        printGameCondition();

        do {
            checkLetter(inputLetter());
            printGameCondition();
            if (areMistakesExceeded()) {
                System.out.println("Вы допустили максимальное количество ошибок и проиграли.");
                System.out.print("Было загадано слово: ");
                for (char letter: word) {
                    System.out.print(letter);
                }
                System.out.println();
                break;
            }
            if (checkVictory()) {
                System.out.println("Вы выйграли!");
                break;
            }
        } while (true);

        cleanMistakes();
        cleanUsedLetter();
    }

    public static void readWordFromConsole() {
        boolean repeat;
        do {
            repeat = false;
            System.out.print("Введите слово: ");
            String newWord = consloleScanner.nextLine();
            if (newWord.equals("")) {
                System.out.println("Вы ничего не ввели, попробуйте снова.");
                repeat = true;
                continue;
            }
            word = newWord.toUpperCase().toCharArray();
            for (char ch : word) {
                if (((ch < 1040) || (ch > 1071)) && ch != 1025) {
                    System.out.println("В слове присутсвует недопустимый символ. Пожалуйста, повторите ввод.");
                    repeat = true;
                    break;
                }
            }
        } while (repeat);
    }

    public static void readWordsFromFile() {

        try {
            InputStream resource = Main.class.getClassLoader().getResourceAsStream("dictionary.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            while (br.ready()) {
                sb.append(br.readLine());
                sb.append(" ");
            }
            br.close();
            dictionary = sb.toString().split(" ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void chooseRandomWord() {
        int randomNumber;
        do {
            randomNumber = (int) (Math.random() * (dictionary.length)) + 1;
        } while (dictionary[randomNumber].length() < 5 || dictionary[randomNumber].length() > 10);
        word = dictionary[randomNumber].toUpperCase().toCharArray();
    }

    public static void makeWordMask(char[] word) {
        wordMask = new char[word.length];
        for (int i = 0; i < word.length; i++) {
            wordMask[i] = '*';
        }
    }

    public static char inputLetter() {

        char letter = ' ';
        boolean repeat;

        do {
            repeat = false;
            System.out.print("Введите букву: ");
            String line = consloleScanner.nextLine();
            if (line.isEmpty()) {
                System.out.println("Вы ничего не ввели, попробуйте снова.");
                repeat = true;
                continue;
            }
            if (line.length() > 1) {
                System.out.println("Вы ввели больше одного символа. Повторите ввод.");
                repeat = true;
                continue;
            }
            letter = line.toUpperCase().charAt(0);
            if ((((letter < 1040) || (letter > 1071)) && letter != 1025)) {
                System.out.println("Введен недопустимый символ. Пожалуйста, повторите ввод.");
                repeat = true;
                continue;
            }
            for (char ch: wordMask) {
                if (letter == ch) {
                    System.out.println("Вы уже угадали данную букву, введите другую.");
                    repeat = true;
                    break;
                }
            }
            if (repeat) {
                continue;
            }
            for (char ch: usedLetters) {
                if (ch == letter) {
                    System.out.println("Вы уже использовали данную букву, введите другую.");
                    repeat = true;
                    break;
                }
            }

        } while (repeat);

        addUsedLetter(letter);

        return letter;
    }

    public static void checkLetter(char character) {
        boolean mistake = true;

        for (int i = 0; i < word.length; i++) {
            if (character == word[i]) {
                wordMask[i] = character;
                mistake = false;
            }
        }

        if (mistake) {
            mistakes++;
            System.out.println("Вы ошиблись, такой буквы в слове нет.");
            return;
        }

        System.out.println("Вы угадали букву!");
    }

    public static void printGameCondition() {
        System.out.println();
        System.out.println(Arrays.toString(wordMask));
        System.out.println();
        System.out.println(gallowsCondition[mistakes]);
        System.out.println("Количество допущенных ошибок: " + mistakes + " из 6.");
        System.out.println("Вы использовали следующие буквы: " + Arrays.toString(usedLetters));
    }

    public static boolean checkVictory() {
        boolean victory = true;

        for (char ch : wordMask) {
            if (ch == '*') {
                victory = false;
                break;
            }
        }

        return victory;
    }

    public static boolean areMistakesExceeded() {
        return mistakes == 6;
    }
    public static void cleanMistakes() {
        mistakes = 0;
    }

    public static void addUsedLetter(char letter) {
        usedLetters[usedLetters.length - 1] = letter;
        usedLetters = Arrays.copyOf(usedLetters, usedLetters.length + 1);
    }

    public static void cleanUsedLetter() {
        usedLetters = Arrays.copyOf(usedLetters, 1);
        usedLetters[0] = ' ';
    }
}
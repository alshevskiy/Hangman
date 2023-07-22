import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private final Scanner SCANNER = new Scanner(System.in);
    private final Dictionary dictionary;
    private char[] word;
    private char[] wordMask;
    private static char[] usedLetters = {' '};
    private int mistakes = 0;
    private final Gallow gallow;

    public Game(Dictionary dictionary, Gallow gallow) {
        this.dictionary = dictionary;
        this.gallow = gallow;
    }

    public void startMenu () {
        System.out.println("Добро пожаловать в игру \"Виселица\"!");
        do {
            System.out.print("Хотите начать новую игру? Введите (д)а или (н)ет: ");
            String input = SCANNER.nextLine();
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

    private void startGameLoop() {
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

    private void chooseRandomWord() {
        int randomNumber;
        do {
            randomNumber = (int) (Math.random() * (dictionary.getLength())) + 1;
        } while (dictionary.getWord(randomNumber).length() < 5 || dictionary.getWord(randomNumber).length() > 10);
        word = dictionary.getWord(randomNumber).toUpperCase().toCharArray();
    }

    private void makeWordMask(char[] word) {
        wordMask = new char[word.length];
        for (int i = 0; i < word.length; i++) {
            wordMask[i] = '*';
        }
    }

    private char inputLetter() {

        char letter = ' ';
        boolean repeat;

        do {
            repeat = false;
            System.out.print("Введите букву: ");
            String line = SCANNER.nextLine();
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

    private void checkLetter(char character) {
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

    private void printGameCondition() {
        System.out.println();
        System.out.println(Arrays.toString(wordMask));
        System.out.println();
        System.out.println(gallow.getCondition(mistakes));
        System.out.println("Количество допущенных ошибок: " + mistakes + " из 6");
        System.out.println("Вы использовали следующие буквы: " + Arrays.toString(usedLetters));
    }

    private boolean checkVictory() {
        boolean victory = true;

        for (char ch : wordMask) {
            if (ch == '*') {
                victory = false;
                break;
            }
        }

        return victory;
    }

    private boolean areMistakesExceeded() {
        return mistakes == 6;
    }
    private void cleanMistakes() {
        mistakes = 0;
    }

    private void addUsedLetter(char letter) {
        usedLetters[usedLetters.length - 1] = letter;
        usedLetters = Arrays.copyOf(usedLetters, usedLetters.length + 1);
    }

    private void cleanUsedLetter() {
        usedLetters = Arrays.copyOf(usedLetters, 1);
        usedLetters[0] = ' ';
    }

}

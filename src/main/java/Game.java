import java.util.*;

public class Game {
    private final Scanner SCANNER = new Scanner(System.in);
    private final Dictionary DICTIONARY;
    private final Gallow GALLOW;
    private final List<Character> WORD = new ArrayList<>();
    private final List<Character> WORD_MASK = new ArrayList<>();
    private final Set<Character> usedLetters = new HashSet<>();
    private int mistakes = 0;

    public Game(Dictionary dictionary, Gallow gallow) {
        this.DICTIONARY = dictionary;
        this.GALLOW = gallow;
    }

    public void start() {
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
        makeWordMask();
        printGameCondition();

        do {
            checkLetter(inputLetter());
            printGameCondition();
            if (mistakesAreExceeded()) {
                System.out.println("--------------------------------------------------------");
                System.out.println("Вы допустили максимальное количество ошибок и проиграли.");
                System.out.print("Было загадано слово: ");
                for (char letter: WORD) {
                    System.out.print(letter);
                }
                System.out.println();
                System.out.println("--------------------------------------------------------");
                break;
            }
            if (gameIsWon()) {
                System.out.println("++++++++++++");
                System.out.println("Вы выиграли!");
                System.out.println("++++++++++++");
                break;
            }
        } while (true);

        WORD.clear();
        WORD_MASK.clear();
        usedLetters.clear();
        cleanMistakes();
    }

    private void chooseRandomWord() {
        int randomNumber;
        do {
            randomNumber = (int) (Math.random() * (DICTIONARY.getSize())) + 1;
        } while (DICTIONARY.getWord(randomNumber).length() < 5 || DICTIONARY.getWord(randomNumber).length() > 10);
        char[] chars = DICTIONARY.getWord(randomNumber).toUpperCase().toCharArray();
        for (char symbol: chars) {
            this.WORD.add(symbol);
        }
    }

    private void makeWordMask() {
        for (int i = 0; i < WORD.size(); i++) {
            WORD_MASK.add('*');
        }
    }

    private char inputLetter() {
        char letter = ' ';
        boolean repeat;

        do {
            repeat = false;
            System.out.println("---------------");
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
                System.out.println("Введен недопустимый символ. Повторите ввод.");
                repeat = true;
                continue;
            }
            if (WORD_MASK.contains(letter)) {
                System.out.println("Вы уже угадали данную букву, введите другую.");
                repeat = true;
                continue;
            }
            if (usedLetters.contains((letter))) {
                System.out.println("Вы уже использовали данную букву, введите другую.");
                    repeat = true;
            }
        } while (repeat);

        usedLetters.add(letter);
        return letter;
    }

    private void checkLetter(char character) {
        boolean mistake = true;
        for (int i = 0; i < WORD.size(); i++) {
            if (WORD.get(i) == character) {
                WORD_MASK.set(i, character);
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
        System.out.println(Arrays.toString(WORD_MASK.toArray()));
        System.out.println();
        System.out.println(GALLOW.getCondition(mistakes));
        System.out.println("Количество допущенных ошибок: " + mistakes + " из 6");
        System.out.println("Вы использовали следующие буквы: " + Arrays.toString(usedLetters.toArray()));
    }

    private boolean gameIsWon() {
        boolean victory = true;

        for (char ch : WORD_MASK) {
            if (ch == '*') {
                victory = false;
                break;
            }
        }

        return victory;
    }

    private boolean mistakesAreExceeded() {
        return mistakes == 6;
    }
    private void cleanMistakes() {
        mistakes = 0;
    }
}
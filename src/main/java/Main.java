public class Main {

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary(new FileReader());
        Gallow gallow = new Gallow();
        Game game = new Game(dictionary, gallow);

        game.startMenu();
    }
}
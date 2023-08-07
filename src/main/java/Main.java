public class Main {
    public static void main(String[] args) {
        Game game = new Game(new Dictionary(new FileReader()), new Gallow());
        game.start();
    }
}
public class Dictionary {
    private final String[] DICTIONARY;

    public Dictionary(FileReader fileReader) {
        this.DICTIONARY = fileReader.readFile();
    }

    public String getWord(int number) {
        return DICTIONARY[number];
    }

    public int getLength () {
        return DICTIONARY.length;
    }
}


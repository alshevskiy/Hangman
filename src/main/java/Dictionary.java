import java.util.List;

public class Dictionary {
    private final List<String> DICTIONARY;

    public Dictionary(FileReader fileReader) {
        this.DICTIONARY = fileReader.readFile();
    }

    public String getWord(int index) {
        return DICTIONARY.get(index);
    }

    public int getSize() {
        return DICTIONARY.size();
    }
}


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class FileReader  {

    public List<String> readFile() {

        List<String> words = null;
        try {
            InputStream resource = Main.class.getClassLoader().getResourceAsStream("dictionary.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            while (br.ready()) {
                sb.append(br.readLine());
                sb.append(" ");
            }
            br.close();

            String[] arrayWords = sb.toString().split(" ");
            words = Arrays.asList(arrayWords);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }
}

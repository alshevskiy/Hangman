import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileReader  {

    public String[] readFile(){
        String[] arrayWords = new String[0];
        try {
            InputStream resource = Main.class.getClassLoader().getResourceAsStream("dictionary.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            while (br.ready()) {
                sb.append(br.readLine());
                sb.append(" ");
            }
            br.close();

            arrayWords = sb.toString().split(" ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayWords;
    }
}

import java.util.ArrayList;
import java.util.List;

public class Gallow {
    private final List<String> gallowsCondition = new ArrayList<>();

    Gallow () {
        gallowsCondition.add(
                 """ 
                 ---------
                 |       |
                 |
                 |
                 |
                 |
                 |
            _____|_____
            """
        );

        gallowsCondition.add("""
                 ---------
                 |       |
                 |       0
                 |
                 |
                 |
                 |
            _____|_____
            """
        );
        gallowsCondition.add(
                """
                    ---------
                    |       |
                    |       0
                    |      / \\
                    |      \\ /
                    |
                    |
               _____|_____
               """
        );
        gallowsCondition.add(
                 """
                 ---------
                 |       |
                 |       0
                 |   ---/ \\
                 |      \\ /
                 |
                 |
            _____|_____
            """
        );
        gallowsCondition.add(
                """
                   ---------
                   |       |
                   |       0
                   |   ---/ \\---
                   |      \\ /
                   |
                   |
              _____|_____
              """
        );
        gallowsCondition.add(
                """
                  ---------
                  |       |
                  |       0
                  |   ---/ \\---
                  |      \\ /
                  |     /
                  |    /
             _____|_____
             """
        );
        gallowsCondition.add(
                """
                   ---------
                   |       |
                   |       0
                   |   ---/ \\---
                   |      \\ /
                   |     /   \\
                   |    /     \\
              _____|_____
              """
        );
    }

    public String getCondition(int index) {
        return gallowsCondition.get(index);
    }
}



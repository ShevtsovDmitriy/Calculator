import Parser.Parser;

/**
 * Created by Дмитрий on 06.11.2014.
 */
public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.parse("((5+9)( + 78))()");
    }
}

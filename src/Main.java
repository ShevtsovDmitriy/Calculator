import Parser.*;

/**
 * Created by Дмитрий on 06.11.2014.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        parser.parse("10/5-1+2*6+2+3");
    }
}

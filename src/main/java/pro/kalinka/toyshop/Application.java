package pro.kalinka.toyshop;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    final private static String ProductRegister = "C:\\Users\\Alina\\IdeaProjects\\ToyShop\\src\\main\\resources\\ProductRegister(Toys).txt";
    final private static Charset CHARSET = StandardCharsets.UTF_8;
    final private static Path PATH = Path.of(ProductRegister);

    final private static String COMMAND_LIST_PRODUCT = "list-products";
    final private static String COMMAND_QUESTION = "?";
    final private static String COMMAND_SLASH_QUESTION = "/?";
    final private static String COMMAND_MINUS_QUESTION = "-?";
    final private static String COMMAND_MINUS_H = "-h";
    final private static String COMMAND_MINUS_HELP = "--help";

    final private static String WELCOME_TEXT = "Welcome to our online shop";
    final private static String USAGE_TEXT = "Usage:\n" +
            "  java -jar ToyShop.jar [command] [options]\n" +
            "  Commands:\n" +
            "   list-products            Show all available products in the shop\n" +
            "   /?, -?, -h, --help       Show help and usage information";


    public static void main(String[] args) throws IOException {

        final String allProductList = Files.readString(PATH, CHARSET);

        if (args.length == 0) {
            System.out.println(USAGE_TEXT);
        } else {
            String arg = args[0];
            if (arg.equals(COMMAND_LIST_PRODUCT)) {
                System.out.println(allProductList);
            } else if (arg.equals(COMMAND_QUESTION) || arg.equals(COMMAND_SLASH_QUESTION) || args.equals(COMMAND_MINUS_QUESTION)
                    || args.equals(COMMAND_MINUS_HELP) || arg.equals(COMMAND_MINUS_H)) {
                System.out.println(USAGE_TEXT);
            } else System.out.println(WELCOME_TEXT);
        }
    }
}
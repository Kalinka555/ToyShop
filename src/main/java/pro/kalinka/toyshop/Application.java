package pro.kalinka.toyshop;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {

    public static void main(String[] args) throws IOException {

        final Path PATH = Path.of("C:\\Users\\Alina\\IdeaProjects\\ToyShop\\src\\main\\resources\\ProductRegister(Toys).txt");
        final Charset CHARSET = StandardCharsets.UTF_8;
        final String allProductList = Files.readString(PATH, CHARSET);

        if (args.length == 0) {
            System.out.println("Usage:\n" +
                    "  java -jar ToyShop.jar [command] [options]\n" +
                    "  Commands:\n" +
                    "   list-products            Show all available products in the shop\n" +
                    "   /?, -?, -h, --help       Show help and usage information");
        } else {
            String arg = args[0];
            if (args[0].equals("list-products")) {
                System.out.println(allProductList);
            } else if (args[0].equals("/?") || arg.equals("-?") || args.equals("-h") || arg.equals("--help") || arg.equals("?")) {
                System.out.println("Usage:\n" +
                        "  java -jar ToyShop.jar [command] [options]\n" +
                        "  Commands:\n" +
                        "   list-products            Show all available products in the shop\n" +
                        "   /?, -?, -h, --help       Show help and usage information");
            } else System.out.println("Welcome to our online shop");
        }
    }
}







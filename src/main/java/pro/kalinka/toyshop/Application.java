package pro.kalinka.toyshop;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {

    public static void main(String[] args) throws IOException {
        System.out.println("Shop program started...");
        System.out.println("Heloo from our shop!");
        System.out.println("Be careful! Bye!");

        final Path PATH = Path.of("C:\\Users\\Alina\\IdeaProjects\\ToyShop\\src\\main\\resources\\ProductRegister(Toys).txt");
        final Charset CHARSET = StandardCharsets.UTF_8;
        String allProductList = Files.readString(PATH, CHARSET);

        if (args[0] == "list-products") {
            System.out.println(allProductList);
        }

        else if (args[0] == "/?" || args[0] == "-?" || args[0] == "-h" || args[0] == "--help" || args[0] == "?") {
            System.out.println("Usage:\n" +
                    "  java -jar ToyShop.jar [command] [options]\n" +
                    "  Commands:\n" +
                    "   list-products            Show all available products in the shop\n" +
                    "   /?, -?, -h, --help       Show help and usage information");
        }

        else System.out.println("Welcome to our online shop");
    }
}







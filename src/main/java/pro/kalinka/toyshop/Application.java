package pro.kalinka.toyshop;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class Application {
    final private static String ProductRegister = "C:\\Users\\Alina\\IdeaProjects\\ToyShop\\src\\main\\resources\\ProductRegister(Toys).txt";
    final private static Charset UNICODE = StandardCharsets.UTF_8;
    final private static Path PATH_OF_LIST_PRODUCTS = Path.of(ProductRegister);

    final private static String COMMAND_LIST_PRODUCT = "list-products";
    final private static String COMMAND_QUESTION = "?";
    final private static String COMMAND_SLASH_QUESTION = "/?";
    final private static String COMMAND_MINUS_QUESTION = "-?";
    final private static String COMMAND_MINUS_H = "-h";
    final private static String COMMAND_MINUS_HELP = "--help";
    final private static String COMMAND_ORDER = "create-order";

    final private static String WELCOME_TEXT = "Welcome to our online shop";
    final private static String USAGE_TEXT = "Usage:\n" +
            " java -jar ToyShop.jar [command] [options]\n" +
            " Commands:\n" +
            "  list-products            Shows all available products in the shop\n" +
            "  ?, /?, -?, -h, --help    Shows help and usage information\n" +
            "  create-order <id> <count> <deliveryAddress>   Allows to create an order with following parameters: ID-number, count, delivery address.";
    final private static String THANKS_TEXT = "  Your order number is № %d.\n" +
            "  Our manager will contact you to confirm the order. \n" +
            "  Thank you and we look forward to seeing you again in our store.\n";
    final private static String WRONG_ID_NUMBER_TEXT = "The ID-number must include only numbers from 1000 to 9999, please try again.\n";
    final private static String WRONG_COUNT_NUMBER_TEXT = "The count must include only number from 1 to 99, please try again.\n";
    final private static String WRONG_ADDRESS_TEXT = "The address is not filled in, please try again";
    final private static String ORDER_FILE_TEXT = " Order number - %d\n ID-number -  %d\n Count - %d\n Delivery address -  %s\n";

    public static void main(String... args) throws IOException {

        if (args.length == 0) {
            System.out.println(USAGE_TEXT);

        } else {
            String arg1 = args[0];
            switch (arg1) {
                case COMMAND_LIST_PRODUCT:
                    final String allProductList = Files.readString(PATH_OF_LIST_PRODUCTS, UNICODE);
                    System.out.println(allProductList);
                    break;

                case COMMAND_QUESTION:
                case COMMAND_SLASH_QUESTION:
                case COMMAND_MINUS_QUESTION:
                case COMMAND_MINUS_HELP:
                case COMMAND_MINUS_H:
                    System.out.println(USAGE_TEXT);
                    break;
                    
                case COMMAND_ORDER:
                    String IDNumberParameter = args[1];
                    String countParameter = args[2];
                    String deliveryAddress = args[3];

                    int IDNumber = Integer.parseInt(IDNumberParameter);
                    int count = Integer.parseInt(countParameter);

                    if (IDNumber < 1001 || IDNumber > 10000) {
                        System.out.println(WRONG_ID_NUMBER_TEXT);
                    } else if (count < 0 || count > 99) {
                        System.out.println(WRONG_COUNT_NUMBER_TEXT);
                    } else if (deliveryAddress == null) {
                        System.out.println(WRONG_ADDRESS_TEXT);
                    } else {
                        final Random RANDOM = new Random();
                        final int ORDER_NUMBER = RANDOM.nextInt(9998) + 1;
                        final String ORDER_FILE_NAME = "order-" + ORDER_NUMBER + ".txt";
                        PrintWriter orderFile = new PrintWriter(ORDER_FILE_NAME);
                        orderFile.printf(ORDER_FILE_TEXT, ORDER_NUMBER, IDNumber, count, deliveryAddress);
                        orderFile.close();
                        System.out.printf(THANKS_TEXT, ORDER_NUMBER);
                    }
                    break;
                default:
                    System.out.println(WELCOME_TEXT);
                    break;
            }
        }
    }
}
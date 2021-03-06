package pro.kalinka.toyshop;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

public class Application {
    final private static String ProductRegister = "C:\\Users\\Alina\\IdeaProjects\\ToyShop\\src\\main\\resources\\ProductRegister(Toys).txt";
    final private static Charset CHARSET = StandardCharsets.UTF_8;
    final private static Path PATH = Path.of(ProductRegister);
    //    final private static Scanner CUSTOMER = new Scanner(System.in);
//    final private static int CUSTOMER_NUM_TEXT = CUSTOMER.nextInt();
//    final private static String CUSTOMER_TEXT = CUSTOMER.next();
    final private static String COMMAND_QUESTION = "?";
    final private static String COMMAND_SLASH_QUESTION = "/?";
    final private static String COMMAND_MINUS_QUESTION = "-?";
    final private static String COMMAND_MINUS_H = "-h";
    final private static String COMMAND_MINUS_HELP = "--help";
    final private static String COMMAND_LIST_PRODUCT = "list-products";
    final private static String COMMAND_ORDER = "create-order";

    final private static String WELCOME_TEXT = "Welcome to our online shop.\n";
    final private static String USAGE_TEXT = "Usage:\n" +
            "  java -jar ToyShop.jar [command] [int id, int quantity, String address]\n" +
            "  Commands:\n" +
            "    list-products            Shows all available products in the shop\n" +
            "    ?, /?, -?, -h, --help    Shows help and usage information\n" +
            "    create-order             Allows you to create an order with the following parameters: ID-number, quantity, delivery address.";
    final private static String WRONG_ID_NUMBER_TEXT = "The ID-number must be from 1 to 999, try again.\n";
    final private static Random RANDOM = new Random();
    final private static int ORDER_NUMBER = RANDOM.nextInt(998) + 1;
    final private static String THANKS_TEXT = "  Your order number is № %d.\n"+
            "  Our manager will contact you to confirm the order. \n" +
            "  Thank you and we look forward to seeing you again in our store.\n";

    //final private static String ORDERING_ID_NUMBER_TEXT = "Enter the ID number of the product:\n";

    //final private static String ORDERING_QUANTITY_TEXT = "Enter the quantity of the product:\n";
    // final private static String WRONG_ORDERING_QUANTITY_TEXT = "The quantity of the product is wrong, try again:\n";
    //final private static String DELIVERY_ADDRESS = "Enter the delivery address:\n";
    //final private static String ORDER_NUMBER_TEXT = "Your order number is: %d"; // + ORDER_NUMBER;
    //final private static int ORDER_NUMBER = 0; //Create random method!!!

    public static void main(String[] args) throws IOException {

        final String allProductList = Files.readString(PATH, CHARSET);

        if (args.length == 0) {
            System.out.println(USAGE_TEXT);
        } else if (args.length > 0) {
            String arg1 = args[0];
            if (arg1.equals(COMMAND_LIST_PRODUCT)) {
                System.out.println(allProductList);
            } else if (arg1.equals(COMMAND_QUESTION) || arg1.equals(COMMAND_SLASH_QUESTION) || arg1.equals(COMMAND_MINUS_QUESTION)
                    || arg1.equals(COMMAND_MINUS_HELP) || arg1.equals(COMMAND_MINUS_H)) {
                System.out.println(USAGE_TEXT);
            } else if (arg1.equals(COMMAND_ORDER)) {
                int IDNumber = Integer.parseInt(args[1]);
                int quantity = Integer.parseInt(args[2]);
                String deliveryAddress = args[3];
                if (IDNumber > 1 && IDNumber < 1000) {
                    System.out.printf(THANKS_TEXT, ORDER_NUMBER);
                } else System.out.println(WRONG_ID_NUMBER_TEXT);
            }else
                System.out.println(WELCOME_TEXT);
        }
    }
}


//Пользователь ToyShop должен иметь возможность внеcти информацию о желаемой покупке.
//
//Для этого надо предусмотреть команду "create-order" со следующими опциями:
//
//артикул (ID) товара
//количество
//адрес доставки
//Программа должна сгенерировать случайный номер заказа от 1 до 9999.
//Информацию о созданном заказе программа должна сохранить в виде файла в текущей папке (cd), название файла формата order-{order_id}.txt, где {order_id} - это сгенерированный номер заказа.
//В ответ на команду программа должна его подтвердить и поблагодарить за заказ.

//Не забудь обновить help новой командой

//Да, и ещё программа должна показать пользователю номер заказа, конечно же.
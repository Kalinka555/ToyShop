package pro.kalinka.toyshop;
/**
 * Пользователь ToyShop должен иметь возможность внеcти информацию о желаемой покупке.
 * Для этого надо предусмотреть команду "create-order" со следующими опциями:
 * артикул (ID) товара
 * количество
 * адрес доставки
 * Опции - это тоже параметры командной строки, если что (java -jar ToyShop.jar [command] [options]).
 * Программа должна сгенерировать случайный номер заказа от 1 до 9999.
 * Информацию о созданном заказе программа должна сохранить в виде файла в текущей папке (cd), название файла формата order-{order_id}.txt, где {order_id} - это сгенерированный номер заказа.
 * В ответ на команду программа должна его подтвердить и поблагодарить за заказ.
 * Необходимо обновить help новой командой, и ещё программа должна показать пользователю номер заказа, конечно же.
 */

import java.io.IOException;
import java.io.PrintWriter;
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
    final private static String THANKS_TEXT = "  Your order number is № %d.\n" +
            "  Our manager will contact you to confirm the order. \n" +
            "  Thank you and we look forward to seeing you again in our store.\n";
    final private static String WRONG_ID_NUMBER_TEXT = "The ID-number must be from 1 to 999, try again.\n";
    final private static String ORDER_FILE_TEXT = " Order number - %d\n ID-number -  %d\n Quantity - %d\n Delivery address -  %s\n";
    final private static Random RANDOM = new Random();
    final private static int ORDER_NUMBER = RANDOM.nextInt(9998) + 1;
    final private static String ORDER_FILE_NAME = "order-{" + ORDER_NUMBER + "}.txt";

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
                if (IDNumber > 100000 && IDNumber < 1000000) {
                    System.out.printf(THANKS_TEXT, ORDER_NUMBER);
                    PrintWriter orderFile  = new PrintWriter(ORDER_FILE_NAME);
                    orderFile.printf(ORDER_FILE_TEXT, ORDER_NUMBER, IDNumber, quantity, deliveryAddress);
                    orderFile.close();
                } else System.out.println(WRONG_ID_NUMBER_TEXT);

            } else
                System.out.println(WELCOME_TEXT);
        }
    }
}

